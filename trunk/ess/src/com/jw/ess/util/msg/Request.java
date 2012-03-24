package com.jw.ess.util.msg;


/**
 * 请求实体类
 * 
 * @author j&w
 * 
 */
public class Request<T>
{
	private T condition;

	public T getCondition()
	{
		return condition;
	}

	public void setCondition(T condition)
	{
		this.condition = condition;
	}

	public String toString()
	{
		return condition != null ? condition.toString() : null;
	}
}
