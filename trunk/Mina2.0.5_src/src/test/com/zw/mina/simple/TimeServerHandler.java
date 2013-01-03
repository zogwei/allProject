package com.zw.mina.simple;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter
{

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception 
    {
        cause.printStackTrace();
    }
    public void messageReceived(IoSession session, Object message) throws Exception 
    {
        String strMsg = message.toString();
        if(strMsg.trim().equalsIgnoreCase("quit"))
        {
            session.close();
            return;
        }
        Date date = new Date();
        session.write(date.toString());
        
        System.out.println("Message written:  " + message );
    }
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception 
    {
        System.out.println("IDLE"+session.getIdleCount(status));        
    }

}


