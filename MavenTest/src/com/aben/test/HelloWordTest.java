package com.aben.test;

import org.junit.Test;

public class HelloWordTest {

	@Test
	public void testIt(){
		String exp = "Hello : ab";
		HelloWorld hello = new HelloWorld();
		String act = hello.setHellp("ab");
		
	}
}
