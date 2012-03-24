package com.jw.ess.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAssisant
{
	private static final BeanFactory beanFactory;
	
	static
	{
		beanFactory = new ClassPathXmlApplicationContext("spring.xml");
		System.out.println(beanFactory);
	}

	public static Object getBean(String beanName)
	{
		return beanFactory.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clazz)
	{
		return beanFactory.getBean(clazz);
	}
	public static void main(String[] args) {
		
		
	}
}
