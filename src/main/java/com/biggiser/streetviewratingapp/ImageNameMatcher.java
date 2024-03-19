package com.biggiser.streetviewratingapp;

import com.biggiser.streetviewratingapp.beans.Init;

import java.io.FileWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ImageNameMatcher {
    public static void main(String[] args) throws Exception {
        // 创建一个包含图片名和对应值的HashMap
        Init init = new Init();
        Map<String, Integer> imgNameHash = Init.imgNameHash;

        // JDBC连接信息
        String url = "jdbc:mysql://127.0.0.1:3307/street?characterEncoding=utf8&useSSL=false";
        String user = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 创建并执行查询
            String sql = "SELECT imgname FROM t_log WHERE logid >= 2";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    // 创建一个临时的HashMap保存数据库中的图片名
                    Map<String, Integer> dbImgNameHash = new HashMap<>();

                    // 遍历结果集并填充dbImgNameHash
                    while (resultSet.next()) {
                        String imgName = resultSet.getString("imgname") + ".jpg";
                        dbImgNameHash.put(imgName, 0);
                    }

                    // 将不存在于数据库中的imagename写入到新建txt文件中
                    try (FileWriter writer = new FileWriter("imagename_not_in_db.txt")) {
                        for (String imgName : imgNameHash.keySet()) {
                            if (!dbImgNameHash.containsKey(imgName)) {
                                writer.write(imgName + "\n");
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
