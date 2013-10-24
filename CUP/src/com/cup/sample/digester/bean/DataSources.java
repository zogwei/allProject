package com.cup.sample.digester.bean;

import java.util.ArrayList;
import java.util.List;

public class DataSources {

	List<DataSource> dataSourceList = new ArrayList<DataSource>();
	
	public void addDataSource(DataSource item){
		dataSourceList.add(item);
	}

	@Override
	public String toString() {
		return "DataSources [dataSourceList=" + dataSourceList + "]";
	}
	
	
}
