package com.tydic.sso.entry.action;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.tydic.sso.Util.SystemConfigProperties;

/**
 * Created by IntelliJ IDEA.
 * User: houdc
 * Date: 2010-5-24
 * Time: 17:41:08
 * To change this template use File | Settings | File Templates.
 */
public class SendToDefaultServiceAction extends AbstractAction {

	private TicketRegistry ticketRegistry;

    public void setTicketRegistry(TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}

	@Override
    protected Event doExecute(RequestContext context) throws Exception {
        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        Service serivce = WebUtils.getService(context);
        if (ticketGrantingTicketId == null) {
            return error();
        }
        if(serivce==null){
            TicketGrantingTicket ticket = (TicketGrantingTicket) this.ticketRegistry.getTicket(ticketGrantingTicketId);
            Authentication auth = ticket.getAuthentication();
            Map<String,Object> map = auth.getPrincipal().getAttributes();
            String ssoUrl = map.get("ssoUrl")==null?"":map.get("ssoUrl")+"";
            String defaultUrl = SystemConfigProperties.get("login.service.defaultUrl");
            if(!StringUtils.isEmpty(ssoUrl)){
            	defaultUrl = ssoUrl;
            }
            Service service = new SimpleWebApplicationServiceImpl(defaultUrl);

            context.getFlowScope().put("service", service);       	
        }

        return success();
    }

}
