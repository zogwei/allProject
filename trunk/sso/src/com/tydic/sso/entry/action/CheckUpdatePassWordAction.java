package com.tydic.sso.entry.action;

import java.util.Calendar;
import java.util.Date;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.PrincipalExt;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.jasig.cas.authentication.principal.WebApplicationService;
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
 * Time: 17:06:29
 * To change this template use File | Settings | File Templates.
 */
public class CheckUpdatePassWordAction extends AbstractAction {
    private TicketRegistry ticketRegistry;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        if (ticketGrantingTicketId == null) {
            return error();
        }
        final WebApplicationService curservice = WebUtils.getService(context);//取当前service
        //获取Authentication对像
        TicketGrantingTicket ticket = (TicketGrantingTicket) this.ticketRegistry.getTicket(ticketGrantingTicketId);
        
        Authentication authen = ticket.getAuthentication();
        Principal principal = authen.getPrincipal();
        PrincipalExt pext = (PrincipalExt)principal;

        Date data = pext.getUpdatePsdDate();
        //map.put("updatePsdDate", new Date());
        //如果最后修改时间为null则强制进入密码修改页面
        if(data==null){
            //创建Service 并注册Service 重定向到密码修改页面
            String changePassWordUrl = SystemConfigProperties.get("changePassword.service.url");

            Service service = new SimpleWebApplicationServiceImpl(changePassWordUrl);
            
            if(!curservice.matches(service)){
            	context.getRequestScope().put("serviceTicketId", "");
            }
            context.getFlowScope().put("service", service);
            pext.setUpdatePsdDate(new Date());
            
            ticket.update();
            return yes();
        }
        //进行时间比较
        
        
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, 90);
        Date nowDate = new Date();
        
        //long timel = nowDate.getTime()-data.getTime();
        
        if (nowDate.after(calendar.getTime())) {
            //创建Service 并注册Service 重定向到密码修改页面
            String changePassWordUrl = SystemConfigProperties.get("changePassword.service.url");

            Service service = new SimpleWebApplicationServiceImpl(changePassWordUrl);
            
            if(!curservice.matches(service)){
            	context.getRequestScope().put("serviceTicketId", "");
            }
            context.getFlowScope().put("service", service);
            //context.getRequestScope().put("serviceTicketId", "");
            pext.setUpdatePsdDate(new Date());
            ticket.update();
            return yes();
        }
        return success();
    }
    public void setTicketRegistry(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

}
