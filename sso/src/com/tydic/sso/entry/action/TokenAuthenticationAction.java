/*
 * Copyrights © 2009，Tianyuan DIC Computer Co., Ltd. 数据大门户  All rights reserved. 
 * See license distributed with this available online at 
 *
 *      http://www.tydic.com/en/html/product/default.aspx
 *
 * Address: 3/F,T3 Building, South 7th Road, South Area, Hi-tech Industrial park, Shenzhen, P.R.C.
 * Email: webmaster@tydic.com　
 * Tel: +86 755 26745688 
 */
package com.tydic.sso.entry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inspektr.common.ioc.annotation.NotNull;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.validation.UsernamePasswordCredentialsValidator;
import org.jasig.cas.web.bind.CredentialsBinder;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.util.CookieGenerator;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.eetrust.plugin.UidPlugin;


/**
 * @author 
 *	cameron[曹志军]
 * @module  
 *	模块名： sso
 * @description  
 *	描述： 提交认证
 * @email 
 *	caozj@tydic.com,cameron6@163.com
 * @date  
 *	Created On : 2009-12-24 4:44:43 PM
 * @version 
 *	1.0
 **/
public class TokenAuthenticationAction extends FormAction {

    /**
     * Binder that allows additional binding of form object beyond Spring
     * defaults.
     */
    private CredentialsBinder credentialsBinder;

    /** Core we delegate to for handling all ticket related tasks. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    @NotNull
    private CookieGenerator warnCookieGenerator;

    protected final void doBind(final RequestContext context,final DataBinder binder) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        final Credentials credentials = (Credentials) binder.getTarget();

        if (this.credentialsBinder != null) {
            this.credentialsBinder.bind(request, credentials);
        }

        super.doBind(context, binder);
    }
    
    public Event referenceData(final RequestContext context) throws Exception {
        context.getRequestScope().put("commandName", getFormObjectName());
        return success();
    }

    public final Event submit(final RequestContext context) throws Exception {
    	//不需要form提交
        //final Credentials credentials = (Credentials) getFormObject(context);
        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        final Service service = WebUtils.getService(context);
        
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        
        UsernamePasswordCredentials upc = new  UsernamePasswordCredentials();
        
        String token = request.getParameter("EIACToken");
        
        if(token==null || token.trim()=="") {
        	return warn();
        }
      
        
        UidPlugin uidp = new UidPlugin(token);
        //认证
        int i = uidp.getAuthorization();
        
        //String usrName = uidp.getUserName();// 用户姓名
    	//String corpName = uidp.getCorpName();// 用户所在公司
    	//String deptName = uidp.getDeptName();// 用户所在部门
    	String role = uidp.getRole();// 用户角色
    	//……进一步处理成功信息
    	String staff_name =role;
        //eiac服务器验证成功
        if (i == 1) {
        	upc.setUsername(staff_name);
         }
         //eiac服务器验证失败
        else {
       	   try {
       		   String error = uidp.getError();//可得到验证错误
       	   		throw new TokenException(error);
       	   }
       	   catch(TicketException exception){
       		   populateErrorsInstance(context,exception);
       		   return error();
       	   }
         }
        
        //表示不做jdbc验证了
        upc.setPassword("dbp@:true");
        
        Credentials credentials = (Credentials)upc;
        
        if (StringUtils.hasText(context.getRequestParameters().get("renew")) && ticketGrantingTicketId != null && service != null) {

            try {
                final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId,service, credentials);
                WebUtils.putServiceTicketInRequestScope(context, serviceTicketId);
                putWarnCookieIfRequestParameterPresent(context);
                return warn();
            } catch (final TicketException e) {
                if (e.getCause() != null && AuthenticationException.class.isAssignableFrom(e.getCause().getClass())) {
                    populateErrorsInstance(context, e);
                    return error();
                }
                
                this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
                if (logger.isDebugEnabled()) {
                    logger.debug("Attempted to generate a ServiceTicket using renew=true with different credentials",e);
                }
            }
        }

        try {
            WebUtils.putTicketGrantingTicketInRequestScope(context,this.centralAuthenticationService.createTicketGrantingTicket(credentials));

            putWarnCookieIfRequestParameterPresent(context);
            return success();
        } catch (final TicketException e) {
            populateErrorsInstance(context, e);
            return error();
        }
    }

    private final Event warn() {
        return result("warn");
    }

    private final void populateErrorsInstance(final RequestContext context,
        final TicketException e) {

        try {
            final Errors errors = getFormErrors(context);
            errors.reject(e.getCode(), e.getCode());
        } catch (final Exception fe) {
            logger.error(fe, fe);
        }
    }

    private void putWarnCookieIfRequestParameterPresent(
        final RequestContext context) {
        final HttpServletResponse response = WebUtils.getHttpServletResponse(context);

        if (StringUtils.hasText(context.getExternalContext().getRequestParameterMap().get("warn"))) {
            this.warnCookieGenerator.addCookie(response, "true");
        } else {
            this.warnCookieGenerator.removeCookie(response);
        }
    }

    public final void setCentralAuthenticationService(
        final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    /**
     * Set a CredentialsBinder for additional binding of the HttpServletRequest
     * to the Credentials instance, beyond our default binding of the
     * Credentials as a Form Object in Spring WebMVC parlance. By the time we
     * invoke this CredentialsBinder, we have already engaged in default binding
     * such that for each HttpServletRequest parameter, if there was a JavaBean
     * property of the Credentials implementation of the same name, we have set
     * that property to be the value of the corresponding request parameter.
     * This CredentialsBinder plugin point exists to allow consideration of
     * things other than HttpServletRequest parameters in populating the
     * Credentials (or more sophisticated consideration of the
     * HttpServletRequest parameters).
     */
    public final void setCredentialsBinder(
        final CredentialsBinder credentialsBinder) {
        this.credentialsBinder = credentialsBinder;
    }
    
    public final void setWarnCookieGenerator(final CookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

    protected void initAction() {
        if (this.getFormObjectClass() == null) {
            this.setFormObjectClass(UsernamePasswordCredentials.class);
            this.setFormObjectName("credentials");
            this.setValidator(new UsernamePasswordCredentialsValidator());

            logger.info("FormObjectClass not set.  Using default class of "
                + this.getFormObjectClass().getName() + " with formObjectName "
                + this.getFormObjectName() + " and validator "
                + this.getValidator().getClass().getName() + ".");
        }

        Assert.isTrue(Credentials.class.isAssignableFrom(this.getFormObjectClass()),"CommandClass must be of type Credentials.");

        if (this.credentialsBinder != null) {
            Assert.isTrue(this.credentialsBinder.supports(this.getFormObjectClass()),"CredentialsBinder does not support supplied FormObjectClass: " + this.getClass().getName());
        }
    }
}

