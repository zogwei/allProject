/**  
 * Class Users 
 * 
 * @author  lgsun
 * @version $Revision:1.0.0, $Date: 2011-3-30
 */
package com.lgsun.beans;

public class User
{
	private String	id;
	private String	name;
	private String	password;
	private String	email;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
