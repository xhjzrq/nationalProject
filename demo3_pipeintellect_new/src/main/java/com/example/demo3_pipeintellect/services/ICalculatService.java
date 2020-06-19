package com.example.demo3_pipeintellect.services;






import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3_pipeintellect.bean.CalculatBean;

import java.util.Map;

/**
 * @author why
 */
public interface ICalculatService {


     JSONObject getResult(CalculatBean calculatBean);
}
