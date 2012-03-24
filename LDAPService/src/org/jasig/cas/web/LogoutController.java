/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inspektr.common.ioc.annotation.NotNull;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller to delete ticket granting ticket cookie in order to log out of
 * single sign on. This controller implements the idea of the ESUP Portail's
 * Logout patch to allow for redirecting to a url on logout. It also exposes a
 * log out link to the view via the WebConstants.LOGOUT constant.
 * 
 * @author Scott Battaglia
 * @version $Revision: 42776 $ $Date: 2008-01-04 09:15:42 -0500 (Fri, 04 Jan 2008) $
 * @since 3.0
 */
public final class LogoutController extends AbstractController {

    /** The CORE to which we delegate for all CAS functionality. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    /** CookieGenerator for TGT Cookie */
    @NotNull
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

    /** CookieGenerator for Warn Cookie */
    @NotNull
    private CookieRetrievingCookieGenerator warnCookieGenerator;

    /** Logout view name. */
    @NotNull
    private String logoutView;

    private TicketRegistry ticketRegistry;
    
	/**
     * Boolean to determine if we will redirect to any url provided in the
     * service request parameter.
     */
    private boolean followServiceRedirects;
    
    public void setTicketRegistry(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }
    
    public LogoutController() {
        setCacheSeconds(0);
    }

    protected ModelAndView handleRequestInternal(
        final HttpServletRequest request, final HttpServletResponse response)
        throws Exception {
        final String ticketGrantingTicketId = this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
        final String service = request.getParameter("service");
        if (ticketGrantingTicketId != null) {
        	TicketGrantingTicket ticket = (TicketGrantingTicket) this.ticketRegistry.getTicket(ticketGrantingTicketId);
        	HashMap<String, Service> services = ticket.getServices();
        	List<String> urls = new ArrayList<String>();
        	for(Entry<String,Service> entry:services.entrySet()){
        		Service s = entry.getValue();
        		urls.add(reBuildLogOutUrl(s.getId()));
        	}
        	
        	request.setAttribute("logoutUrls", urls);
        	this.centralAuthenticationService
                .destroyTicketGrantingTicket(ticketGrantingTicketId);
            this.ticketGrantingTicketCookieGenerator.removeCookie(response);
            this.warnCookieGenerator.removeCookie(response);
        }

        if (this.followServiceRedirects && service != null) {
            return new ModelAndView(new RedirectView(service));
        }

        return new ModelAndView(this.logoutView);
    }

    public void setTicketGrantingTicketCookieGenerator(
        final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
        this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
    }

    public void setWarnCookieGenerator(final CookieRetrievingCookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

    /**
     * @param centralAuthenticationService The centralAuthenticationService to
     * set.
     */
    public void setCentralAuthenticationService(
        final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    public void setFollowServiceRedirects(final boolean followServiceRedirects) {
        this.followServiceRedirects = followServiceRedirects;
    }

    public void setLogoutView(final String logoutView) {
        this.logoutView = logoutView;
    }
    
    private static String reBuildLogOutUrl(String url){
    	String[] strs = url.split("/");
    	String restr = "";
    	StringBuffer sb = new StringBuffer("");
    	if(strs.length>4){
    		sb.append(strs[0]).append("/").append(strs[1]).append("/").append(strs[2]).append("/").append(strs[3]);
    		restr = sb.toString();
    	}else if(strs.length<=4){
    		restr = url;
    	}
    	restr = restr+"/sso/logout"+Math.random();
    	return restr;
    }
}
