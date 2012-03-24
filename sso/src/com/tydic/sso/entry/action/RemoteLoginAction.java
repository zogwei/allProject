/**
 * Copyrights @ 2009，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：DBP-Server
 * All rights reserved. 
 * Filename：RemoteLoginAction.java 
 * Description：
 * 	1. SSO 远程登录实现
 * version:1.0
 * user:cameron[曹志军]
 * History:2009-Aug 22, 2009-3:47:13 PM
 */
package com.tydic.sso.entry.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.inspektr.common.ioc.annotation.NotEmpty;
import org.inspektr.common.ioc.annotation.NotNull;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class RemoteLoginAction extends AbstractAction {
	
	  /** CookieGenerator for the Warnings. */
    @NotNull
    private CookieRetrievingCookieGenerator warnCookieGenerator;
    
    /** CookieGenerator for the TicketGrantingTickets. */
    @NotNull
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;
    
    /** Extractors for finding the service. */
    @NotEmpty
    private List<ArgumentExtractor> argumentExtractors;
    
    /** Boolean to note whether we've set the values on the generators or not. */
    private boolean pathPopulated = false;
    
    /**
     *  远程登录实现
     * 
     *  method name:RemoteLoginAction-doExecute
     *	@param context
     *	@return
     *	@throws Exception
     */
    protected Event doExecute(final RequestContext context) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        if (!this.pathPopulated) {
            final String contextPath = context.getExternalContext().getContextPath();
            final String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
            
            logger.info("Setting path for cookies to: " + cookiePath);
            
            this.warnCookieGenerator.setCookiePath(cookiePath);
            this.ticketGrantingTicketCookieGenerator.setCookiePath(cookiePath);
            this.pathPopulated = true;
        }
        context.getFlowScope().put("ticketGrantingTicketId",this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request));
        context.getFlowScope().put("warnCookieValue",
                Boolean.valueOf(this.warnCookieGenerator.retrieveCookieValue(request)));
        final Service service = WebUtils.getService(this.argumentExtractors, context);
        if (service != null && logger.isDebugEnabled()) {
            logger.debug("Placing service in FlowScope: " + service.getId());
        }
        context.getFlowScope().put("service", service);
        
        // 客户端必须传递loginUrl参数过来，否则无法确定登陆目标页面
        if (StringUtils.hasText(request.getParameter("loginUrl"))) {
            context.getFlowScope().put("remoteLoginUrl", request.getParameter("loginUrl"));
        } 
        else {
            request.setAttribute("remoteLoginMessage", "loginUrl parameter must be supported.");
            return error();
        }
        
        // 若参数包含submit则进行提交，否则进行验证
        if (StringUtils.hasText(request.getParameter("submit"))) {
            return result("submit");
        } 
        else {
            return result("checkTicketGrantingTicket");
        }
    }
    public void setTicketGrantingTicketCookieGenerator( final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
        this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
    }
    public void setWarnCookieGenerator(final CookieRetrievingCookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }
    public void setArgumentExtractors( final List<ArgumentExtractor> argumentExtractors) {
        this.argumentExtractors = argumentExtractors;
    }

}

