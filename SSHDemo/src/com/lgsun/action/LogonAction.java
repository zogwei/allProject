/**  
 * Class LogOnAction 
 * 
 * @author  lgsun
 * @version $Revision:1.0.0, $Date: 2011-3-31
 */
package com.lgsun.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.lgsun.beans.User;
import com.lgsun.dao.UsersDAO;
import com.lgsun.service.UsersService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("all")
public class LogonAction extends ActionSupport
{
	private UsersDAO usersDAO;
	private List<User> users;
	private String name;
	private String password;
	private String errorMsg;

	public String execute()
	{
		System.out.println("execute:" + name);
		users = usersDAO.getUsersByHibernate();

		// HttpServletRequest request = ServletActionContext.getRequest();
		// HttpServletResponse response = ServletActionContext.getResponse();
		// HttpSession session = request.getSession();
		// session.setAttribute("ssStr", "session value");
		// request.setAttribute("reqStr", "request value");

		return SUCCESS;
	}

	public void validate()
	{
		UsersService usersService = new UsersService();
		if (name == null || "".equals(name))
		{
			errorMsg = "The user name is null.";
		}
		else if (password == null || "".equals(password))
		{
			errorMsg = "The password is null.";
		}
		else if (usersService.CheckUser(name, password))
		{
			errorMsg = "Logon successfully";
		}
		else
		{
			errorMsg = "The user's name or password is error!";
		}
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<User> getUsers()
	{
		return users;
	}

	public void setUsers(List<User> users)
	{
		this.users = users;
	}

	public UsersDAO getUsersDAO()
	{
		return usersDAO;
	}

	public void setUsersDAO(UsersDAO usersDAO)
	{
		this.usersDAO = usersDAO;
	}
}
