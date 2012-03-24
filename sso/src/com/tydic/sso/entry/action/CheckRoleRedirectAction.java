package com.tydic.sso.entry.action;

import com.tydic.sso.Util.SystemConfigProperties;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: houdc
 * Date: 2010-5-24
 * Time: 16:12:18
 * To change this template use File | Settings | File Templates.
 */
public class CheckRoleRedirectAction extends AbstractAction {

    private TicketRegistry ticketRegistry;

    private CentralAuthenticationService centralAuthenticationService;
    //配置需要重定向的角色 roleId,配置文件(system-config.propertis)中的key
    private Properties roles;

    public void setRoles(Properties roles) {
        this.roles = roles;
    }

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        if (ticketGrantingTicketId == null) {
            return error();
        }
        //获取Ticket对像
        TicketGrantingTicket ticket = (TicketGrantingTicket) this.ticketRegistry.getTicket(ticketGrantingTicketId);

        Authentication authen = ticket.getAuthentication();
        Principal principal = authen.getPrincipal();
        Map<String, Object> map = principal.getAttributes();
        String roles = map.get("ROLES")==null?"":map.get("ROLES").toString();
        if (roles == null || roles.equals("")) {
            return no();
        }

        String[] roleIds = roles.split(",");
        String key = "";
        for (String roleId : roleIds) {
            key = this.roles.getProperty(roleId);
            if (key != null && !"".equals(key)) {
                break;
            }
        }
        //从配置文件中读出URL
        if(key==null || "".equals(key)){
            return no();
        }
        String url = SystemConfigProperties.get(key);
        if (url == null || "".equals(url)) {
            return no();
        }
        //新建service将原来的service替换
        Service service = new SimpleWebApplicationServiceImpl(url);

        //将service注册
        final String serviceTicketId = this.centralAuthenticationService
                .grantServiceTicket(ticketGrantingTicketId,
                        service);
        WebUtils.putServiceTicketInRequestScope(context,
                serviceTicketId);
        //将service放入socp中
        context.getFlowScope().put("service", service);

        return success();
    }

    public void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    public void setTicketRegistry(final TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }


}
