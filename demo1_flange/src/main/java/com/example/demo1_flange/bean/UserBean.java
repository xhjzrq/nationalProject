/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: UserBean
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/2/28 23:33
 * <p>
 * Description: UserBean
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo1_flange.bean;


import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈UserBean〉
 *
 * @author why

 * @create 2020/2/28

 * @since 1.0.0

 */
@Data
public class UserBean implements Serializable {


    private static final long serialVersionUID = -6589823624250648823L;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String company;
    private String department;
    private String remarks;
    private String functionAPI;

    public String getFunctionAPI() {
        return functionAPI;
    }

    public void setFunctionAPI(String functionAPI) {
        this.functionAPI = functionAPI;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
