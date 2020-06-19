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


package com.example.demo2_parameter.services.impl;



import com.example.demo2_parameter.bean.CalculatBean;
import com.example.demo2_parameter.services.ICalculatService;
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
     * 算法
     *
     */
    @Override
    public Map<String,Map<Integer,Map<String,String>>> getResult(CalculatBean CalculatBean){
        Map<String,Map<Integer,Map<String,String>>> inputMap = CalculatBean.getInputMap();
        Map<Double,Map<String,String>> parameter = CalculatBean.getParameter();
//        System.out.println(parameter);

        //结果返回列表，计算获得，map结构：{管件号：{序号{参数名：参数值}}}，参数与inputMap中的参数相同，另多出4个参数：ModifiedCorner 修正曲角，CorrectionOfStartingPoint 修正起弯点，CorrectionOfStraightPipeSection 修正直管段，BlankingLength 下料长度
        Map<String,Map<Integer,Map<String,String>>> result = new HashMap<>();

        /*
         * 计算
         * */
        for (String Pipe : inputMap.keySet()
        ) {
            Map<Integer,Map<String,String>> intpuPipeMap = inputMap.get(Pipe);//获取相同管件号的数据
            Map<String,String> parameterPreviousMap = new HashMap<>();//上一个参数行
            Double allExtend = 0d;//所有延伸
            Double allCorrectionOfStraightPipeSection = 0d;//所有修正直管段
            Double allArcLength = 0d;//所有弧长
            Double BlankingLength = 0d; //下料长度
            Double SwFalan = 0d;
            Map<Integer,Map<String,String>> resultPipeMap = inputMap.get(Pipe);//相同管件号的数据的计算结果
            for (int i = 1; i <= intpuPipeMap.size(); i++) {
                Map<String,String> nMap = intpuPipeMap.get(i);//获取单行数据
                Map<String,String> parameterLineMap = new HashMap<>();
                if(i != intpuPipeMap.size()){
                    parameterLineMap = parameter.get(Double.valueOf(nMap.get("alpha")));//通过曲角获取对应的csv中的参数
                }
                Double ModifiedCorner = 0d;//修正后曲角
                Double CorrectionOfStartingPoint = 0d; //修正起弯点
                Double CorrectionOfStraightPipeSection = 0d; //修正直管段

                if(i > 1 && i < intpuPipeMap.size()){
                    ModifiedCorner = Double.valueOf(parameterLineMap.get("Theta"));
                    CorrectionOfStartingPoint = Double.valueOf(nMap.get("length")) + Double.valueOf(parameterPreviousMap.get("ArcLength")) - Double.valueOf(parameterPreviousMap.get("TailCut")) - Double.valueOf(parameterLineMap.get("FirstCut")) - Double.valueOf(parameterPreviousMap.get("Extend"));
                    CorrectionOfStraightPipeSection =  Double.valueOf(nMap.get("length")) - Double.valueOf(parameterPreviousMap.get("TailCut")) - Double.valueOf(parameterLineMap.get("FirstCut"));
                    parameterPreviousMap = parameterLineMap;
                    allExtend += Double.valueOf(parameterLineMap.get("Extend"));
                    allArcLength += Double.valueOf(parameterLineMap.get("ArcLength"));
                }else{
                    if(i == 1){
//                        System.out.println(parameterLineMap);
                        ModifiedCorner = Double.valueOf(parameterLineMap.get("Theta"));
                        CorrectionOfStartingPoint = Double.valueOf(nMap.get("length")) - Double.valueOf(parameterLineMap.get("FirstCut"));//首段获取修正起弯点
                        CorrectionOfStraightPipeSection = Double.valueOf(nMap.get("length")) - Double.valueOf(parameterLineMap.get("FirstCut"));//首段修正直管段
                        parameterPreviousMap = parameterLineMap;
                        allExtend += Double.valueOf(parameterLineMap.get("Extend"));
                        allArcLength += Double.valueOf(parameterLineMap.get("ArcLength"));
                        SwFalan += Double.valueOf(nMap.get("flangeReduction"));
                    }else{
                        CorrectionOfStartingPoint = Double.valueOf(nMap.get("length")) + Double.valueOf(parameterPreviousMap.get("ArcLength")) - Double.valueOf(parameterPreviousMap.get("TailCut")) - Double.valueOf(parameterPreviousMap.get("Extend"));
                        CorrectionOfStraightPipeSection = Double.valueOf(nMap.get("length")) - Double.valueOf(parameterPreviousMap.get("TailCut")) - allExtend;
                        SwFalan += Double.valueOf(nMap.get("flangeReduction"));
                    }
                }
                allCorrectionOfStraightPipeSection += CorrectionOfStraightPipeSection;
                if(i != intpuPipeMap.size()){
                    ModifiedCorner = new BigDecimal(ModifiedCorner).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                }

                CorrectionOfStartingPoint =  new BigDecimal(CorrectionOfStartingPoint).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                CorrectionOfStraightPipeSection =  new BigDecimal(CorrectionOfStraightPipeSection).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                nMap.put("ModifiedCorner",ModifiedCorner.toString());
                nMap.put("CorrectionOfStartingPoint",CorrectionOfStartingPoint.toString());
                nMap.put("CorrectionOfStraightPipeSection",CorrectionOfStraightPipeSection.toString());
                resultPipeMap.put(i,nMap);
            }
            BlankingLength = allCorrectionOfStraightPipeSection + allArcLength - SwFalan;
            Map<String,String> fristMap = resultPipeMap.get(1);
            BlankingLength =  new BigDecimal(BlankingLength).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            fristMap.put("BlankingLength",BlankingLength.toString());
            resultPipeMap.put(1,fristMap);
            result.put(Pipe,resultPipeMap);
        }

        return result;
    }
}
