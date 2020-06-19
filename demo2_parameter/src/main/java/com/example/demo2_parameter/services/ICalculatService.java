package com.example.demo2_parameter.services;




import com.example.demo2_parameter.bean.CalculatBean;

import java.util.Map;

/**
 * @author why
 */
public interface ICalculatService {


     Map<String,Map<Integer,Map<String,String>>> getResult(CalculatBean calculatBean);
}
