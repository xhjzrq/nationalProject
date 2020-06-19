/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: RequestUtil
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/29 15:50
 * <p>
 * Description: RequestUtil
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo3_pipeintellect.bean;


import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈RequestUtil〉
 *
 * @author why

 * @create 2020/2/29

 * @since 1.0.0

 */
@Data
public class RequestBean {


    private Integer pageNum;
    private Integer pageSize;
    private String username;


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
