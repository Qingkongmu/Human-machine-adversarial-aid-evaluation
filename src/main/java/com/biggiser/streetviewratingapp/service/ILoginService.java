package com.biggiser.streetviewratingapp.service;

import com.biggiser.streetviewratingapp.beans.UserBean;

public interface ILoginService {
    public UserBean Login(String username, String password);
}
