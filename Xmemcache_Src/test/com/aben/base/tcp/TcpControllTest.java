package com.aben.base.tcp;

import com.google.code.yanf4j.core.impl.TextLineCodecFactory;
import com.google.code.yanf4j.nio.TCPController;

public class TcpControllTest {

	/**
	 * @param args
	 */ 
	public static void main(String[] args) {
		TCPController ctl = new TCPController();
		ctl.setHandler(new TcpHandler());
		ctl.setCodecFactory(new TextLineCodecFactory());
		try{
			ctl.bind(8899);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
