package com.example.demo1_flange.shiro;


import com.alibaba.fastjson.JSON;
import com.example.demo1_flange.common.ApiResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author why
 */
public class ShiroUtil {


	/**
	 * 统一返回前端json数据
	 * 
	 * @param response
	 *
	 */
	public static void writeResponse(HttpServletResponse response, String message) {
		try {
			response.setContentType("application/json");
			OutputStream outputStream = response.getOutputStream();
			ApiResult result = new ApiResult().setMessage(message).setCode(-8);
			outputStream.write(JSON.toJSONString(result).getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}