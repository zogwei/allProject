package com.jw.ess.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperator {
	public static String readToRequest(String filePath) {
		BufferedReader reader = null;
		StringBuilder xml = new StringBuilder();
		String line;
		try {
			reader = new BufferedReader((new FileReader(filePath)));
			while ((line = reader.readLine()) != null) {
				xml.append(line);
			}
			return xml.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void writeFromResponse(String filePath, String content) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
