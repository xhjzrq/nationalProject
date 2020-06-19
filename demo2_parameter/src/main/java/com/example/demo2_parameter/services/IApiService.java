package com.example.demo2_parameter.services;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2_parameter.bean.DeleteBean;
import com.example.demo2_parameter.bean.RequestBean;
import com.example.demo2_parameter.bean.UserBean;


import java.util.List;
import java.util.Map;

/**
 * @author why
 */

public interface IApiService {


    UserBean getUser(String username);

    Map<String,JSONObject> userList(RequestBean requestBean);

    Boolean createUser(UserBean userBean);


    boolean deletePublic(List<DeleteBean> deleteBeans);

    String  getConf(String username);
}
