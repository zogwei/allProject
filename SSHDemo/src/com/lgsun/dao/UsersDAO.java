/**  
 * Class UsersDAO 
 * 
 * @author  lgsun
 * @version $Revision:1.0.0, $Date: 2011-3-31
 */
package com.lgsun.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.lgsun.beans.User;
import com.lgsun.common.BaseDaoHibernate;
import com.lgsun.common.HibernateSessionFactory;

@SuppressWarnings("all")
public class UsersDAO extends BaseDaoHibernate
{/*
 * public User getUser(String userName) throws HibernateException { Session
 * session = null; Transaction tx = null; User users = null; try { session =
 * HibernateSessionFactory.currentSession(); tx = session.beginTransaction();
 * Query query = session.createQuery("from User where name=?");
 * query.setString(0, userName.trim()); users = (User) query.uniqueResult();
 * query = null; tx.commit(); } catch (HibernateException e) { if (tx != null) {
 * tx.rollback(); } e.printStackTrace(); } finally {
 * HibernateSessionFactory.closeSession(); } return users; }
 * 
 * public List<User> getUsers() throws HibernateException { Session session =
 * null; Transaction tx = null; List<User> users = null; try { session =
 * HibernateSessionFactory.currentSession(); tx = session.beginTransaction();
 * Query query = session.createQuery("from User"); users = query.list(); query =
 * null; tx.commit(); } catch (HibernateException e) { if (tx != null) {
 * tx.rollback(); } e.printStackTrace(); } finally {
 * HibernateSessionFactory.closeSession(); } return users; }
 */
	public List<User> getUsersByJDBC() throws HibernateException
	{
		String sql = "select * from users";
		System.out.println(getJdbcTemplate());
		List<Map<String, Object>> temp = getJdbcTemplate().queryForList(sql);
		List<User> users = new ArrayList<User>();
		for (Map<String, Object> userMap : temp)
		{
			User newUser = new User();
			newUser.setId(userMap.get("id").toString());
			newUser.setId(userMap.get("username").toString());
			newUser.setId(userMap.get("password").toString());
			newUser.setId(userMap.get("email").toString());
			users.add(newUser);
		}

		return users;
	}

	public List<User> getUsersByHibernate()
	{
		SessionFactory sessionFactory = this.getSessionFactory();
		String sql = "from User";
		HibernateTemplate hib = getHibernateTemplate();
		List<User> temp = getHibernateTemplate().find(sql);
		return temp;
	}

}
