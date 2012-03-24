package com.jw.ess.util.msg;

/**
 * 消息实体类
 * 
 * @author j&w
 * 
 */
public class Message
{
	private int code;// 返回码

	private String text;// 文本描述

	private int state;// 1-成功，2-失败

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public String toString()
	{
		return "[code=" + code + ", text=" + text + ", state=" + state + "]";
	}
}
