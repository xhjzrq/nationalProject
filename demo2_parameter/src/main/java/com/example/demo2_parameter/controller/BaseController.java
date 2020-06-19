/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: BaseController
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/28 23:50
 * <p>
 * Description: BaseController
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo2_parameter.controller;


import com.example.demo2_parameter.bean.UserBean;
import com.example.demo2_parameter.common.ApiResult;
import org.apache.shiro.SecurityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br> 
 * 〈BaseController〉
 *
 * @author why

 * @create 2020/2/28

 * @since 1.0.0

 */

public class BaseController {
    /**
     * 获取当前登录用户
     *
     * @return
     */
    public UserBean getCurrentUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return (UserBean) principal;
    }


    /**
     * 设置当前登录用户
     *
     * @param request
     * @return
     */
    public void setCurrentUser(HttpServletRequest request, UserBean userBean) {
        request.getSession().setAttribute("CURRENT_USER", userBean);
    }

    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    protected ApiResult success(Object obj) {
        return new ApiResult().setData(obj).setCode(0).setMessage("消息返回成功");
    }

    protected ApiResult error(String message) {
        return new ApiResult().setMessage(message).setCode(-1);
    }


    protected ApiResult successPagesNull(Object object) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("dataList", null);
        result.put("totalNumber",0);
        result.put("totalPage",0);
        result.put("pageNumber",0);
        return new ApiResult().setData(result).setCode(0).setMessage("消息返回成功");
    }

}
