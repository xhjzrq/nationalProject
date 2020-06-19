/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: LoginController
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/28 23:19
 * <p>
 * Description: LoginController
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo3_pipeintellect.controller;




import com.example.demo3_pipeintellect.bean.UserBean;
import com.example.demo3_pipeintellect.common.ApiResult;
import com.example.demo3_pipeintellect.services.IApiService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br> 
 * 〈LoginController〉
 *
 * @author why

 * @create 2020/2/28

 * @since 1.0.0

 */
@RestController
public class LoginController extends BaseController{
     @Resource
     private IApiService apiService;


    @PostMapping(value = "/login")
    public ApiResult login (HttpServletRequest request, String password, String username, String type){
        if (request.getAttribute("success") != null && (boolean) request.getAttribute("success")) {
            UserBean user = new UserBean();
            user = apiService.getUser(username);

            return success(user);

        }
        return error("登录失败！");
    }

    /**
     * 退出系统
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/logout")
    @ResponseBody
    public ApiResult logout() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String loginMode= "login";
        try {
            subject.logout();
        } catch (Exception e) {
            return error("退出操作失败");
        }
        return success(loginMode);

        }
}
