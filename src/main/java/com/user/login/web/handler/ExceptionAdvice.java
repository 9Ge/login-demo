package com.user.login.web.handler;

import com.user.login.web.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

// 定义异常处理类
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    private static Logger L = LoggerFactory.getLogger(ExceptionAdvice.class);

    // 对所有的ParamsException统一进行拦截处理，如果捕获到该异常，则封装成MessageBody返回给前端
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleParamsException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        //按需重新封装需要返回的错误信息
        List<String> invalidArguments = new ArrayList<>();
//解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            log.info("{}", error.getRejectedValue());
            log.info("{}", error.getField());
            invalidArguments.add(error.getDefaultMessage());
        }
        return invalidArguments;
    }

    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleLoginException(HttpServletRequest request, LoginException exception) {
        //按需重新封装需要返回的错误信息
        return exception.getMessage();
    }


}