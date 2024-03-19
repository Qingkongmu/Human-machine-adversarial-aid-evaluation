package com.biggiser.streetviewratingapp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.biggiser.streetviewratingapp.beans.Init;
import com.biggiser.streetviewratingapp.mapper.UserMapper;
import com.biggiser.streetviewratingapp.service.IRandomImgService;

@Service
public class RandomImgServiceImpl implements IRandomImgService {

    @Resource
    private UserMapper um;
    @Resource
    private Init init;

    @Override
    public int getRandomImg() throws Exception {
        System.out.println("getRandomImg----");
        System.out.println("----int"+Init.imgScores.size());
      /*  init.init1();*/
        int random = (int) (Math.random() * Init.imgScores.size());

        return random;
    }

}
