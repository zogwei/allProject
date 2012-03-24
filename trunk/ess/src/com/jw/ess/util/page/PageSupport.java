package com.jw.ess.util.page;

import java.util.List;

import com.jw.ess.util.CommonConstant;

/**
 * 该类封装了分页功能
 * 
 * @author j&w
 * 
 * @param <T>
 *            结果实体类
 */
public class PageSupport<T>
{
	private List<T> result;// 查询结果

	// 总记录数
	private int count;

	// 请求页
	public int currentPage;

	public int pageSize;

	public PageSupport()
	{

	}

	public PageSupport(List<T> result, int count, int currentPage, int pageSize)
	{
		this.result = result;
		this.count = count;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	/**
	 * 是否存在第一页
	 * 
	 * @return true存在，false不存在
	 */
	public boolean hasFirst()
	{
		return currentPage > 1;
	}

	/**
	 * 是否存在上一页
	 * 
	 * @return true存在，false不存在
	 */
	public boolean hasPrev()
	{
		return currentPage > 1;
	}

	/**
	 * 是否存在下一页
	 * 
	 * @return true存在，false不存在
	 */
	public boolean hasNext()
	{
		return currentPage < getTotalPages();
	}

	/**
	 * 是否存在最后一页
	 * 
	 * @return true存在，false不存在
	 */
	public boolean hasLast()
	{
		return currentPage < getTotalPages();
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	public int getTotalPages()
	{
		if(pageSize<=0)pageSize=CommonConstant.PAGE_SIZE;
		return (count + pageSize - 1) / pageSize;
	}

	public List<T> getResult()
	{
		return result;
	}

	public void setResult(List<T> result)
	{
		this.result = result;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getCurrentPage()
	{
		if (currentPage > getTotalPages())
		{
			currentPage = getTotalPages();
		}
		if (currentPage < 1)
		{
			currentPage = 1;
		}
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int beginIndexOf()
	{
		return (getCurrentPage() - 1) * pageSize;
	}

	public String toString()
	{
		return "[result=" + result + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + ", count=" + count + "]";
	}
}
