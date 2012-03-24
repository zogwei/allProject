package com.jw.ess.converter;

import com.jw.ess.converter.constant.Commons;

public class DefaultXmlConverter<T> implements XmlConverter<T> {

	@Override
	public T fromXml(String xml) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toXml(T t) {
		StringBuilder xml = new StringBuilder();
		xml.append(Commons.XML_DECLARATION);
		xml.append(Commons.RESPONSE_START);
		xml.append(toContent(t));
		xml.append(Commons.RESPONSE_END);
		System.out.println(">>>>>>>>>return XML:"+xml.toString());
		return xml.toString();
	}

	protected String toContent(T t) {
		return "";
	}
}
