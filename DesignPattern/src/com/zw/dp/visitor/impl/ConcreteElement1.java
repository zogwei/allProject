/* 
 * Copyrights @ 2010，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：  设计模式Demo
 * 版         本:  V1.0.0.0
 * 作         者:  aben
 * 创建时间：  2010-9-8 下午07:53:45
 * 文   件  名：  com.zw.dp.visitor.impl.ConcreteElement1.java
 */
package com.zw.dp.visitor.impl;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.Visitor;

/**
 * 说           明:	   具体被访问者。
 * 作           者： aben
 * 版          本:	 V1.x
 * 创建时间:	 2010-9-8 下午07:53:45
 */

public class ConcreteElement1 implements Element {

	@Override
	public void accept(Visitor visitor) 
	{
		System.out.println("ConcreteElement1 accept");
		visitor.visitorElement(this);
	}

}

