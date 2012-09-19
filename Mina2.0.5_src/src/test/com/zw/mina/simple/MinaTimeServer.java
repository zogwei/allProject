package com.zw.mina.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class MinaTimeServer 
{
    private static final int PORT = 6488;
    
    public static void main(String[] args) throws IOException 
    {
        //��������������TCP����
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName("UTF-8"))));
        
        acceptor.setHandler(new TimeServerHandler());
        
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        
        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("����������");
    }

}

