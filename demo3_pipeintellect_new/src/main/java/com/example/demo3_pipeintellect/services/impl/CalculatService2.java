/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: CalculatService
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/3/1 16:01
 * <p>
 * Description: CalculatService
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo3_pipeintellect.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3_pipeintellect.bean.CalculatBean;
import com.example.demo3_pipeintellect.services.ICalculatService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 〈法兰预转角计算〉<br>
 * 〈CalculatService〉
 *
 * @author why

 * @create 2020/3/1

 * @since 1.0.0

 */
@Service
public class CalculatService2  implements ICalculatService {
    /**
     * 算法
     *
     */
    public static Double surplusMaterial;//余料
    public static Map<String,String> inputOfCondition;
    public static Map<String,String>[] inputOfXlmx;
    public static Map<String,Map<String, String>> inputOfCcsj;
    public static JSONObject tlzjjg;
    public static int xxCount = 0;
    @Override
    public  JSONObject getResult(CalculatBean calculatBean) {
        xxCount=0;
        inputOfCondition = calculatBean.getInputOfCondition();
        Double zxlc = Double.valueOf(inputOfCondition.get("smallLength"));
        Double fl = Double.valueOf(inputOfCondition.get("feiliao"));
        inputOfXlmx = calculatBean.getInputOfXlmx();
        inputOfCcsj = calculatBean.getInputOfCcsj();
        for (int i = 0; i < inputOfXlmx.length; i++) {
            Map<String, String> xlmx = inputOfXlmx[i];
            Double length = Double.valueOf(xlmx.get("length"));
            if (length < zxlc) {
                inputOfXlmx[i].put("casingNumber", "-");
                xxCount++;
            }
        }
        JSONArray newresult = new JSONArray();
        for (String key : inputOfCcsj.keySet()) {
            int count = 0;
            for (int j = 1; j <= Integer.valueOf(inputOfCcsj.get(key).get("count")); j++) {
                if(inputOfXlmx.length <= xxCount){
                    break;
                }
                tlzjjg = new JSONObject();
                tlzjjg.put("zcd",0);
                tlzjjg.put("zh","");
//                tlzjjg.put("utilizationRatio",0);
                f_comb(0,"",0d,0d,inputOfCondition,inputOfCcsj.get(key));
                String bh = key + "-" + j+"";
                if(tlzjjg.get("zh").equals("")){
                    continue;
                }
                String zh[] = tlzjjg.get("zh").toString().split("-");
                JSONArray xlzh = new JSONArray();
                for (int i = 0; i < zh.length; i++) {
                    int bh3 = i+1;
                    String bh4 = bh + "-" + bh3;
                    inputOfXlmx[Integer.valueOf(zh[i])].put("casingNumber",bh4);
                    String xxjson = JSON.toJSONString(inputOfXlmx[Integer.valueOf(zh[i])]);
                    JSONObject xx = JSONObject.parseObject(xxjson);
                    xlzh.add(xx);
                    xxCount++;
                }
                String json = JSON.toJSONString(inputOfCcsj.get(key));
                JSONObject jm = JSONObject.parseObject(json);
                jm.put("surplusMaterial",tlzjjg.get("surplusMaterial").toString());
                Double lc = Double.valueOf(inputOfCcsj.get(key).get("length"));
                Double yl = tlzjjg.getDouble("surplusMaterial");
                Double zcd = tlzjjg.getDouble("zcd");

                Double utilizationRatio ;//陈晨改的
                Double kycd ;//陈晨改的  //----------
                if (yl<fl){
                    utilizationRatio = (zcd)/lc;
                    kycd = zcd;  //--------------
                }else{
                    utilizationRatio = (zcd + yl)/lc;
                    kycd = zcd + yl;  //--------
                }
                //Double utilizationRatio = (lc - yl)/lc;
                utilizationRatio = new BigDecimal(utilizationRatio).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                jm.put("utilizationRatio",utilizationRatio.toString());

                jm.put("lc",lc.toString());  //-------------
                jm.put("kycd",kycd.toString());  //-----------

                JSONObject linshi = new JSONObject();
                linshi.put("ccsj",jm);
                linshi.put("zh",xlzh);
                newresult.add(linshi);
                count++;
            }
            if(count > 0){
                inputOfCcsj.get(key).put("used",count+"");
            }
            if(inputOfXlmx.length <= xxCount){
                break;
            }
        }
        JSONArray ccJsonArray = new JSONArray();
        for (String key : inputOfCcsj.keySet()) {
            String ccjson = JSON.toJSONString(inputOfCcsj.get(key));
            JSONObject cc = JSONObject.parseObject(ccjson);
            cc.put("id",key);
            ccJsonArray.add(cc);
        }
        JSONArray xlJsonArray = new JSONArray();
        for (int i = 0; i < inputOfXlmx.length; i++) {
            String xxjson = JSON.toJSONString(inputOfXlmx[i]);
            JSONObject xx = JSONObject.parseObject(xxjson);
            xlJsonArray.add(xx);
        }
        JSONObject lastResult = new JSONObject();
        lastResult.put("combination",newresult);
        lastResult.put("ccsj",ccJsonArray);
        lastResult.put("xlmx",xlJsonArray);
        return  lastResult;
    }

