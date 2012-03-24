/* 
 * Copyrights @ 2010，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：  设计模式Demo
 * 版         本:  V1.0.0.0
 * 作         者:  aben
 * 创建时间：  2010-9-7 下午04:59:04
 * 文   件  名：  com.zw.dp.visitor.AbstractElement.java
 */
package com.zw.dp.visitor;

/**
 * 说          明:	  被访问者抽象元素 。<br>
 * 作          者： aben<br>
 * 版          本:	 V1.x<br>
 * 创建时间:	 2010-9-7 下午04:59:04
 */

public interface Element {

	/**
	 * 
	 * 方法说明：   抽象访问角色。
	 * 作          者：   aben
	 * 版          本：    V1.x
	 * 创建时间：   2010-9-7 下午05:00:34
	 * @param visitor 抽象访问者
	 */
	void accept(Visitor visitor);
}

