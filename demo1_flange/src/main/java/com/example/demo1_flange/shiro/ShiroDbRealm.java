package com.example.demo1_flange.shiro;

import com.alibaba.fastjson.JSONObject;
import com.example.demo1_flange.bean.UserBean;
import com.example.demo1_flange.util.DealWithFile;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 身份校验核心类
 *
 * @author why
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroDbRealm.class);



    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOGGER.info("===============进行权限配置================");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserBean user = (UserBean) principals.getPrimaryPrincipal();
        return authorizationInfo;
    }

    /**
     * 认证身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LOGGER.info("===============进行登陆认证================");
        UsernamePasswordToken myToken = (UsernamePasswordToken) token;
        UserBean user = getUser(myToken.getUsername(), String.valueOf(((UsernamePasswordToken) token).getPassword()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = format.format(new Date());
        if (user == null) {
            LOGGER.debug("user {} is not exist.", myToken.getUsername());
            throw new IncorrectCredentialsException();
        }

        Object principal = user;
        Object credentials = user.getPassword();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                principal, credentials, getName());

        return authenticationInfo;
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
        UserBean user = (UserBean) principals.getPrimaryPrincipal();
        removeUserCache(user);
    }

    /**
     * 清除用户缓存
     *
     * @param user
     */
    public void removeUserCache(UserBean user) {
        removeUserCache(user.getUsername());
    }

    /**
     * 清除用户缓存
     *
     * @param loginName
     */
    public void removeUserCache(String loginName) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(loginName, super.getName());
        super.clearCachedAuthenticationInfo(principals);
    }
/**
 * 查询单个用户
 */

public UserBean getUser(String username, String password){
    UserBean user = new UserBean();
    Map<String,JSONObject> userMap = new HashMap();
    userMap = DealWithFile.getUsers();
    for (String key : userMap.keySet()
    ) {
        if(username.equals(key)){
            JSONObject json = userMap.get(key);
            if(password.equals(json.getString("password"))){
                user.setUsername(json.getString("username"));
                user.setCompany(json.getString("company"));
                user.setDepartment(json.getString("department"));
                user.setName(json.getString("name"));
                user.setPassword(json.getString("password"));
                user.setRemarks(json.getString("remarks"));
                user.setPhone(json.getString("phone"));


            }
        }
    }
    return user ;
}




}
