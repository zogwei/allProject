/* 
 * Copyrights @ 2010��Tianyuan DIC Computer Co., Ltd.
 * ��Ŀ���ƣ�  ���ģʽDemo
 * ��         ��:  V1.0.0.0
 * ��         ��:  aben
 * ����ʱ�䣺  2010-9-7 ����04:59:04
 * ��   ��  ����  com.zw.dp.visitor.AbstractElement.java
 */
package com.zw.dp.visitor;

/**
 * ˵          ��:	  �������߳���Ԫ�� ��<br>
 * ��          �ߣ� aben<br>
 * ��          ��:	 V1.x<br>
 * ����ʱ��:	 2010-9-7 ����04:59:04
 */

public interface Element {

	/**
	 * 
	 * ����˵����   ������ʽ�ɫ��
	 * ��          �ߣ�   aben
	 * ��          ����    V1.x
	 * ����ʱ�䣺   2010-9-7 ����05:00:34
	 * @param visitor ���������
	 */
	void accept(Visitor visitor);
}

