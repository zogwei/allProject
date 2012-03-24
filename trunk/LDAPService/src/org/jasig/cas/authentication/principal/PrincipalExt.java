package org.jasig.cas.authentication.principal;

import java.util.Date;

/**
 * 权限扩展
 * @author houdc
 *	用于修改用户密码
 */
public interface PrincipalExt {

	Date getUpdatePsdDate();
	
	void setUpdatePsdDate(Date nd);//重置修改时间
}
