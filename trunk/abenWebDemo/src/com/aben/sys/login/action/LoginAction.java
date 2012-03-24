package com.aben.sys.login.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.aben.sys.login.buz.LoginServ;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends  ActionSupport{
	
	 @Autowired
	 LoginServ loginServ ;
	 public String loginCheck()
	 {
		 return "success";
	 }
}
