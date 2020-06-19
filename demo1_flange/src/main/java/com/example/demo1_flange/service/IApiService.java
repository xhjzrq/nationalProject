package com.example.demo1_flange.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo1_flange.bean.DeleteBean;
import com.example.demo1_flange.bean.RequestBean;
import com.example.demo1_flange.bean.UserBean;
import org.apache.poi.ss.formula.functions.T;

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

    JSONObject getConf(String username);
}
