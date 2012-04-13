package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.Price;
import com.jw.ess.entity.Spec;
import com.jw.ess.util.ex.EssException;

/**
 * 规格表服务操作接口
 * 
 * @author j&w
 * 
 */
public interface IPriceService
{

	/**
	 * 插入新的规格
	 * 
	 * @param spec
	 * @throws EssException
	 */
	public void addPrice(Price price) throws EssException;

	
	public void deletePrice(Price price) throws EssException;
	public List<Price> findPrice(Price price) throws EssException;
	public void updatePrice(Price price) throws EssException;

}
