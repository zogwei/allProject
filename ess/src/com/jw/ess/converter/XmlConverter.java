package com.jw.ess.converter;

public interface XmlConverter<T> {
	String toXml(T t);
	T fromXml(String xml);
}
