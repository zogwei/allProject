/* 
 * Copyrights @ 2010，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：  设计模式Demo
 * 版         本:  V1.0.0.0
 * 作         者:  aben
 * 创建时间：  2010-9-8 下午07:36:01
 * 文   件  名：  com.zw.dp.visitor.AbstractVisitor.java
 */
package com.zw.dp.visitor;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.impl.ConcreteElement1;
import com.zw.dp.visitor.impl.ConcreteElement2;

/**
 * 说           明:	   访问者的标示接口。
 * 作           者： aben
 * 版          本:	 V1.x
 * 创建时间:	 2010-9-8 下午07:36:01
 */

public interface Visitor {
	//针对每个具体的接口元素都要有接口，这是使用二次分派，或者使用反射机制 
	void visitorElement(Element element);//反射机制实现时使用的接口
	
	void visitorElementByReflect(Element element);
	void visitorElement(ConcreteElement1 element);
	void visitorElement(ConcreteElement2 element);
}

