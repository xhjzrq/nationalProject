///**
// * Copyright (C), 2015-2020, XXX有限公司
// * <p>
// * FileName: CalculatService
// * <p>
// * Author:   why
// * <p>
// * Date:     2020/3/1 16:01
// * <p>
// * Description: CalculatService
// * <p>
// * History:
// *
// * <author>          <time>          <version>          <desc>
// * <p>
// * 作者姓名           修改时间           版本号              描述
// */
//
//
//package com.example.demo3_pipeintellect.services.impl;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.example.demo3_pipeintellect.bean.CalculatBean;
//import com.example.demo3_pipeintellect.services.ICalculatService;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.*;
//
///**
// * 〈法兰预转角计算〉<br>
// * 〈CalculatService〉
// *
// * @author why
//
// * @create 2020/3/1
//
// * @since 1.0.0
//
// */
//@Service
//public class CalculatService3 implements ICalculatService {
//    /**
//     * 算法
//     *
//     */
//
//    public  static Double surplusMaterial;//余料
//    public  static Double utilizationRatio;//利用率
//    public  static  Map<Integer,Map<String,String>> combination;//组合方式
//    public  static JSONArray  result = new JSONArray();
//    public  static  Map<String,Map<String, String>> resultCcsj = new HashMap<>();
//    public  static  Map<String,String>[] inputNext;
//    public  static int nextId = 0;
//    public static Double zxyl = 0d;
//    public  static Double slit =0d;
//    @Override
//    public  JSONObject getResult(CalculatBean calculatBean){
//        //pipe == 管件号
//        //id == 序号/编号
//        //specifications == 规格
//        //material == 材质
//        //length == 料长
//        //count == 管材测长表根数
//        //casingNumber == 套料编号   返回值参数
//        //slit == 切缝宽度
//        //combination == 组合数据
//        //ccsj == 返回测长
//        JSONArray  nullArray = new JSONArray();
//        result = nullArray;
//        Map<String,String>[] inputOfXlmx = calculatBean.getInputOfXlmx();
//        Map<String,Map<String, String>> inputOfCcsj = calculatBean.getInputOfCcsj();
//        resultCcsj = inputOfCcsj;
//        Map<String,String> inputOfCondition = calculatBean.getInputOfCondition();
//        zxyl = Double.valueOf(inputOfCondition.get("smallOddments"));
//        slit = Double.valueOf(inputOfCondition.get("slit"));
//        Map<String,String> countMap = new HashMap<>();
//        for (String key: inputOfCcsj.keySet()
//             ) {
//            countMap.put(key,inputOfCcsj.get(key).get("count"));
//        }
//        Set<String> screen = new HashSet<>();
//        for (String id : inputOfCcsj.keySet()) {
//            screen.add(inputOfCcsj.get(id).get("specifications")+ inputOfCcsj.get(id).get("material"));
//
//        }
//
//
//        for (String str : screen) {
//
//            String specificationsAndmaterial = str;
//            int inputOfXlmxOfScreenIndex = 0;
//            int inputOfXlmxOfScreenLength = 0;
//            for (int j = 0; j < inputOfXlmx.length; j++) {
//                if(specificationsAndmaterial.equals(inputOfXlmx[j].get("specifications")+inputOfXlmx[j].get("material"))){
//                    inputOfXlmxOfScreenLength++;
//                }
//            }
//            Map<String,String>[] inputOfXlmxOfScreen = new Map[inputOfXlmxOfScreenLength];
//            for (int j = 0; j < inputOfXlmx.length; j++) {
//                if(specificationsAndmaterial.equals(inputOfXlmx[j].get("specifications")+inputOfXlmx[j].get("material"))){
//                    if(Double.valueOf(inputOfXlmx[j].get("length")) > Double.valueOf(inputOfCondition.get("smallLength"))){
//                        inputOfXlmxOfScreen[inputOfXlmxOfScreenIndex] = inputOfXlmx[j];
//                        inputOfXlmxOfScreenIndex++;
//                    }
//
//                }
//            }
//            inputNext = new Map[inputOfXlmxOfScreen.length];
//            inputNext = inputOfXlmxOfScreen;
//            for (String id :inputOfCcsj.keySet()
//                 ) {
//                if(specificationsAndmaterial.equals(inputOfCcsj.get(id).get("specifications")+inputOfCcsj.get(id).get("material"))){
//                    Double cl = Double.valueOf(inputOfCcsj.get(id).get("length"));
//                    for (int i = 0; i < Integer.valueOf(inputOfCcsj.get(id).get("count")); i++) {
//                        if(inputNext.length > 0){
//                            dfs2(inputNext,cl,id,slit);
//                        }
//                    }
//                }
//
//
//            }
//
//
//        }
//        Map<String,Integer> maptj  = new HashMap<>();
//        for (int j = 0; j < result.size(); j++) {
//            Object object = result.get(j);
//            JSONObject json = (JSONObject)object;
//            JSONObject json2 = (JSONObject)json.get("ccsj");
//            if(maptj.get(json2.get("id").toString()) == null){
//                maptj.put(json2.get("id").toString(),1);
//            }else{
//                maptj.put(json2.get("id").toString(),Integer.valueOf(maptj.get(json2.get("id").toString()  ))+1);
//            }
//        }
//        JSONArray newresult = new JSONArray();
//        Map<String,Integer> maptj2 = new HashMap<>();
//        JSONArray xlResult = new JSONArray();
//        for (int j = 0; j < result.size(); j++) {
//            Object object = result.get(j);
//            JSONObject json = (JSONObject)object;
//            JSONObject json2 = (JSONObject)json.get("ccsj");
//            String ccidzh = json2.getString("id");
//            String tlbh = ccidzh + "-" + maptj.get(ccidzh);
//            JSONArray array = json.getJSONArray("zh");
//            JSONArray newArray = new JSONArray();
//            if(maptj2.get(ccidzh) == null){
//                maptj2.put(ccidzh,1);
//            }else{
//                maptj2.put(ccidzh,maptj2.get(ccidzh) + 1);
//            }
//            for (int i = 0; i < array.size(); i++) {
//                Object object2 = array.get(i);
//                JSONObject json3 = (JSONObject)object2;
//                json3.put("casingNumber",tlbh + "-" + maptj2.get(ccidzh));
//                newArray.add(json3);
//                xlResult.add(json3);
//            }
//            JSONObject linshi = new JSONObject();
//            linshi.put("ccsj",json2);
//            linshi.put("zh",newArray);
//            newresult.add(linshi);
//        }
//
//        JSONArray ccJsonArray = new JSONArray();
//        for (String id : inputOfCcsj.keySet()
//        ) {
//            JSONObject jsonMap = new JSONObject();
//            for (String jsonkey: inputOfCcsj.get(id).keySet()
//            ) {
//                jsonMap.put(jsonkey,inputOfCcsj.get(id).get(jsonkey));
//            }
//            Boolean flag = true;
//            for (String i2d :maptj.keySet()
//            ) {
//                if(i2d.equals(id)){
//                    jsonMap.put("used",maptj.get(i2d));
//                    jsonMap.put("count",countMap.get(id));
////                    flag =false;
//                }
//            }
//            jsonMap.put("id",id);
//            ccJsonArray.add(jsonMap);
//        }
//
//        JSONArray xlJsonArray = new JSONArray();
//        for (int i = 0; i < inputOfXlmx.length; i++) {
//            boolean flag = true;
//            for (int j = 0; j < xlResult.size(); j++) {
//                JSONObject json = (JSONObject) xlResult.get(j);
//                if(json.get("pipe").equals(inputOfXlmx[i].get("pipe")) && json.get("id").equals(inputOfXlmx[i].get("id"))){
//                    xlJsonArray.add(json);
//                    flag = false;
//                }
//            }
//            if(flag){
//                JSONObject jsonObject = new JSONObject();
//                for (String key : inputOfXlmx[i].keySet()
//                ) {
//                    jsonObject.put(key,inputOfXlmx[i].get(key));
//                }
//                xlJsonArray.add(jsonObject);
//            }
//        }
//
//        JSONObject lastResult = new JSONObject();
//        lastResult.put("combination",newresult);
//        lastResult.put("ccsj",ccJsonArray);
//        lastResult.put("xlmx",xlJsonArray);
//        return  lastResult;
//    }
//
//    public static void dfs2(Map<String,String>[] input,Double ccLength,String mapID,Double slit){
//        utilizationRatio = 0d;
//        surplusMaterial = ccLength;
//        Double zjlyl = 0d;
//        Double yl = 0d;
//        Map<Integer,Map<String,String>> zh = new HashMap<>();
//        Double zclc = 0d;
//        for (int i = 0; i < input.length; i++) {
//            if(i == 0){
//                zclc = Double.valueOf(input[i].get("length"));
//            }else if (Double.valueOf(input[i].get("length"))>zclc){
//                zclc = Double.valueOf(input[i].get("length"));
//            }
//        }
//
//
//
//
//            int starti = (int)Math.floor(ccLength/zclc);
//            if(starti == 0 || starti > input.length){
//                starti = 1;
//            }
//            for (int i = starti; i <= input.length; i++) {
//                nextId = 0;
//                Map<String,String>[] output = new Map[i];
//                dfs(input,output,0,0,ccLength,slit,zxyl) ;
//                if(utilizationRatio > zjlyl){
//                    zjlyl = utilizationRatio;
//                    yl = surplusMaterial;
//                    zh = combination;
//                }
//                if(nextId == 0 || surplusMaterial == zxyl || surplusMaterial == 0){
//                    break;
//                }
//            }
//
//
//        zjlyl = new BigDecimal(zjlyl).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        yl = new BigDecimal(yl).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        resultCcsj.get(mapID).put("utilizationRatio",zjlyl.toString());
//        resultCcsj.get(mapID).put("surplusMaterial",yl.toString());
////        Map<String,Map<String, String>> rescc = new HashMap<>();
////        rescc.put(ccid,inputOfCcsj.get(ccid));
//        Map<String,String>[] zh2 = new Map[zh.size()];
//        for (int id : zh.keySet()
//        ) {
//            zh2[id] = zh.get(id);
//        }
////
//        JSONObject jsonKey = new JSONObject();
//        JSONObject jsonKey3 = new JSONObject();
////        for (String id :rescc.keySet()
////        ) {
//            JSONObject jsonKey2 = new JSONObject();
//            String jsonKey2Str = jsonKey2.toJSONString(resultCcsj.get(mapID));
//            jsonKey2 = JSONObject.parseObject(jsonKey2Str);
//            jsonKey2.put("id",mapID);
//            jsonKey3 = jsonKey2;
////        }
//        JSONArray jsonArray = new JSONArray();
//        for (int i = 0; i < zh2.length; i++) {
//            JSONObject json = new JSONObject();
//            String jsonStr = json.toJSONString(zh2[i]);
//            jsonArray.add(JSONObject.parseObject(jsonStr));
//        }
//        jsonKey.put("ccsj",jsonKey3);
//        jsonKey.put("zh",jsonArray);
//        result.add(jsonKey);
//
//        inputNext = new Map[input.length - zh2.length];
//        int nextId1 = 0;
//        for (int i = 0; i < input.length; i++) {
//            Boolean flag = true;
//            for (int j = 0; j < zh2.length; j++) {
//                if(input[i].get("pipe").equals(zh2[j].get("pipe")) && input[i].get("id").equals(zh2[j].get("id"))){
//                    flag = false;
//                    break;
//                }
//            }
//            if(flag){
//                inputNext[nextId1] = input[i];
//                nextId1++;
//            }
//        }
//
//
//    }
//
//
//    public static void dfs(Map<String,String>[] input,Map<String,String>[] output,int index,int start,Double length,Double slit,Double zxyl){
//        if(index==output.length)//产生一个组合序列
//        {
//            Double zuheLength = 0d;
//            for (int i = 0; i < output.length; i++) {
//                zuheLength += Double.valueOf(output[i].get("length"));
//            }
//            zuheLength = zuheLength + slit * (output.length - 1);
//            if(length - zuheLength > zxyl){
//                nextId++;
//                Double zuheUtilizationRatio = zuheLength/length;
//                if(zuheUtilizationRatio > utilizationRatio){
//                    utilizationRatio = zuheUtilizationRatio;
//                    surplusMaterial = length - zuheLength;
//                    Map<Integer,Map<String,String>> szMap = new HashMap<>();
//                    for (int i = 0; i < output.length; i++) {
//                        szMap.put(i,output[i]);
//                    }
//                    combination = szMap;
//                }
//            }else if(length - zuheLength == 0){
//                nextId = 0;
//                utilizationRatio = 1d;
//                surplusMaterial = 0d;
//                Map<Integer,Map<String,String>> szMap = new HashMap<>();
//                for (int i = 0; i < output.length; i++) {
//                    szMap.put(i,output[i]);
//                }
//                combination = szMap;
//            }else if(length - zuheLength == zxyl){
//                nextId = 0;
//                Double zuheUtilizationRatio = zuheLength/length;
//                utilizationRatio = zuheUtilizationRatio;
//                surplusMaterial = zxyl;
//                Map<Integer,Map<String,String>> szMap = new HashMap<>();
//                for (int i = 0; i < output.length; i++) {
//                    szMap.put(i,output[i]);
//                }
//                combination = szMap;
//            }
//
//        }else{
//            for(int j=start;j<input.length;j++){
//                output[index]=input[j];//记录选取的元素
//                if(surplusMaterial > zxyl){
//                    dfs(input,output,index+1,j+1,length,slit,zxyl);//选取下一个元素，可选下标区间为[j+1,input.length]
//                }else if(surplusMaterial == 0d){
//                    break;
//                }else if(surplusMaterial == zxyl){
//                    break;
//                }
//            }
//        }
//    }
//}
