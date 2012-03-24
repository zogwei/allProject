/* 
 * Copyrights @ 2010，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：  设计模式Demo
 * 版         本:  V1.0.0.0
 * 作         者:  aben
 * 创建时间：  2010-9-15 下午03:22:21
 * 文   件  名：  com.zw.dp.visitor.impl.ConcreteElement3.java
 */
package com.zw.dp.visitor.impl;

import com.zw.dp.visitor.Element;
import com.zw.dp.visitor.Visitor;

/**
 * 说           明:	   。
 * 作           者： aben
 * 版          本:	 V1.x
 * 创建时间:	 2010-9-15 下午03:22:21
 */

public class ConcreteElement3 implements Element {

	@Override
	public void accept(Visitor visitor) {
		System.out.println("ConcreteElement3 accept");
		visitor.visitorElement(this);
	}

}

