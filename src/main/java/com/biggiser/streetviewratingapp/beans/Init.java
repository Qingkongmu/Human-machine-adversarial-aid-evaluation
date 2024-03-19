package com.biggiser.streetviewratingapp.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import weka.core.Instances;

@Component
public class Init {
    public static Map<String, Integer> imgNameHash;
    public static List<ImageScore> imgScores;
    public static int featureDimension;
    public static List<String> featureTitle;//特征标头


    public Init() throws Exception {
        imgNameHash = new HashMap<>();
        imgScores = new ArrayList<>();
        featureTitle = new ArrayList<>();
        this.init1();
    }

    //@PostConstruct
    public void init1() throws Exception {
        System.out.println("Start Loading StreetViews hashMap and imgScores----");

        //String path="F:/myStudy/graduateWork/WuhanStreetview/wuhanStreetPhoto/";
//		String path="/data/wuhanStreetPhoto/";//street view image path
        String path = "H:\\human-machine\\StreetViewRatingApp\\src\\main\\webapp\\LjPass";
        System.out.println(path);
        //String path="E:/wuhanStreetPhoto/";
        File file = new File(path);
        //imgNameHash=new HashMap<>();
        File[] flist = file.listFiles();
        String str;
        for (int i = 0; i < flist.length; i++) {
            str = flist[i].getAbsolutePath();
            int index = str.lastIndexOf('.');
            if ("jpg".equals(str.substring(index + 1))) {
                imgNameHash.put(flist[i].getName(), i);//写进hash表
                ImageScore imgscore = new ImageScore(flist[i].getName());
                imgScores.add(imgscore);//写进打分表
            }
        }
        System.out.println("end Loading StreetViews hashMap and imgScores----");

        System.out.println("Start Loading StreetViews Features----");

        //String csvPath="F:/myStudy/graduateWork/WuhanStreetview/wuhanStreetPhoto/features.csv";
        String csvPath = "H:\\human-machine\\StreetViewRatingApp\\src\\main\\resources\\file19.csv";//each image's feature after segment model
        //String csvPath="E:/wuhanStreetPhoto/features.csv";
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));
        String s = reader.readLine();//过滤标题
        String[] title = s.split(",");
        featureDimension = title.length - 1;//列数，去掉title行以及filename列
        System.out.println("featureDimension:" + featureDimension);
        for (int i = 1; i <= featureDimension; i++) {
            featureTitle.add(title[i]);
        }

        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] items = line.split(",");
            if (items.length != featureDimension + 1) {
                continue;
            }

//			String curName=items[0]+".png";
            String curName = items[0];
            int curId = -1;
            if (imgNameHash.containsKey(curName)) {
                curId = imgNameHash.get(curName);//根据key取值
            } else {
                continue;
            }
            imgScores.get(curId).clearFeatures();
            for (int i = 1; i < items.length; i++) {
                imgScores.get(curId).addFeature(Double.parseDouble(items[i]));
            }
        }
        reader.close();

        System.out.println("End Loading StreetViews Features----");
    }
}