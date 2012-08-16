package com.aben.echoserver;

import java.io.IOException;

import com.google.code.yanf4j.config.Configuration;
import com.google.code.yanf4j.core.impl.TextLineCodecFactory;
import com.google.code.yanf4j.nio.TCPController;

public class ChatServer {
	public static void main(String[] args) throws IOException {
		TCPController controller = new TCPController(new Configuration(),
				new ChatServerHandler(), new TextLineCodecFactory());
		controller.bind(5566); // 端口
		controller.start(); // 启动
	}
}
