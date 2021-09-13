package com.user.login.user.service.impl;

import com.user.login.user.mapper.UserMapper;
import com.user.login.user.model.User;
import com.user.login.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @Date: 2021/9/13 14:11<br/>
 * @Author: yangcheng
 * @Version: 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean loginUser( User queryUser ){
        User resultUser = userMapper.selectOne(queryUser);
        return resultUser != null;
    }

    @Override
    public boolean save(User user) {
        user.setId(UUID.randomUUID().toString().replace("-",""));
        userMapper.insert(user);
        return true;
    }

}
