package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.Price;
import com.jw.ess.util.ex.EssException;

public interface IPriceDao {

	public void insertPrice(Price price) throws EssException;
	
	public List<Price> findPrice(Price price) throws EssException;
	
	public void deletePrice(Price price) throws EssException;
	
	public void updatePrice(Price price) throws EssException;
}
