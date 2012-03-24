package com.jw.ess.util.ex;

/**
 * 该类表示系统统一的异常类
 * 
 * @author j&w
 */
public class EssException extends Exception
{
	private static final long serialVersionUID = 321456278909L;

	private int errorCode;// 错误码

	/**
	 * 构造函数
	 * 
	 * @param msg
	 *            错误信息
	 * @param errorCode
	 *            错误码
	 */
	public EssException(String msg, int errorCode)
	{
		super(msg);
		this.errorCode = errorCode;
	}

	/**
	 * 构造函数
	 * 
	 * @param t
	 *            原始异常
	 * @param errorCode
	 *            错误码
	 */
	public EssException(Throwable t, int errorCode)
	{
		super(t);
		this.errorCode = errorCode;
	}

	/**
	 * setter
	 * 
	 * @param errorCode
	 *            错误码
	 */
	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * getter
	 * 
	 * @return 错误码
	 */
	public int getErrorCode()
	{
		return errorCode;
	}

}
