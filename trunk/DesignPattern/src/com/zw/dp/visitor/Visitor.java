/* 
 * Copyrights @ 2010��Tianyuan DIC Computer Co., Ltd.
 * ��Ŀ���ƣ�  ���ģʽDemo
 * ��         ��:  V1.0.0.0
 * ��         ��:  aben
 * ����ʱ�䣺  2010-9-8 ����07:36:01
 * ��   ��  ����  com.zw.dp.visitor.AbstractVisitor.java
 */
package com.zw.dp.visitor;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.impl.ConcreteElement1;
import com.zw.dp.visitor.impl.ConcreteElement2;

/**
 * ˵           ��:	   �����ߵı�ʾ�ӿڡ�
 * ��           �ߣ� aben
 * ��          ��:	 V1.x
 * ����ʱ��:	 2010-9-8 ����07:36:01
 */

public interface Visitor {
	//���ÿ������Ľӿ�Ԫ�ض�Ҫ�нӿڣ�����ʹ�ö��η��ɣ�����ʹ�÷������ 
	void visitorElement(Element element);//�������ʵ��ʱʹ�õĽӿ�
	
	void visitorElementByReflect(Element element);
	void visitorElement(ConcreteElement1 element);
	void visitorElement(ConcreteElement2 element);
}

