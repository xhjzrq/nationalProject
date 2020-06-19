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


package com.example.demo1_flange.bean;


import lombok.Data;

import java.util.HashMap;
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
     * 表参数
     */
    private Map<Integer, Map<String,Double>> table = new HashMap<>();

    /**
     * 法兰进位
     */
    private int carry;

    /**
     * 法兰孔数
     */
    private Double holes;

    /**
     * 计算精度
     */
    private int accuracy;



    public int getCarry() {
        return carry;
    }

    public void setCarry(int carry) {
        this.carry = carry;
    }

    public Double getHoles() {
        return holes;
    }

    public void setHoles(Double holes) {
        this.holes = holes;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }


    public Map<Integer, Map<String, Double>> getTable() {
        return table;
    }

    public void setTable(Map<Integer, Map<String, Double>> table) {
        this.table = table;
    }

}
