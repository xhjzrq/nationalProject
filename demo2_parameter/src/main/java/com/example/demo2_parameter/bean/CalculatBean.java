/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: CalculatBean
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/29 18:18
 * <p>
 * Description: CalculatBean
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo2_parameter.bean;


import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈CalculatBean〉
 *
 * @author why

 * @create 2020/2/29

  @since 1.0.0

 */
@Data
public class CalculatBean {


    /**
     * 网页输入参数
     * inputMap 网页输入的参数，map结构：{管件号：{序号：{参数名：参数值}}}，计算中用到的参数(参数名固定)：身长 length，曲角 alpha, 法兰减量 flangeReduction
     */
    private Map<String,Map<Integer,Map<String,String>>> inputMap = new HashMap<>();

    /**
     * scv表参数
     * 参数列表，读取csv获得，map结构：{曲角：{参数名：参数值}} 参数名直接饮用csv中的表头,曲角为csv中Alpha的值
     */
    private Map<Double,Map<String,String>> parameter  = new HashMap<>();




    public Map<String, Map<Integer, Map<String, String>>> getInputMap() {
        return inputMap;
    }


    public void setInputMap(Map<String, Map<Integer, Map<String, String>>> inputMap) {
        this.inputMap = inputMap;
    }

    public Map<Double, Map<String, String>> getParameter() {
        return parameter;
    }

    public void setParameter(Map<Double, Map<String, String>> parameter) {
        this.parameter = parameter;
    }
}
