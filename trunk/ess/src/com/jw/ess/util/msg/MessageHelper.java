package com.jw.ess.util.msg;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jw.ess.util.PathUtil;
import com.jw.ess.util.ex.EssException;

/**
 * Message帮助类
 * 
 * @author j&w
 * 
 */
public class MessageHelper
{
	private static final Log logger = LogFactory.getLog(MessageHelper.class);

	private static Map<Integer, String> messageMap;

	public static final int MESSAGE_SUCCESS_STATE = 1;

	public static final int MESSAGE_FAILED_STATE = 2;
	static
	{
		messageMap = new HashMap<Integer, String>();

		Properties properties = new Properties();
		final String messageFile = "com/jw/ess/util/ex/message.xml";
		try
		{
			properties.loadFromXML(new FileInputStream(PathUtil.getClassPath()
					+ messageFile));
			for (Entry<Object, Object> entry : properties.entrySet())
			{
				messageMap.put(Integer.valueOf(String.valueOf(entry.getKey())),
						String.valueOf(entry.getValue()));
			}
			messageMap = Collections.unmodifiableMap(messageMap);
		}
		catch (IOException e)
		{
			logger.error("failed load message file : "
					+ (PathUtil.getClassPath() + messageFile), e);
		}
	}

	public static Message createSuccessMessage(int messageCode)
	{
		return createMessage(messageCode, MESSAGE_SUCCESS_STATE);
	}

	public static Message createFailedMessage(EssException e)
	{
		return createMessage(e.getErrorCode(), MESSAGE_FAILED_STATE);
	}

	private static Message createMessage(int messageCode, int state)
	{
		Message m = new Message();
		m.setCode(messageCode);
		m.setState(state);
		m.setText(getMessageText(messageCode));
		return m;
	}

	public static String getMessageText(int messageCode)
	{
		return messageMap.get(messageCode);
	}

	public static void main(String[] args)
	{
		System.out.println(getMessageText(13001));
	}
}
