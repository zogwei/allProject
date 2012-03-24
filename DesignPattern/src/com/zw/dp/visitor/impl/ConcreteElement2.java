/* 
 * Copyrights @ 2010��Tianyuan DIC Computer Co., Ltd.
 * ��Ŀ���ƣ�  ���ģʽDemo
 * ��         ��:  V1.0.0.0
 * ��         ��:  aben
 * ����ʱ�䣺  2010-9-8 ����08:35:58
 * ��   ��  ����  com.zw.dp.visitor.impl.ConcreteElement2.java
 */
package com.zw.dp.visitor.impl;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.Visitor;

/**
 * ˵           ��:	   ��
 * ��           �ߣ� aben
 * ��          ��:	 V1.x
 * ����ʱ��:	 2010-9-8 ����08:35:58
 */

public class ConcreteElement2 implements Element {

	@Override
	public void accept(Visitor visitor) 
	{
		System.out.println("ConcreteElement2 accept");
		visitor.visitorElement(this);
	}

}

