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

/**
 * @author 
 *	cameron[曹志军]
 * @module  
 *	模块名： sso
 * @description  
 *	描述： 根据token登录的(集团门户通过这个action登录到西藏的大门户)
 * @email 
 *	caozj@tydic.com,cameron6@163.com
 * @date  
 *	Created On : 2009-12-24 4:27:21 PM
 * @version 
 *	1.0
 **/
public class TokenLoginAction extends AbstractAction {

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
	
	@Override
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
        
        // 若参数包含token则进行提交，否则进行验证
        if (StringUtils.hasText(request.getParameter("EIACToken"))) {
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

