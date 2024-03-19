package com.biggiser.streetviewratingapp.service;

import com.biggiser.streetviewratingapp.beans.BaseScore;
import com.biggiser.streetviewratingapp.beans.UserRatingBean;


public interface IRatingService {
    public boolean RandomForestTraining(UserRatingBean userRating);//训练

    public void RandomForestTesting(UserRatingBean userRating, int imgId);//预测

    public void AddScore(int userId, String imgName, BaseScore userScore, BaseScore RFPrediction);//添加打分数据
}
