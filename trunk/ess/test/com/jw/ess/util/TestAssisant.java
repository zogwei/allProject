package com.jw.ess.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestAssisant
{
	public static String getString(InputStream is) throws IOException
	{
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = reader.readLine()) != null)
		{
			builder.append(line);
		}
		return builder.toString();
	}
}
