package com.biggiser.streetviewratingapp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.biggiser.streetviewratingapp.beans.UserBean;
import com.biggiser.streetviewratingapp.mapper.UserMapper;
import com.biggiser.streetviewratingapp.service.IRegisterService;


@Service
public class RegisterServiceImpl implements IRegisterService {

    @Resource
    private UserMapper um;

    @Override
    public int Register(UserBean user) {
        try {
            return um.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public UserBean Login(String username, String password) {
        return um.login(username, password);
    }
}
