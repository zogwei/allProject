package com.jw.ess.util;

public class TypeUtil
{
	public static int toInt(Object o)
	{
		return o == null ? 0 : Integer.valueOf(String.valueOf(o));
	}
}
