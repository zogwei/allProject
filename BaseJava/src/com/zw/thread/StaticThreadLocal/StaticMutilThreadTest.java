package com.zw.thread.StaticThreadLocal;

//结论：在多线程环境下的 线程变量，数据范围只是线程环境，即使是static fianl的
import java.util.Random;
import java.util.concurrent.Executor;
public class StaticMutilThreadTest {

	 static final ThreadLocal<Integer> data = new ThreadLocal<Integer>();

	 public static void main(String[] args) {
		 data.set(Integer.valueOf(0));
		 System.out.println("0 in data:"+data.get());
	  new Thread(new Runnable(){
	   public void run(){
		   System.out.println("first:put in data:"+data.get());
	    data.set( new Random().nextInt());
	   System.out.println("first:put in data:"+data.get());
	   new A().get();
	   new B().get();   
	   }
	  }).start();

	  new Thread(new Runnable(){
	   public void run(){
		   System.out.println("second:put in data:"+data.get());
		   data.set( new Random().nextInt());
	   System.out.println("second:put in data:"+data.get());
	   new A().get();
	   new B().get();   
	   }
	  }).start();
	  
	 }
	 static class A{
	  public void get(){
	   System.out.println("A"+Thread.currentThread().getName()+":  "+data.get());
	  }
	 }
	 static class B{
	  public void get(){
	   System.out.println("B"+Thread.currentThread().getName()+":  "+data.get());
	  }
	 }

	}