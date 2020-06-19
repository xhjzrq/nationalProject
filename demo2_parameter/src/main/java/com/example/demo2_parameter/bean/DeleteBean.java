/**
 * Copyright (C), 2015-2020, XXX有限公司
 * <p>
 * FileName: DeleteBean
 * <p>
 * Author:   why
 * <p>
 * Date:     2020/3/1 19:03
 * <p>
 * Description: DeleteBean
 * <p>
 * History:
 *
 * <author>          <time>          <version>          <desc>
 * <p>
 * 作者姓名           修改时间           版本号              描述
 */


package com.example.demo2_parameter.bean;


import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈DeleteBean〉
 *
 * @author why

 * @create 2020/3/1

 * @since 1.0.0

 */
@Data
public class DeleteBean implements Serializable {

    private static final long serialVersionUID = -956276294804116127L;

    public String username;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
