package com.biggiser.streetviewratingapp;

import java.io.*;
import java.util.*;

import com.biggiser.streetviewratingapp.beans.Init;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelProcessor {
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\10511\\Desktop\\GISDATA\\丽江坐标点数据\\ljzuobiao.xlsx";
        Init init = new Init();
        Map<String, Integer> imgNameHash = Init.imgNameHash;
        HashMap<String, String[]> resultMap = new HashMap<>();

        try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell fidCell = row.getCell(0);
                String fid;

                if (fidCell.getCellType() == CellType.NUMERIC) {
                    double numericValue = fidCell.getNumericCellValue();
                    int intValue = (int) numericValue;
                    fid = String.valueOf(intValue);
                } else {
                    continue;
                }

                String imageName = fid + ".jpg";

                if (imgNameHash.containsKey(imageName)) {
                    Cell lngCell = row.getCell(1);
                    Cell latCell = row.getCell(2);

                    double lng = lngCell.getNumericCellValue();
                    double lat = latCell.getNumericCellValue();

                    String[] data = new String[3];
                    data[0] = fid;
                    data[1] = String.valueOf(lng);
                    data[2] = String.valueOf(lat);

                    resultMap.put(fid, data);
                }

                // 释放资源
                row = null;
                fidCell = null;
            }

            // 创建SXSSFWorkbook
            SXSSFWorkbook newWorkbook = new SXSSFWorkbook();
            Sheet newSheet = newWorkbook.createSheet("NewSheet");

            int rowIndex = 0;
            int count = 0;
            for (String[] data : resultMap.values()) {
                if (count % 10 == 0) {
                    rowIndex++;
                }
                Row newRow = newSheet.getRow(rowIndex);
                if (newRow == null) {
                    newRow = newSheet.createRow(rowIndex);
                }

                for (int i = 0; i < data.length; i++) {
                    Cell newCell = newRow.createCell(i);
                    if (i == 0) {
                        int intValue = Integer.parseInt(data[i]);
                        newCell.setCellValue(intValue);
                    } else {
                        double doubleValue = Double.parseDouble(data[i]);
                        newCell.setCellValue(doubleValue);
                    }
                }
                count++;
            }

            String newFilePath = "C:\\Users\\10511\\Desktop\\GISDATA\\丽江坐标点数据\\10mljzb.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(newFilePath)) {
                newWorkbook.write(fileOut);
            }

            System.out.println("处理完成！新的Excel文件已保存至：" + newFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}