package com.netty.test;

public class ChannelOutboundBuffer {
	  private static int head;
	  private static int tail;
	   
	  private static String[] msgList = new String[8];
	  
	  public static void main(String[] args){
		  tail = 15;
		  System.out.println(tail + 1 & msgList.length - 1);
	  }
}
