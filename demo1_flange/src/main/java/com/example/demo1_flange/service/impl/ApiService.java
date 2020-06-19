/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: ApiService
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/29 2:52
 * <p>
 * Description: ApiService
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo1_flange.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.example.demo1_flange.bean.DeleteBean;
import com.example.demo1_flange.bean.RequestBean;
import com.example.demo1_flange.bean.UserBean;
import com.example.demo1_flange.service.IApiService;
import com.example.demo1_flange.util.DealWithFile;
import com.example.demo1_flange.util.configWithFile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈ApiService〉
 *
 * @author why

 * @create 2020/2/29

 * @since 1.0.0

 */
@Service
public class ApiService implements IApiService {



   @Override
   public UserBean getUser(String username){
      UserBean user = new UserBean();
       Map<String,JSONObject> userMap = new HashMap();
       userMap = DealWithFile.getUsers();
       for (String key : userMap.keySet()
       ) {
           if(username.equals(key)){
               JSONObject json = userMap.get(key);

                   user.setUsername(json.getString("username"));
                   user.setCompany(json.getString("company"));
                   user.setDepartment(json.getString("department"));
                   user.setName(json.getString("name"));
                   user.setPassword(json.getString("password"));
                   user.setRemarks(json.getString("remarks"));
                   user.setPhone(json.getString("phone"));

           }
       }
       return user ;
   }

    @Override
    public Map<String,JSONObject> userList(RequestBean requestBean) {
        Map<String,JSONObject> userMap = new HashMap();
        userMap = DealWithFile.getUsers();
        return userMap;
    }

    @Override
    public Boolean createUser(UserBean userBean) {
        Map<String,JSONObject> userMap = new HashMap();
        userMap = DealWithFile.getUsers();
        for(String key : userMap.keySet()){
            if(userBean.getUsername().equals(key) && userBean.getFunctionAPI().equals("add")){
                return false;
            }
        }
        JSONObject json = new JSONObject();
           json.put("username",userBean.getUsername());
           json.put("password",userBean.getPassword());
           json.put("name",userBean.getName());
           json.put("phone",userBean.getPhone());
           json.put("company",userBean.getCompany());
           json.put("department",userBean.getDepartment());
           json.put("remarks",userBean.getRemarks());

           userMap.put(json.get("username").toString(),json);
           DealWithFile.writetxtfile(userMap);
           return true;


    }

    @Override
    public boolean deletePublic(List<DeleteBean> deleteBeans) {
        boolean flag = false;
         for (int i = 0; i <deleteBeans.size(); i++ ){
             Map userMap = new HashMap();
             userMap = DealWithFile.getUsers();
             userMap.remove(deleteBeans.get(i).getUsername());
             flag = DealWithFile.writetxtfile(userMap);
         }

        return flag;
    }

    @Override
    public JSONObject getConf( String username) {
       JSONObject json = new JSONObject();
        Map<String,JSONObject> userMap = new HashMap();
        userMap = configWithFile.getUsers("conf.text");
        for (String key : userMap.keySet()
        ) {
            if(username.equals(key)){
                json = userMap.get(key);

            }
        }

        return json;
    }


}

