/**  
 * Class UsersService 
 * 
 * @author  lgsun
 * @version $Revision:1.0.0, $Date: 2011-3-31
 */
package com.lgsun.service;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.lgsun.beans.User;
import com.lgsun.dao.UsersDAO;

@SuppressWarnings("all")
public class UsersService
{
	private UsersDAO usersDAO = new UsersDAO();

	public boolean CheckUser(String name, String password)
	{
		// String hql = "from User where name={0} and password={1}";
		// String hql = "from User";
		// HibernateTemplate template =usersDAO.getHibernateTemplate();
		// List temp = usersDAO.getHibernateTemplate().find(hql);
		String sql = "select * from Users where username=? and password=?";
		List temp = usersDAO.getJdbcTemplate().queryForList(sql, name, password);
		if ((!temp.isEmpty()) && (temp.size() == 1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
