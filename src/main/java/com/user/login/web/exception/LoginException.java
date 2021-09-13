package com.user.login.web.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Date: 2021/9/13 16:55<br/>
 * @Author: yangcheng
 * @Version: 1.0
 */
public class LoginException extends AuthenticationException {

    public LoginException(String msg) {
        super(msg);
    }
}
