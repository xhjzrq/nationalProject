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


package com.example.demo3_pipeintellect.bean;


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
     * 管件下料明细
     * pipe == 管件号
     * id == 序号/编号
     * specifications == 规格
     * material == 材质
     * length == 料长
     */
    private Map<String,String>[] inputOfXlmx;

    /**
     * 管材测长数据
     * id == 序号/编号
     * specifications == 规格
     * material == 材质
     * length == 料长
     * count == 管材测长表根数
     */
    private Map<String,Map<String, String>> inputOfCcsj  = new HashMap<>();

    /**
     * 条件参数
     * slit == 切缝宽度
     */
    private Map<String,String > inputOfCondition  = new HashMap<>();


}
