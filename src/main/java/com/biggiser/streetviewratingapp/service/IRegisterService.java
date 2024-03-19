package com.biggiser.streetviewratingapp.service;

import com.biggiser.streetviewratingapp.beans.UserBean;

public interface IRegisterService {
	public UserBean Login(String username,String password);//注册成功查询id
	public int Register(UserBean user);
}
