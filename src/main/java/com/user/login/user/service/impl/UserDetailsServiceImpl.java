package com.user.login.user.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.user.login.user.mapper.UserMapper;
import com.user.login.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User queryUser = new User();
        queryUser.setName(username);
        User user = userMapper.selectOne(queryUser);
        if(user == null){
            throw new RuntimeException("the user don't exist");
        }
//        if (user == null) return null;

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getName()).password(user.getPwd()).authorities(user.getRole()).build();
        return userDetails;
    }
}