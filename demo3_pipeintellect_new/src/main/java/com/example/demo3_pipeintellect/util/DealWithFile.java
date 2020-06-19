/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: DealWithFile
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/29 2:56
 * <p>
 * Description: DealWithFile
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo3_pipeintellect.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈DealWithFile〉
 *
 * @author why

 * @create 2020/2/29

 * @since 1.0.0

 */

public class DealWithFile {

    private static String pathName =System.getProperty("user.dir")+"/resources/users.txt" ;
//      private static String pathName ="D:/demo1_flange/resources/users.txt";

    public static Map getUsers() {
//        System.out.println(pathName);
        Map<String, JSONObject> userMap = new HashMap();
        try {
            //读文件
//            File file = new File("./src/main/resources/templates/users.txt");
            File file = new File(pathName);

//            System.out.println(getResource(""));
            // 判断文件是否为标准文件以及是否存在
            if (file.isFile() && file.exists()) {
                //读文件
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
                BufferedReader br = new BufferedReader(isr);
                String txt = null;

                while ((txt = br.readLine()) != null) {

                    JSONObject json = JSON.parseObject(txt);
                    String mapKey = json.get("username").toString();
                    userMap.put(mapKey,json);
//                    System.out.println(json);
                }
                br.close();
                isr.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件读取错误!");
        }
        return userMap;
    }


    public static boolean writetxtfile(Map userMap) {
        boolean flag=false;
        try {
            //写入的txt文档的路径
//            PrintWriter pw=new PrintWriter("./src/main/resources/templates/users.txt","utf-8");
            PrintWriter pw=new PrintWriter(pathName,"utf-8");
            //写入的内容
            for (Object json :  userMap.values()
            ) {
                pw.write(json.toString()+"\r\n");
            }
            pw.flush();
            pw.close();
            flag=true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
         System.out.println(getUsers());
    }
}
