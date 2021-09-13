package com.user.login.user.service;

import com.user.login.user.model.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @Date: 2021/9/13 14:11<br/>
 * @Author: yangcheng
 * @Version: 1.0
 */
public interface UserService {
    boolean loginUser( User user);

    boolean save( User user);
}
