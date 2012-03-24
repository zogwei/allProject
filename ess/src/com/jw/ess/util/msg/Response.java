package com.jw.ess.util.msg;

/**
 * 响应实体类
 * 
 * @author j&w
 * 
 * @param <T>
 *            数据实体类
 */
public class Response<T>
{
	private T data;// 返回的数据

	private Message message;// 返回的消息

	public Response()
	{
	}

	public Response(Message message)
	{
		this(null, message);
	}

	public Response(T data, Message message)
	{
		this.data = data;
		this.message = message;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

	public Message getMessage()
	{
		return message;
	}

	public void setMessage(Message message)
	{
		this.message = message;
	}

	public String toString()
	{
		return "data=" + data + ", message=" + message;
	}
}
