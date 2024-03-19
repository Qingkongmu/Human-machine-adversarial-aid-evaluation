package com.biggiser.streetviewratingapp.beans;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.trees.RandomForest;

public class UserRatingBean {
    private Integer id;//用户id
    private List<BaseScore> scoredImg;//不包含特征列
    private Integer curNum;//当前打了几张街景
    private RandomForest safetyForest;
    private RandomForest comfortForest;
    private RandomForest convenienceForest;
    private RandomForest attractivenessForest;
    //预测值
    private double psafetyValue;
    private double pcomfortValue;
    private double pconvenienceValue;
    private double pattractivenessValue;

    public UserRatingBean() {
        super();
        this.id = -1;
        scoredImg = new ArrayList<>();
        curNum = 0;
        safetyForest = new RandomForest();
        comfortForest = new RandomForest();
        convenienceForest = new RandomForest();
        attractivenessForest = new RandomForest();

        psafetyValue = -1;
        pcomfortValue = -1;
        pconvenienceValue = -1;
        pattractivenessValue = -1;
    }

    public UserRatingBean(Integer id) {
        super();
        this.id = id;
        scoredImg = new ArrayList<>();
        curNum = 0;
        safetyForest = new RandomForest();
        comfortForest = new RandomForest();
        convenienceForest = new RandomForest();
        attractivenessForest = new RandomForest();

        psafetyValue = -1;
        pcomfortValue = -1;
        pconvenienceValue = -1;
        pattractivenessValue = -1;
    }

    public double getPsafetyValue() {
        return psafetyValue;
    }

    public void setPsafetyValue(double psafetyValue) {
        this.psafetyValue = psafetyValue;
    }

    public double getPcomfortValue() {
        return pcomfortValue;
    }

    public void setPcomfortValue(double pcomfortValue) {
        this.pcomfortValue = pcomfortValue;
    }

    public double getPconvenienceValue() {
        return pconvenienceValue;
    }

    public void setPconvenienceValue(double pconvenienceValue) {
        this.pconvenienceValue = pconvenienceValue;
    }

    public double getPattractivenessValue() {
        return pattractivenessValue;
    }

    public void setPattractivenessValue(double pattractivenessValue) {
        this.pattractivenessValue = pattractivenessValue;
    }

    public RandomForest getsafetyForest() {
        return safetyForest;
    }

    public void setsafetyForest(RandomForest safetyForest) {
        this.safetyForest = safetyForest;
    }

    public RandomForest getcomfortForest() {
        return comfortForest;
    }

    public void setcomfortForest(RandomForest comfortForest) {
        this.comfortForest = comfortForest;
    }

    public RandomForest getconvenienceForest() {
        return convenienceForest;
    }

    public void setconvenienceForest(RandomForest convenienceForest) {
        this.convenienceForest = convenienceForest;
    }

    public RandomForest getattractivenessForest() {
        return attractivenessForest;
    }

    public void setattractivenessForest(RandomForest attractivenessForest) {
        this.attractivenessForest = attractivenessForest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<BaseScore> getScoredImg() {
        return scoredImg;
    }

    public void setScoredImg(List<BaseScore> scoredImg) {
        this.scoredImg = scoredImg;
    }

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    @Override
    public String toString() {
        return String.format("UserRatingBean [id=%s, curNum=%s]", id, curNum);
    }
}
