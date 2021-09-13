package com.user.login.user.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 * @Date: 2021/9/13 13:46<br/>
 * @Author: yangcheng
 * @Version: 1.0
 */
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private String id;
    private String sex;

    @Transient
    private String faceImage;

    @NotBlank(message = "登录名称不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String pwd;

    private String role;

}
