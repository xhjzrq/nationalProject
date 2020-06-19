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


package com.example.demo3_pipeintellect.controller;


import com.alibaba.fastjson.JSONObject;

import com.example.demo3_pipeintellect.bean.CalculatBean;
import com.example.demo3_pipeintellect.bean.DeleteBean;
import com.example.demo3_pipeintellect.bean.RequestBean;
import com.example.demo3_pipeintellect.bean.UserBean;
import com.example.demo3_pipeintellect.common.ApiResult;
import com.example.demo3_pipeintellect.services.IApiService;
import com.example.demo3_pipeintellect.services.ICalculatService;
import com.example.demo3_pipeintellect.util.ExcelUtils;
import com.example.demo3_pipeintellect.util.configWithFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
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
     * 无余角计算
     * @return
     */
    @PostMapping(value = "calculation")
    public ApiResult getCalculationResult(@RequestBody CalculatBean calculatBean) {
        JSONObject  json = new JSONObject () ;
//          System.out.println("进入到无余角计算中！！！");
//        System.out.println(calculatBean.getInputOfXlmx());
//        System.out.println(calculatBean.getInputOfCcsj());
//        System.out.println(calculatBean.getInputOfCondition());

        Map<String,JSONObject> userMap = new HashMap();
        userMap = configWithFile.getUsers("conf.text");
        JSONObject json1 = new JSONObject();
        json1.put("DeviceNo",calculatBean.getInputOfCondition().get("DeviceNo"));
        json1.put("slit",calculatBean.getInputOfCondition().get("slit"));
        json1.put("smallOddments",calculatBean.getInputOfCondition().get("smallOddments"));
        json1.put("smallLength",calculatBean.getInputOfCondition().get("smallLength"));
        json1.put("feiliao",calculatBean.getInputOfCondition().get("feiliao"));
        json1.put("username",this.getCurrentUser().getUsername());
        userMap.put("admin",json1);
        configWithFile.writetxtfile(userMap,"conf.text");


        json = calculatService.getResult(calculatBean);
        //System.out.println(json);
        return success(json);
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

    /**
     * 文件上传
     */
    @RequestMapping("upload")
    @ResponseBody
    public Map uploadFile(MultipartFile file){
        Map<String,Object> map = new HashMap<>(16);
        //调用工具类解析excel文件
        List<ArrayList<String>> row = ExcelUtils.excelUtils(file);
        //打印信息

        map.put("status","success");
        map.put("data",row);

        return map;
    }


}
