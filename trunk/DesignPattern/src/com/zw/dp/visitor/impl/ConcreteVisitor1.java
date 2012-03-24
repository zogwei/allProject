/* 
 * Copyrights @ 2010，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：  设计模式Demo
 * 版         本:  V1.0.0.0
 * 作         者:  aben
 * 创建时间：  2010-9-8 下午07:38:59
 * 文   件  名：  com.zw.dp.visitor.impl.ConcreteVisitor1.java
 */
package com.zw.dp.visitor.impl;

import com.zw.dp.visitor.Visitor;
import com.zw.dp.visitor.*;
/**
 * 说           明:	   。
 * 作           者： aben
 * 版          本:	 V1.x
 * 创建时间:	 2010-9-8 下午07:38:59
 */

public class ConcreteVisitor1 implements Visitor {

	public void visitorElement(Element element)
	{
		System.out.println("visit element");
	}
	
	public void visitorElement(ConcreteElement1 element)
	{
		System.out.println("visit ConcreteElement1");
	}
	
	public void visitorElement(ConcreteElement2 element)
	{
		System.out.println("visit ConcreteElement2");
	}
	
	public void visitorElementByReflect(Element element)
	{
		if(element instanceof ConcreteElement1)
		{
			System.out.println("visit ConcreteElement1");
		}
		else if(element instanceof ConcreteElement2)
		{
			System.out.println("visit ConcreteElement2");
		}
		else
		{
			System.out.println("visit Element");
		}
	}
} 

