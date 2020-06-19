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


package com.example.demo1_flange.service.impl;


import com.example.demo1_flange.bean.CalculatBean;
import com.example.demo1_flange.service.ICalculatService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
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
public class CalculatService implements ICalculatService {
    /**
     * 计算方法
     * @param calculatBean
     * @return
     */

    @Override
    public Map<String, Double> getResult(CalculatBean calculatBean) {
        int accuracy = calculatBean.getAccuracy();
        Map<String,Double> result = new HashMap<>();
        Map<Integer,Map<String,Double>> table = calculatBean.getTable();
        //初始角
        Double initialAngle = 0d;
        Double initialZ = table.get(1).get("zzz");
        Double initialxOry = table.get(1).get("xory");
        if(initialZ == 0){
            initialAngle = 0d;
        }else if(initialZ > 0 && initialxOry == 0){
            initialAngle = 90d;
        }else if(initialZ < 0 && initialxOry == 0){
            initialAngle = -90d;
        }else{
            initialAngle = Math.toDegrees(Math.atan(initialZ/initialxOry));
        }
        //尾端角
        Double tailAngle = 0d;
        Double tailAngleZ = table.get(table.size()).get("zzz");
        Double tailAnglexOry = table.get(table.size()).get("xory");
        if(tailAngleZ == 0){
            tailAngle = 0d;
        }else if(tailAngleZ > 0 && tailAnglexOry == 0){
            tailAngle = 90d;
        }else if(tailAngleZ < 0 && tailAnglexOry == 0){
            tailAngle = -90d;
        }else{
            tailAngle = Math.toDegrees(Math.atan(tailAngleZ/tailAnglexOry));
        }
        initialAngle = new BigDecimal(initialAngle).setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
        tailAngle = new BigDecimal(tailAngle).setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double allCorner = 0d;
        for(int i = 2; i < table.size() ; i++ ){
            if(!table.get(i).get("corner").equals("null")){
                Double corner = table.get(i).get("corner");
                if(corner == 180 || corner == -180){
                    corner = 0d;
                }
                allCorner += corner;
            }
        }
        //预转角
        Double preTurningAngle = initialAngle + tailAngle + allCorner;
//        if(preTurningAngle > 90){
//            preTurningAngle = preTurningAngle - 180;
//        }else if(preTurningAngle < -90){
//            preTurningAngle = preTurningAngle + 180;
//        }
        while (preTurningAngle > 90){
            preTurningAngle = preTurningAngle - 180;
        }
        while (preTurningAngle < -90){
            preTurningAngle = preTurningAngle + 180;
        }
        if(calculatBean.getCarry() == 4){
//            if(preTurningAngle > 45){
//                preTurningAngle = preTurningAngle - 90;
//            }else if(preTurningAngle < -45){
//                preTurningAngle = preTurningAngle +90;
//            }
            while (preTurningAngle > 45){
                preTurningAngle = preTurningAngle - 90;
            }
            while (preTurningAngle < -45){
                preTurningAngle = preTurningAngle +90;
            }
        }
        if(calculatBean.getHoles() > 0){
            Double K = Double.valueOf(180/calculatBean.getHoles());
//            if(preTurningAngle > K){
//                preTurningAngle = preTurningAngle - 2*K;
//            }else if(preTurningAngle < -K){
//                preTurningAngle = preTurningAngle + 2*K;
//            }
            while (preTurningAngle > K){
                preTurningAngle = preTurningAngle - 2*K;
            }
            while (preTurningAngle < -K){
                preTurningAngle = preTurningAngle + 2*K;
            }
        }
        preTurningAngle = new BigDecimal(preTurningAngle).setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
        result.put("initialAngle",initialAngle);
        result.put("tailAngle",tailAngle);
        result.put("preTurningAngle",preTurningAngle);
        return result;

    }

}
