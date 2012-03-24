/* 
 * Copyrights @ 2010��Tianyuan DIC Computer Co., Ltd.
 * ��Ŀ���ƣ�  ���ģʽDemo
 * ��         ��:  V1.0.0.0
 * ��         ��:  aben
 * ����ʱ�䣺  2010-9-8 ����08:42:36
 * ��   ��  ����  com.zw.dp.visitor.impl.Main.java
 */
package com.zw.dp.visitor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.Visitor;

/**
 * ˵           ��:	   ��
 * ��           �ߣ� aben
 * ��          ��:	 V1.x
 * ����ʱ��:	 2010-9-8 ����08:42:36
 */

public class Main {

	/**
	 * ����˵����   ��
	 * ��          �ߣ�   aben
	 * ��          ����    V1.x
	 * ����ʱ�䣺   2010-9-8 ����08:42:36
	 * @param args 
	 */
	public static void main(String[] args) {

		List<Element> elementContainer = new ArrayList<Element>();
		Visitor visitor = new ConcreteVisitor1();
		elementContainer.add(new ConcreteElement1());
		elementContainer.add(new ConcreteElement2());
		Iterator iterator = elementContainer.iterator();
		
		System.out.println(">>>vistorAllelemntByTwiceAllot");
		vistorAllelemntByTwiceAllot(iterator,visitor);
		
		System.out.println(">>>vistorAllelemntByOneAllotAndReflect");
		vistorAllelemntByOneAllotAndReflect(iterator,visitor);
	}
	
	private static void vistorAllelemntByTwiceAllot(Iterator iterator,Visitor visitor)
	{
		Element tempElement = null;
		while(iterator.hasNext())
		{
			tempElement = (Element)iterator.next();
			tempElement.accept(visitor);
		}
	}
	
	private static void vistorAllelemntByOneAllotAndReflect(Iterator iterator,Visitor visitor)
	{
		Element tempElement = null;
		while(iterator.hasNext())
		{
			tempElement = (Element)iterator.next();
			visitor.visitorElementByReflect(tempElement);
		}
	}
	
	

}

