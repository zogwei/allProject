package com.jw.ess.util.page;

import com.jw.ess.util.CommonConstant;

/**
 * 该类封装了分页请求参数
 * @author j&w
 */
public class Page {
	//请求页数
	private int currentPage;
	//每页请求记录条数
	private int pageSize = CommonConstant.PAGE_SIZE;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
