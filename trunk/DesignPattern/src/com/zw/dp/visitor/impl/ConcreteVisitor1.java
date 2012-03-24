/* 
 * Copyrights @ 2010��Tianyuan DIC Computer Co., Ltd.
 * ��Ŀ���ƣ�  ���ģʽDemo
 * ��         ��:  V1.0.0.0
 * ��         ��:  aben
 * ����ʱ�䣺  2010-9-8 ����07:38:59
 * ��   ��  ����  com.zw.dp.visitor.impl.ConcreteVisitor1.java
 */
package com.zw.dp.visitor.impl;

import com.zw.dp.visitor.Visitor;
import com.zw.dp.visitor.*;
/**
 * ˵           ��:	   ��
 * ��           �ߣ� aben
 * ��          ��:	 V1.x
 * ����ʱ��:	 2010-9-8 ����07:38:59
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

