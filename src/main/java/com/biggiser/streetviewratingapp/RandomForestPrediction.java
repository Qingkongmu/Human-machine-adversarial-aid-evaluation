package com.biggiser.streetviewratingapp;

import com.biggiser.streetviewratingapp.beans.*;
import com.biggiser.streetviewratingapp.service.IRandomImgService;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.distribution.TDistribution;

public class RandomForestPrediction {


    public static void main(String[] args) throws Exception {

        // 加载已经训练好的随机森林模型
        RandomForest safetyForest = loadModel("F:\\RFModels\\1_RFModel_safetyForest");
        RandomForest comfortForest = loadModel("F:\\RFModels\\1_RFModel_comfortForest");
        RandomForest convenienceForest = loadModel("F:\\RFModels\\1_RFModel_convenienceForest");
        RandomForest attractivenessForest = loadModel("F:\\RFModels\\1_RFModel_attractivenessForest");
        UserRatingBean curUserScoredImg = new UserRatingBean();
        curUserScoredImg.setCurNum(1);

        // 构造一个用于预测的实例
//        Instance instance = buildInstance(/* 构造实例的具体方法 */);
        List<BaseScore> bss = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Double safetyScore = (Math.random() * 51 + 50);
            Double comfortScore = (Math.random() * 51 + 50);
            Double convenienceScore = (Math.random() * 51 + 50);
            Double attractivenessScore = (Math.random() * 51 + 50);
            String filename = i + 200 + ".jpg";
            BaseScore sc = new BaseScore(safetyScore, comfortScore, convenienceScore, attractivenessScore, filename);
            bss.add(sc);
        }
//        Double safetyScore = (Math.random() * 51 + 50);
//        Double comfortScore = (Math.random() * 51 + 50);
//        Double convenienceScore = (Math.random() * 51 + 50);
//        Double attractivenessScore = (Math.random() * 51 + 50);
//        String filename = 200 + ".jpg";
//        BaseScore sc = new BaseScore(safetyScore, comfortScore, convenienceScore, attractivenessScore, filename);
//        bss.add(sc);
        curUserScoredImg.setScoredImg(bss);
        Init init = new Init();
        Instances safetyInstances = LocalRandomForest.BuildInstances(curUserScoredImg, "safety");
        Instances comfortInstances = LocalRandomForest.BuildInstances(curUserScoredImg, "comfort");
        Instances convenienceInstances = LocalRandomForest.BuildInstances(curUserScoredImg, "convenience");
        Instances attractivenessInstances = LocalRandomForest.BuildInstances(curUserScoredImg, "attractiveness");
//        Instances safetyInstances = LocalRandomForest.BuildInstances("safety", 200);
//        Instances comfortInstances = LocalRandomForest.BuildInstances("comfort", 200);
//        Instances convenienceInstances = LocalRandomForest.BuildInstances("convenience", 200);
//        Instances attractivenessInstances = LocalRandomForest.BuildInstances("attractiveness", 200);
        //设置类别标签
        safetyInstances.setClassIndex(Init.featureDimension);
        comfortInstances.setClassIndex(Init.featureDimension);
        convenienceInstances.setClassIndex(Init.featureDimension);
        attractivenessInstances.setClassIndex(Init.featureDimension);

        // 进行预测并获取每个元素的权重比值
//        String s =  safetyForest.computeAverageImpurityDecreasePerAttribute();
//        System.out.println(s);
        double[][] safetyWeights = getFeatureWeights(safetyForest, safetyInstances);
        double[][] comfortWeights = getFeatureWeights(comfortForest, comfortInstances);
        double[][] convenienceWeights = getFeatureWeights(convenienceForest, convenienceInstances);
        double[][] attractivenessWeights = getFeatureWeights(attractivenessForest, attractivenessInstances);

        //输出权重比值
        for (int i = 0; i < safetyWeights.length; i++) {
            System.out.println("Safety Feature " + (i + 1) + " Weight: " + safetyWeights[i][0] + " P-Value: " + safetyWeights[i][1]);
        }

        for (int i = 0; i < comfortWeights.length; i++) {
            System.out.println("Comfort Feature " + (i + 1) + " Weight: " + comfortWeights[i][0] + " P-Value: " + comfortWeights[i][1]);
        }

