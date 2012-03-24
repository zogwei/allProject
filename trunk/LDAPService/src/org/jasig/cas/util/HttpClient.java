/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.uportal.org/license.html
 */
package org.jasig.cas.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inspektr.common.ioc.annotation.GreaterThan;
import org.inspektr.common.ioc.annotation.NotNull;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;

/**
 * @author Scott Battaglia
 * @version $Revision: 43407 $ $Date: 2008-03-25 09:54:08 -0400 (Tue, 25 Mar 2008) $
 * @since 3.1
 */
public final class HttpClient implements Serializable,DisposableBean {

    /** Unique Id for serialization. */
    private static final long serialVersionUID = -5306738686476129516L;

    /** The default status codes we accept. */
    private static final int[] DEFAULT_ACCEPTABLE_CODES = new int[] {
        HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_NOT_MODIFIED,
        HttpURLConnection.HTTP_MOVED_TEMP, HttpURLConnection.HTTP_MOVED_PERM,
        HttpURLConnection.HTTP_ACCEPTED};

    private static final Log log = LogFactory.getLog(HttpClient.class);

    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(100);
    
    /** List of HTTP status codes considered valid by this AuthenticationHandler. */
    @NotNull
    private int[] acceptableCodes = DEFAULT_ACCEPTABLE_CODES;

    @GreaterThan(0)
    private int connectionTimeout = 5000;

    @GreaterThan(0)
    private int readTimeout = 5000;
    
    /**
     * Note that changing this executor will affect all httpClients.  While not ideal, this change was made because certain ticket registries
     * were persisting the HttpClient and thus getting serializable exceptions.
     * @param executorService
     */
    public void setExecutorService(final ExecutorService executorService) {
        Assert.notNull(executorService);
        EXECUTOR_SERVICE = executorService;
    }
    
    public boolean sendMessageToEndPoint(String url, final String message,final boolean async) {
        final Future<Boolean> result = EXECUTOR_SERVICE.submit(new MessageSender(url, message, this.readTimeout, this.connectionTimeout));

        if (async) {
            return true;
        }

        try {
            return result.get();
        } catch (final Exception e) {
            return false;
        }
    }
    public static String extranctManagedServerNameFromUrl(String url){
    	if(CommonUtils.isEmpty(url)){
    		return "";
    	}
    	int begin = url.indexOf("Managed_Server_Name");
    	if(begin==-1){
    		return "";
    	}
    	int end = url.indexOf("?")==-1?url.length():url.indexOf("?");
    	return url.substring(begin, end);
    }
    public boolean isValidEndPoint(final String url) {
        try {
            final URL u = new URL(url);
            return isValidEndPoint(u);
        } catch (final MalformedURLException e) {
            log.error(e,e);
            return false;
        }
    }

    public boolean isValidEndPoint(final URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(this.connectionTimeout);
            connection.setReadTimeout(this.readTimeout);

            connection.connect();

            final int responseCode = connection.getResponseCode();

            for (int i = 0; i < this.acceptableCodes.length; i++) {
                if (responseCode == this.acceptableCodes[i]) {
                    if (log.isDebugEnabled()) {
                        log.debug("Response code from server matched "
                            + responseCode + ".");
                    }
                    return true;
                }
            }

            if (log.isDebugEnabled()) {
                log
                    .debug("Response Code did not match any of the acceptable response codes.  Code returned was "
                        + responseCode);
            }
        } catch (final IOException e) {
            log.error(e,e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    /**
     * Set the acceptable HTTP status codes that we will use to determine if the
     * response from the URL was correct.
     * 
     * @param acceptableCodes an array of status code integers.
     */
    public final void setAcceptableCodes(final int[] acceptableCodes) {
        this.acceptableCodes = acceptableCodes;
    }

    public void setConnectionTimeout(final int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setReadTimeout(final int readTimeout) {
        this.readTimeout = readTimeout;
    }
    public void destroy() throws Exception {
        EXECUTOR_SERVICE.shutdown();
    }
    
    private static final class MessageSender implements Callable<Boolean> {

        private String url;

        private String message;

        private int readTimeout;

        private int connectionTimeout;

        public MessageSender(String url, final String message, final int readTimeout, final int connectionTimeout) {
            this.url = url;
            this.message = message;
            this.readTimeout = readTimeout;
            this.connectionTimeout = connectionTimeout;
        }

        public Boolean call() throws Exception {
            HttpURLConnection connection = null;
            BufferedReader in = null;
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Attempting to access " + url);
                }
                String managedServerName = extranctManagedServerNameFromUrl(url);
                String managedServer = managedServerName.replace("Managed_Server_Name=", "");
                String oldip = url.split("/")[2];
                
                url = url.replace(oldip, managedServer.trim());
                
                final URL logoutUrl = new URL(url);
                final String output = "logoutRequest=" + URLEncoder.encode(message, "UTF-8");

                connection = (HttpURLConnection) logoutUrl.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(readTimeout);
                connection.setConnectTimeout(connectionTimeout);
                connection.setRequestProperty("Content-Length", Integer.toString(output.getBytes().length));
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                final DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
                printout.writeBytes(output);
                printout.flush();
                printout.close();

                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (in.readLine() != null) {
                    // nothing to do
                }

                if (log.isDebugEnabled()) {
                    log.debug("Finished sending message to" + url);
                }
                return true;
            } catch (final SocketTimeoutException e) {
                log.warn("Socket Timeout Detected while attempting to send message to [" + url + "].");
                return false;
            } catch (final Exception e) {
                log.warn("Error Sending message to url endpoint [" + url + "].  Error is [" + e.getMessage() + "]");
                return false;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (final IOException e) {
                        // can't do anything
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

    }    
}
