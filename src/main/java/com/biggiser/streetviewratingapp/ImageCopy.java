package com.biggiser.streetviewratingapp;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ImageCopy {
    public static void main(String[] args) {
        String sourceFolderPath = "H:\\human-machine\\StreetViewRatingApp\\src\\main\\webapp\\LjPass"; // 源文件夹路径
        String targetFolderPath = "H:\\human-machine\\StreetViewRatingApp\\src\\main\\webapp\\LjPass_temp01"; // 目标文件夹路径
        String txtFilePath = "H:\\human-machine\\StreetViewRatingApp\\imagename_not_in_db.txt"; // 存储图片名称的txt文件路径

        try {
            // 读取txt文件中的图片名称
            List<String> imageNames = Files.readAllLines(Paths.get(txtFilePath));

            // 遍历图片名称，并复制到目标文件夹
            for (String imageName : imageNames) {
                String sourceImagePath = sourceFolderPath + File.separator + imageName;
                String targetImagePath = targetFolderPath + File.separator + imageName;
                Path source = Paths.get(sourceImagePath);
                Path target = Paths.get(targetImagePath);
                Files.copy(source, target);
            }
            System.out.println("图片复制完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
