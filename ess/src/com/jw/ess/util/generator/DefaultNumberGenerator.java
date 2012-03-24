package com.jw.ess.util.generator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultNumberGenerator implements NumberGenerator
{

	@Override
	public String generate()
	{
		return null;
	}
	
	public static String OrderNumberGenerate()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		Date date = new Date();
		return "dd" + format.format(date);
	}
public static void main(String[] args)
{
	System.out.println(OrderNumberGenerate());
}
}
