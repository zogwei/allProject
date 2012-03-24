/* 
 * Copyrights @ 2010，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：  设计模式Demo
 * 版         本:  V1.0.0.0
 * 作         者:  aben
 * 创建时间：  2010-9-8 下午08:42:36
 * 文   件  名：  com.zw.dp.visitor.impl.Main.java
 */
package com.zw.dp.visitor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.Visitor;

/**
 * 说           明:	   。
 * 作           者： aben
 * 版          本:	 V1.x
 * 创建时间:	 2010-9-8 下午08:42:36
 */

public class Main {

	/**
	 * 方法说明：   。
	 * 作          者：   aben
	 * 版          本：    V1.x
	 * 创建时间：   2010-9-8 下午08:42:36
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