    public static int f_comb(int id,String casingNumber,Double c_comb,Double l_comb,Map<String,String> condition,Map<String, String> ccjl){
        int rtn =1;
        String ls_s = "";
        if(c_comb > Double.valueOf(ccjl.get("length"))){
            return 1;
        }
        for (int i = id; i < inputOfXlmx.length; i++) {
            if(null == inputOfXlmx[i].get("casingNumber")){
                inputOfXlmx[i].put("casingNumber","");
            }

            if(!inputOfXlmx[i].get("specifications").equals(ccjl.get("specifications")) || !inputOfXlmx[i].get("material").equals(ccjl.get("material")) || !inputOfXlmx[i].get("casingNumber").equals("")){
                continue;
            }
            Double xlcd = Double.valueOf(inputOfXlmx[i].get("length"));
            if((c_comb + xlcd) > Double.valueOf(ccjl.get("length"))){
                continue;
            }else if((c_comb + xlcd) == Double.valueOf(ccjl.get("length"))){
                surplusMaterial = 0d;
                rtn =0;
            }else{
               if(Double.valueOf(condition.get("smallOddments")) > 0){
                    if(Double.valueOf(ccjl.get("length")) - c_comb - xlcd - Double.valueOf(condition.get("slit")) < Double.valueOf(condition.get("smallOddments"))){
                        continue;
                    }else{
                        surplusMaterial = Double.valueOf(ccjl.get("length")) - c_comb - xlcd - Double.valueOf(condition.get("slit"));
                    }
                   rtn = 1;
               }
            }
            if(casingNumber.length() > 0){
                ls_s = casingNumber + "-" + i;
            }else{
                ls_s = i+"";
            }
            Double sum_xlcd = l_comb + xlcd;
            Double sum_cd = c_comb + xlcd + Double.valueOf(condition.get("slit"));
            if(rtn == 0){
                tlzjjg.put("zh",ls_s);
                tlzjjg.put("surplusMaterial",surplusMaterial);
                tlzjjg.put("zcd",sum_xlcd);
                return 0;
            }
            if(Double.valueOf(tlzjjg.get("zcd").toString()) < sum_xlcd ){
                tlzjjg.put("zh",ls_s);
                tlzjjg.put("surplusMaterial",surplusMaterial);
                tlzjjg.put("zcd",sum_xlcd);
            }

            if(i == inputOfXlmx.length-1){
                return 1;
            }
            rtn = f_comb(i+1,ls_s,sum_cd,sum_xlcd,condition,ccjl);
            if(rtn == 0){
                break;
            }
        }
        return rtn;
    }

}
