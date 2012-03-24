/* 
 * Copyrights @ 2010，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：  设计模式Demo
 * 版         本:  V1.0.0.0
 * 作         者:  aben
 * 创建时间：  2010-9-8 下午08:35:58
 * 文   件  名：  com.zw.dp.visitor.impl.ConcreteElement2.java
 */
package com.zw.dp.visitor.impl;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.Visitor;

/**
 * 说           明:	   。
 * 作           者： aben
 * 版          本:	 V1.x
 * 创建时间:	 2010-9-8 下午08:35:58
 */

public class ConcreteElement2 implements Element {

	@Override
	public void accept(Visitor visitor) 
	{
		System.out.println("ConcreteElement2 accept");
		visitor.visitorElement(this);
	}

}

