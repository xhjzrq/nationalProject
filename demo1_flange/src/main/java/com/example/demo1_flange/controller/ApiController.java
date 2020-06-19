/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: ApiController
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/29 2:35
 * <p>
 * Description: ApiController
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo1_flange.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo1_flange.bean.CalculatBean;
import com.example.demo1_flange.bean.DeleteBean;
import com.example.demo1_flange.bean.RequestBean;
import com.example.demo1_flange.bean.UserBean;
import com.example.demo1_flange.common.ApiResult;
import com.example.demo1_flange.service.IApiService;
import com.example.demo1_flange.service.ICalculatService;
import com.example.demo1_flange.util.configWithFile;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈ApiController〉
 *
 * @author why

 * @create 2020/2/29

 * @since 1.0.0

 */
@RestController
@RequestMapping(value = "api")
public class ApiController extends BaseController {

    @Resource
    IApiService apiService;
    @Resource
    ICalculatService calculatService;

    /**
     * 查询单个用户
     * @return
     */
    @PostMapping(value = "findUser")
    public ApiResult getUser (@RequestBody DeleteBean deleteBean){
     UserBean user = new UserBean();
     user = apiService.getUser(deleteBean.getUsername());
    return success(user);
}

    /**
     * 查询所有用户
     * @return
     */
    @PostMapping(value = "userList")
    public ApiResult userList (@RequestBody RequestBean requestBean){
        Map<String,JSONObject> user ;
        user = apiService.userList(requestBean);
//        System.out.println(user);
        return success(user);
    }

    /**
     * 添加修改用户
     * @param userBean
     * @return
     */
    @PostMapping("userCreate")
    public ApiResult userCreate (@RequestBody UserBean userBean ){
        Boolean flag ;
        flag = apiService.createUser(userBean);
        if (flag==false){
            return error("User already exists");
        }
        return success(flag);
    }

    /**
     * 删除用户
     */
    @PostMapping(value = "deletePublic")
    public ApiResult deletePublic(@RequestBody List<DeleteBean> deleteBeans){
        boolean flag;
        flag = apiService.deletePublic(deleteBeans);
        return success(flag);
    }


    /**
     * 法兰计算
     * @return
     */
    @PostMapping(value = "calculation")
    public Map<String,Double> getCalculationResult(@RequestBody CalculatBean calculatBean) {
//        System.out.println("进入到法兰技术后台！！！！");
//        System.out.println(calculatBean.getTable());
//        System.out.println(calculatBean.getHoles());
//        System.out.println(calculatBean.getAccuracy());
//        System.out.println(calculatBean.getCarry());
        Map<String,JSONObject> userMap = new HashMap();
        userMap = configWithFile.getUsers("conf.text");
        JSONObject json = new JSONObject();
        json.put("holes",calculatBean.getHoles());
        json.put("accuracy",calculatBean.getAccuracy());
        json.put("carry",calculatBean.getCarry());
        json.put("username",this.getCurrentUser().getUsername());
        userMap.put("admin",json);
        configWithFile.writetxtfile(userMap,"conf.text");
        Map<String,Double> resultMap = calculatService.getResult(calculatBean);
        return resultMap;
    }

    /**
     * 查询配置文件
     * @return
     */
    @PostMapping(value = "getConf")
    public ApiResult getConf (){
        JSONObject json ;
        String username = this.getCurrentUser().getUsername();
        json = apiService.getConf(username);
        return success(json);
    }
}
