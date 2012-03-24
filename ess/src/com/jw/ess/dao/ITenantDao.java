package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.Tenant;
import com.jw.ess.util.ex.EssException;

public interface ITenantDao {
	void insertTenant(Tenant tenant) throws EssException;

	List<Tenant> findAllTenants() throws EssException;
	
	List<Tenant> findTenantsByName(String tenantName)throws EssException;
	
	Tenant findTenantsById(int id)throws EssException;
	
	String findTenantName(Tenant tenant) throws EssException;
	
	void updateTenant(Tenant tenant)throws EssException;
}
