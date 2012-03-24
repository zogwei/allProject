/* 
 * Copyrights @ 2010��Tianyuan DIC Computer Co., Ltd.
 * ��Ŀ���ƣ�  ���ģʽDemo
 * ��         ��:  V1.0.0.0
 * ��         ��:  aben
 * ����ʱ�䣺  2010-9-8 ����07:53:45
 * ��   ��  ����  com.zw.dp.visitor.impl.ConcreteElement1.java
 */
package com.zw.dp.visitor.impl;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.Visitor;

/**
 * ˵           ��:	   ���屻�����ߡ�
 * ��           �ߣ� aben
 * ��          ��:	 V1.x
 * ����ʱ��:	 2010-9-8 ����07:53:45
 */

public class ConcreteElement1 implements Element {

	@Override
	public void accept(Visitor visitor) 
	{
		System.out.println("ConcreteElement1 accept");
		visitor.visitorElement(this);
	}

}

