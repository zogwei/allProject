package com.lgsun.common;

import java.io.Serializable;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("all") 
public class BaseDaoHibernate extends HibernateDaoSupport implements Dao, ApplicationContextAware
{

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
	{
		if (BaseDaoHibernate.applicationContext == null)
		{
			BaseDaoHibernate.applicationContext = applicationContext;
		}
	}

	/* 
	 * @see com.lgsun.common.Dao#saveObject(java.lang.Object)
	 */
	public void saveObject(Object o)
	{
		getHibernateTemplate().save(o);
	}
	
	/* 
	 * @see com.lgsun.common.Dao#getObject(java.lang.Class, java.io.Serializable)
	 */
	public Object getObject(Class clazz, Serializable id)
	{
		Object o = getHibernateTemplate().get(clazz, id);

		if (o == null)
		{
			throw new ObjectRetrievalFailureException(clazz, id);
		}

		return o;
	}

	/*
	 * @see com.lgsun.common.Dao#getObjects(java.lang.Class)
	 */
	public List getObjects(Class clazz)
	{
		return getHibernateTemplate().loadAll(clazz);
	}

	/* 
	 * @see com.lgsun.common.Dao#removeObject(java.lang.Class, java.io.Serializable)
	 */
	public void removeObject(Class clazz, Serializable id)
	{
		getHibernateTemplate().delete(getObject(clazz, id));
	}

	public static JdbcTemplate getJdbcTemplate()
	{
		DataSource dataSource = (DriverManagerDataSource) BaseDaoHibernate.applicationContext.getBean("springappDataSource");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}
}
