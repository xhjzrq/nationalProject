package com.example.demo3_pipeintellect.services;

import com.alibaba.fastjson.JSONObject;
import com.example.demo3_pipeintellect.bean.DeleteBean;
import com.example.demo3_pipeintellect.bean.RequestBean;
import com.example.demo3_pipeintellect.bean.UserBean;


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
