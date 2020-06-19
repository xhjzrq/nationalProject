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


package com.example.demo2_parameter.util;


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

public class configWithFile {


    public static Map getUsers(String pathName) {

        Map<String, JSONObject> userMap = new HashMap();
        try {
            //读文件
            File file = new File(System.getProperty("user.dir")+"/resources/"+pathName);

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


    public static String readFileContent(String fileName) {
        File file = new File(System.getProperty("user.dir")+"/resources/"+fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public static boolean writetxtfile(Map userMap ,String pathName) {
        boolean flag=false;
        try {
            //写入的txt文档的路径
//            PrintWriter pw=new PrintWriter("./src/main/resources/templates/users.txt","utf-8");
            PrintWriter pw=new PrintWriter(System.getProperty("user.dir")+"/resources/"+pathName,"utf-8");
            //写入的内容

                pw.write(String.valueOf(userMap));

            pw.flush();
            pw.close();
            flag=true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
//         System.out.println(getUsers("admin.text"));
//        Map<String,JSONObject> userMap = new HashMap();
//        JSONObject json = new JSONObject();
//        json.put("admin","test");
//        json.put("34","tes3412t");
//        json.put("gr","tf");
//        json.put("nuy","htf");
//
//        userMap.put("admin",json);
//
//        System.out.println(writetxtfile(userMap,"conf.text"));

//       System.out.println(readFileContent("admin.text"));

    }
}
