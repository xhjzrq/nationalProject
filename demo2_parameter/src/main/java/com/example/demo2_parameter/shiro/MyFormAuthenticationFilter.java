package com.example.demo2_parameter.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

    @Autowired
    private DefaultWebSecurityManager securityManager;

    /**
     * 登陆验证
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//    	System.out.println("((HttpServletRequest)request).getRequestURI()----------------"+((HttpServletRequest)request).getRequestURI());
    	String loginname = ((HttpServletRequest)request).getRequestURI();
    	UsernamePasswordToken token = createToken(request, response);
//    	if(((HttpServletRequest)request).getRequestURI().equals("ukeylogin")){
//    		     token = createToken_ukey(request, response);
//    	}else{
//    		     token = createToken(request, response);    
//    	}
    	
    	   
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
        	e.printStackTrace();
            LOGGER.error("登录失败." + e);
            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 登陆成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        httpServletRequest.setAttribute("success", true);
        return true;
    }

    /**
     * 创建token
     */

    @Override
    protected UsernamePasswordToken createToken(ServletRequest request, ServletResponse response ) {
    	String username = null;
    	String password = null;


    			username = getUsername(request);
        	    password = getPassword(request);

        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
//        System.out.println(username+"------"+password+"--------"+rememberMe+"----"+host);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
    }

    /**
     * 未登录拦截处理
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Login page view.");
                }
                return true;
            }
        } else {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            if (isAjax(request)) {
                ShiroUtil.writeResponse((HttpServletResponse) response, "您的登录已失效，请重新登录本系统！");
            } else {
                this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

    private static boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
