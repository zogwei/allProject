package com.aben.base.tcp;

import com.google.code.yanf4j.core.Session;
import com.google.code.yanf4j.core.impl.HandlerAdapter;

public class TcpHandler extends HandlerAdapter {
	
	public void onMessageReceived(Session session, Object message) {
		System.out.println(">>> recive msg : "  + message );
	}

}

