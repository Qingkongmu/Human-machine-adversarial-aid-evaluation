package com.biggiser.streetviewratingapp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.biggiser.streetviewratingapp.beans.UserBean;
import com.biggiser.streetviewratingapp.mapper.UserMapper;
import com.biggiser.streetviewratingapp.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private UserMapper um;


    @Override
    public UserBean Login(String username, String password) {
        return um.login(username, password);
    }
}
