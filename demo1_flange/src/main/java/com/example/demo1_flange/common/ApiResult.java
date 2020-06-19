/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: ApiResult
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/28 23:27
 * <p>
 * Description: ApiResult
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo1_flange.common;


import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈ApiResult〉
 *
 * @author why

 * @create 2020/2/28

 * @since 1.0.0

 */

public class ApiResult implements Serializable {
    private static final long serialVersionUID = 8052566108151898248L;
    public static final String SUCCESS_MESSAGE_CODE = "front.common.success";
    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int LOGIN_ERROR = -3;
    public static final int SYSTEM_ERROR = -4;
    private int code = 0;
    private String message = "";
    private Object data;

    public int getCode() {
        return this.code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ApiResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public <T> T getData() {
        return (T) this.data;
    }

    public ApiResult setData(Object data) {
        this.data = data;
        return this;
    }
}