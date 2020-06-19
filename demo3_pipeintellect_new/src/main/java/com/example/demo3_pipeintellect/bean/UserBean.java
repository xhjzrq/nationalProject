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


package com.example.demo3_pipeintellect.bean;


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




}
