package com.biggiser.streetviewratingapp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.biggiser.streetviewratingapp.beans.BaseScore;
import com.biggiser.streetviewratingapp.beans.Init;
import com.biggiser.streetviewratingapp.beans.LocalRandomForest;
import com.biggiser.streetviewratingapp.beans.LogBean;
import com.biggiser.streetviewratingapp.beans.UserRatingBean;
import com.biggiser.streetviewratingapp.mapper.UserMapper;
import com.biggiser.streetviewratingapp.service.IRatingService;

import weka.core.Instances;
import weka.core.SerializationHelper;

@Service
public class RatingServiceImpl implements IRatingService {
    @Resource
    private UserMapper um;

    @Override
    public boolean RandomForestTraining(UserRatingBean userRating) {
        System.out.println("training-----");
        int num = userRating.getScoredImg().size();
        if (num < 50) {//张数不够
            System.out.println(num);
            System.out.println("traing not enough scored images---at least 50");
            return false;
        }

        System.out.println("building trainging set----");

        Instances safetyInstances = LocalRandomForest.BuildInstances(userRating, "safety");
        Instances comfortInstances = LocalRandomForest.BuildInstances(userRating, "comfort");
        Instances convenienceInstances = LocalRandomForest.BuildInstances(userRating, "convenience");
        Instances attractivenessInstances = LocalRandomForest.BuildInstances(userRating, "attractiveness");

        //设置类别标签
        safetyInstances.setClassIndex(Init.featureDimension);
        comfortInstances.setClassIndex(Init.featureDimension);
        convenienceInstances.setClassIndex(Init.featureDimension);
        attractivenessInstances.setClassIndex(Init.featureDimension);

        System.out.println("building trainging set----end");

        System.out.println("trainging randomForest Model starting----");
        try {
            userRating.getsafetyForest().setOptions(weka.core.Utils.splitOptions("-I 50 -P 66"));
            userRating.getcomfortForest().setOptions(weka.core.Utils.splitOptions("-I 50 -P 66"));
            userRating.getconvenienceForest().setOptions(weka.core.Utils.splitOptions("-I 50 -P 66"));
            userRating.getattractivenessForest().setOptions(weka.core.Utils.splitOptions("-I 50 -P 66"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            userRating.getsafetyForest().buildClassifier(safetyInstances);
            userRating.getcomfortForest().buildClassifier(comfortInstances);
            userRating.getconvenienceForest().buildClassifier(convenienceInstances);
            userRating.getattractivenessForest().buildClassifier(attractivenessInstances);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("trainging randomForest Model ending----");

        System.out.println("randomForest Model Serialization----");

        //String basePath="F:\\myStudy\\graduateWork\\WuhanStreetview\\RFModels\\";
//		String basePath="/data/RFModels/";//store models in disk 
        String basePath = "F:\\RFModels\\";//store models in disk
        try {
            SerializationHelper.write(basePath + userRating.getId() + "_RFModel_safetyForest", userRating.getsafetyForest());
            SerializationHelper.write(basePath + userRating.getId() + "_RFModel_comfortForest", userRating.getcomfortForest());
            SerializationHelper.write(basePath + userRating.getId() + "_RFModel_convenienceForest", userRating.getconvenienceForest());
            SerializationHelper.write(basePath + userRating.getId() + "_RFModel_attractivenessForest", userRating.getattractivenessForest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("randomForest Model Serialization ending----");

        userRating.setCurNum(0);
        return true;
    }

    @Override
    public void RandomForestTesting(UserRatingBean userRating, int imgId) {

        System.out.println("building testing dataset-----");
        Instances psafetyData = LocalRandomForest.BuildInstances("safety", imgId);
        Instances pcomfortData = LocalRandomForest.BuildInstances("comfort", imgId);
        Instances pconvenienceData = LocalRandomForest.BuildInstances("convenience", imgId);
        Instances pattractivenessData = LocalRandomForest.BuildInstances("attractiveness", imgId);

        psafetyData.setClassIndex(Init.featureDimension);
        pcomfortData.setClassIndex(Init.featureDimension);
        pconvenienceData.setClassIndex(Init.featureDimension);
        pattractivenessData.setClassIndex(Init.featureDimension);

        System.out.println("building testing dataset end-----");

        System.out.println("testing dataset-----");
        try {
            userRating.setPsafetyValue(userRating.getsafetyForest().classifyInstance(psafetyData.get(0)));
            userRating.setPcomfortValue(userRating.getcomfortForest().classifyInstance(pcomfortData.get(0)));
            userRating.setPconvenienceValue(userRating.getconvenienceForest().classifyInstance(pconvenienceData.get(0)));
            userRating.setPattractivenessValue(userRating.getattractivenessForest().classifyInstance(pattractivenessData.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("testing dataset end-----");
    }

    @Override
    public void AddScore(int userId, String imgName, BaseScore userScore, BaseScore RFPrediction) {
        LogBean log = new LogBean(null, userId, imgName, userScore.getsafetyScore(), userScore.getcomfortScore(), userScore.getconvenienceScore(), userScore.getattractivenessScore(), RFPrediction.getsafetyScore(), RFPrediction.getcomfortScore(), RFPrediction.getconvenienceScore(), RFPrediction.getattractivenessScore());
        try {
            um.insertLog(log);
        } catch (Exception e) {
            System.out.println("insert log fail!");
            e.printStackTrace();
        }
    }
}
