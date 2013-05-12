/**  
 * Class HibernateSessionFactory 
 * 
 * @author  lgsun
 * @version $Revision:1.0.0, $Date: 2011-3-30
 */
package com.lgsun.common;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("all")
public class HibernateSessionFactory
{

	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal threadLocal = new ThreadLocal();
	private static final Configuration cfg = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;

	public static Session currentSession() throws HibernateException
	{
		Session session = (Session) threadLocal.get();
//		if (session == null)
//		{
//			if (sessionFactory == null)
//			{
//				try
//				{
//					cfg.configure(CONFIG_FILE_LOCATION);
//					sessionFactory = cfg.buildSessionFactory();
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//			session = sessionFactory.openSession();
//			threadLocal.set(session);
//		}
		return session;
	}

	public static void closeSession() throws HibernateException
	{
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null)
		{
			session.close();
		}
	}

}
