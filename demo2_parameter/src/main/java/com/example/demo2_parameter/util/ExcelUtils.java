/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: ExcelUtils
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/3/8 16:38
 * <p>
 * Description: ExcelUtils
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo2_parameter.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 〈一句话功能简述〉<br> 
 * 〈ExcelUtils〉
 *
 * @author why

 * @create 2020/3/8

 * @since 1.0.0

 */

public class ExcelUtils {
    public ExcelUtils() {
        throw new Error("工具类不允许实例化！");
    }

    /**
     * 获取并解析excel文件，返回一个二维集合
     * @param file 上传的文件
     * @return 二维集合（第一重集合为行，第二重集合为列，每一行包含该行的列集合，列集合包含该行的全部单元格的值）
     */
    public static ArrayList<ArrayList<String>> excelUtils(MultipartFile file) {
        ArrayList<ArrayList<String>> row = new ArrayList<>();
        //获取文件名称
        String fileName = file.getOriginalFilename();
//        System.out.println(fileName);

        try {
            //获取输入流
            InputStream in = file.getInputStream();
            //判断excel版本
            Workbook workbook = null;
            if (judegExcelEdition(fileName)) {
                workbook = new XSSFWorkbook(in);
            } else {
                workbook = new HSSFWorkbook(in);
            }

            //获取第一张工作表
            Sheet sheet = workbook.getSheetAt(0);
            //从第二行开始获取
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                //循环获取工作表的每一行
                Row sheetRow = sheet.getRow(i);
//                System.out.println(sheetRow);
                //循环获取每一列
                ArrayList<String> cell = new ArrayList<>();
                for (int j = 0; j < sheetRow.getPhysicalNumberOfCells(); j++) {
//                    System.out.println(j);
                    sheetRow.getCell(j).setCellType(CellType.STRING);
//                    System.out.println(sheetRow.getCell(j).getStringCellValue());
                    //将每一个单元格的值装入列集合
                    cell.add(sheetRow.getCell(j).getStringCellValue());
                }
                //将装有每一列的集合装入大集合
                row.add(cell);

                //关闭资源
                workbook.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("===================未找到文件======================");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("===================上传失败======================");
        }

        return row;
    }

    /**
     * 判断上传的excel文件版本（xls为2003，xlsx为2017）
     * @param fileName 文件路径
     * @return excel2007及以上版本返回true，excel2007以下版本返回false
     */
    private static boolean judegExcelEdition(String fileName){
        if (fileName.matches("^.+\\.(?i)(xls)$")){
            return false;
        }else {
            return true;
        }

    }
}



