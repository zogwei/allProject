package com.sun.btrace.samples.test;
import java.util.Random;

public class BTraceTest {

	public static void main(String[] args) throws Exception {
		Random random = new Random();

		// 计数器
		Counter counter = new Counter();
		while (true) {
			// 每次增加随机值
			counter.add(random.nextInt(10));
			Thread.sleep(1000);
		}
	}
}

 class Counter {
	// 总数
	private static int totalCount = 0;

	public int add(int num) throws Exception {
		totalCount += num;
		sleep();
		return totalCount;
	}
	
	public void sleep() throws Exception {
		Thread.sleep(1000);
	}

}