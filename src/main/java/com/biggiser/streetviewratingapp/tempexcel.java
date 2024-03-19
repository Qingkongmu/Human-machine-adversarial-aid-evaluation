package com.biggiser.streetviewratingapp;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class tempexcel {

        private static final int PAGE_SIZE = 10000; // 分页大小

        public static void main(String[] args) throws Exception {
            // Configure the database connection
            String url = "jdbc:mysql://127.0.0.1:3307/street?characterEncoding=utf8&useSSL=false";
            String user = "root";
            String password = "123456";

            // Connect to the MySQL database using JDBC
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            // Create a Statement object for executing SQL queries
            Statement stmt = conn.createStatement();

            // Execute the SQL query to retrieve data from the t_log table
            String sql = "SELECT * FROM t_log WHERE logid >= 2 ORDER BY imgname ASC";
            ResultSet rs = stmt.executeQuery(sql);

            // Create a new Excel workbook
            SXSSFWorkbook workbook = new SXSSFWorkbook(1000); // 每1000条数据刷新一次到磁盘

            // Create a new sheet in the workbook
            Sheet sheet = workbook.createSheet("TLog Data");

            // Define the header row and its style
            String[] headers = {"imgname", "safetyuser", "comfortuser", "convenienceuser", "attractivenessuser",
                    "safetypredict", "comfortpredict", "conveniencepredict", "attractivenesspredict"};
            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(30);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font headerFont = workbook.createFont();
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 15 * 256); // 设置列宽
            }

            // Define the data rows and their style
            int rowNum = 1;
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Map<String, com.biggiser.streetviewratingapp.TLog> map = new HashMap<>();
            while (rs.next()) {

                String imgname = rs.getString("imgname");
                double safetyuser = rs.getDouble("safetyuser");
                double comfortuser = rs.getDouble("comfortuser");
                double convenienceuser = rs.getDouble("convenienceuser");
                double attractivenessuser = rs.getDouble("attractivenessuser");
                double safetypredict = rs.getDouble("safetypredict");
                double comfortpredict = rs.getDouble("comfortpredict");
                double conveniencepredict = rs.getDouble("conveniencepredict");
                double attractivenesspredict = rs.getDouble("attractivenesspredict");

                if (map.containsKey(imgname)) {
                    // If the imgname already exists in the HashMap, calculate the average of the 8 fields
                    com.biggiser.streetviewratingapp.TLog tlog = map.get(imgname);
                    tlog.setSafetyuser((tlog.getSafetyuser() + safetyuser) / 2);
                    tlog.setComfortuser((tlog.getComfortuser() + comfortuser) / 2);
                    tlog.setConvenienceuser((tlog.getConvenienceuser() + convenienceuser) / 2);
                    tlog.setAttractivenessuser((tlog.getAttractivenessuser() + attractivenessuser) / 2);
                    tlog.setSafetypredict((tlog.getSafetypredict() + safetypredict) / 2);
                    tlog.setComfortpredict((tlog.getComfortpredict() + comfortpredict) / 2);
                    tlog.setConveniencepredict((tlog.getConveniencepredict() + conveniencepredict) / 2);
                    tlog.setAttractivenesspredict((tlog.getAttractivenesspredict() + attractivenesspredict) / 2);
                } else {
                    // If the imgname does not exist in the HashMap, create a new TLog object and put it in the HashMap
                    com.biggiser.streetviewratingapp.TLog tlog = new com.biggiser.streetviewratingapp.TLog(safetyuser, comfortuser, convenienceuser, attractivenessuser,
                            safetypredict, comfortpredict, conveniencepredict, attractivenesspredict);
                    map.put(imgname, tlog);
                }

                if (rowNum % PAGE_SIZE == 0) {
                    for (Map.Entry<String, com.biggiser.streetviewratingapp.TLog> entry : map.entrySet()) {
                        Row row = sheet.createRow(rowNum++);
                        row.setHeightInPoints(20);
                        com.biggiser.streetviewratingapp.TLog tlog = entry.getValue();
                        row.createCell(0).setCellValue(entry.getKey());
                        row.createCell(1).setCellValue(tlog.getSafetyuser());
                        row.createCell(2).setCellValue(tlog.getComfortuser());
                        row.createCell(3).setCellValue(tlog.getConvenienceuser());
                        row.createCell(4).setCellValue(tlog.getAttractivenessuser());
                        row.createCell(5).setCellValue(tlog.getSafetypredict());
                        row.createCell(6).setCellValue(tlog.getComfortpredict());
                        row.createCell(7).setCellValue(tlog.getConveniencepredict());
                        row.createCell(8).setCellValue(tlog.getAttractivenesspredict());
                        for (int i = 1; i < 9; i++) {
                            row.getCell(i).setCellStyle(dataStyle);
                        }
                    }
                    map.clear(); // 清空map，释放内存
                }
            }
            // Write the remaining data to the workbook
            int count = 0;
            for (Map.Entry<String, com.biggiser.streetviewratingapp.TLog> entry : map.entrySet()) {
                if (count % 10 == 0) { // Only write data every 5 entries
                    Row row = sheet.createRow(rowNum++);
                    row.setHeightInPoints(20);
                    com.biggiser.streetviewratingapp.TLog tlog = entry.getValue();
                    int intValue = Integer.parseInt(entry.getKey());
                    row.createCell(0).setCellValue(intValue);
                    row.createCell(1).setCellValue(tlog.getSafetyuser());
                    row.createCell(2).setCellValue(tlog.getComfortuser());
                    row.createCell(3).setCellValue(tlog.getConvenienceuser());
                    row.createCell(4).setCellValue(tlog.getAttractivenessuser());
                    row.createCell(5).setCellValue(tlog.getSafetypredict());
                    row.createCell(6).setCellValue(tlog.getComfortpredict());
                    row.createCell(7).setCellValue(tlog.getConveniencepredict());
                    row.createCell(8).setCellValue(tlog.getAttractivenesspredict());
                    for (int i = 1; i < 9; i++) {
                        row.getCell(i).setCellStyle(dataStyle);
                    }
                }
                count++;
            }
            map.clear(); // 清空map，释放内存
// Close the database connection
            rs.close();
            stmt.close();
            conn.close();

// Adjust the column widths to fit the content
            adaptiveColumnWidth((SXSSFSheet) sheet, headers.length, 5, 15);

// Save the workbook to a file
            File file = new File("t_log_data10.xlsx");
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.dispose(); // 关闭workbook并清除临时文件
            outputStream.close();

            System.out.println("Excel file has been created successfully.");
        }
        //默认列宽自适应，可限制最小列宽、最大列宽
        private static void adaptiveColumnWidth(SXSSFSheet sheet, int columnCount, Integer minColumnWidth, Integer maxColumnWidth) {
            sheet.trackAllColumnsForAutoSizing();//跟踪要自动调整宽度的所有列
            for (int i = 0; i < columnCount; i++) {
                //设置列宽自适应
                sheet.autoSizeColumn(i);
                int currentColumnWidth = sheet.getColumnWidth(i);
                // 如果最小列宽不为null，且自适应列宽小于最小列宽，则设置该列宽为最小列宽
                if (minColumnWidth!=null && currentColumnWidth < (minColumnWidth * 256)) {
                    sheet.setColumnWidth(i, minColumnWidth * 256);
                }
                // 如果最大列宽不为null，且自适应列宽大于最大列宽，则设置该列宽为最大列宽
                if (maxColumnWidth!=null && currentColumnWidth > (maxColumnWidth * 256)) {
                    sheet.setColumnWidth(i, maxColumnWidth * 256);
                }
            }
        }

    }