        for (int i = 0; i < convenienceWeights.length; i++) {
            System.out.println("Convenience Feature " + (i + 1) + " Weight: " + convenienceWeights[i][0] + " P-Value: " + convenienceWeights[i][1]);
        }

        for (int i = 0; i < attractivenessWeights.length; i++) {
            System.out.println("Attractiveness Feature " + (i + 1) + " Weight: " + attractivenessWeights[i][0] + " P-Value: " + attractivenessWeights[i][1]);
        }
    }

    private static RandomForest loadModel(String path) {
        try {
            return (RandomForest) SerializationHelper.read(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static double[][] getFeatureWeights(RandomForest forest, Instances instances) {
        try {
            int numFeatures = instances.numAttributes() - 1; // 获取特征的数量（减去类别属性）
            double[][] importance = new double[numFeatures][2];

            for (int i = 0; i < numFeatures; i++) {
                Attribute attribute = instances.attribute(i);
                double[] featureValues = instances.attributeToDoubleArray(i);
                double correlation = calculatePearsonCorrelation(featureValues, instances.classAttribute(), forest, instances);
                importance[i][0] = correlation; // 不使用绝对值以保持一致性（相关性可以为正或负）

                // 计算P值
                AttributeStats attributeStats = instances.attributeStats(i);

                if (!attribute.isNumeric()) {
                    double pValue = attributeStats.nominalCounts[0] / (double) instances.numInstances();
                    importance[i][1] = pValue;
                } else {
                    double pValue = calculateNumericPValue(featureValues, instances.classAttribute(), forest, instances);
                    importance[i][1] = pValue;
                }
            }

            return importance;
        } catch (Exception e) {
            e.printStackTrace();
            return new double[0][0];
        }
    }



    private static double calculatePearsonCorrelation(double[] x, Attribute classAttribute, RandomForest forest, Instances instances) {
        if (x.length != 50) {
            throw new IllegalArgumentException("Input array length must be equal to the number of trees in the random forest");
        }

        double[] predictions = new double[x.length];

        for (int t = 0; t < x.length; t++) {
//            Instance inst = new DenseInstance(2);
//            inst.setValue(0, x[t]);
//            inst.setMissing(1);
            try {
                predictions[t] = forest.classifyInstance(instances.get(t));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXSquare = 0;
        double sumYSquare = 0;

        for (int i = 0; i < x.length; i++) {
            sumX += x[i];
            sumY += predictions[i];
            sumXY += x[i] * predictions[i];
            sumXSquare += x[i] * x[i];
            sumYSquare += predictions[i] * predictions[i];
        }

        int n = x.length;

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumXSquare - sumX * sumX) * (n * sumYSquare - sumY * sumY));

        if (denominator == 0.0) {
            return 0.0; // 避免除零错误
        }

        return numerator / denominator;
    }
    private static double calculateNumericPValue(double[] x, Attribute classAttribute, RandomForest forest, Instances instances) {
        try {
            double[] predictions = new double[x.length];

            for (int t = 0; t < x.length; t++) {
                try {
                    predictions[t] = forest.classifyInstance(instances.get(t));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            double sumX = 0;
            double sumY = 0;
            double sumXY = 0;
            double sumXSquare = 0;
            double sumYSquare = 0;

            for (int i = 0; i < x.length; i++) {
                sumX += x[i];
                sumY += predictions[i];
                sumXY += x[i] * predictions[i];
                sumXSquare += x[i] * x[i];
                sumYSquare += predictions[i] * predictions[i];
            }

            int n = x.length;

            double numerator = n * sumXY - sumX * sumY;
            double denominator = Math.sqrt((n * sumXSquare - sumX * sumX) * (n * sumYSquare - sumY * sumY));

            if (denominator == 0.0) {
                return 0.0; // 避免除零错误
            }

            double correlation = numerator / denominator;
            double t = correlation * Math.sqrt((n - 2) / (1 - correlation * correlation));
            TDistribution tDist = new TDistribution(n - 2);
            return 2 * (1 - tDist.cumulativeProbability(Math.abs(t)));
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }

}



