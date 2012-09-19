package com.zw.netty.simple;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.example.telnet.TelnetServerPipelineFactory;

public class TelnetServer {

	public static void main(String[] args) throws Exception {
		// Configure the server.
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		// Configure the pipeline factory.
		bootstrap.setPipelineFactory(new TelnetServerPipelineFactory());

		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(8080));
	}
}