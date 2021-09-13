package com.user.login.web.controller;

import com.user.login.user.model.User;
import com.user.login.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class AuthController {
 
  @Autowired
  private UserService userService;
 
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
 
  @PostMapping("/register")
  public String registerUser(@RequestBody @Validated  User user){
    user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
    user.setRole("ROLE_USER");
    userService.save(user);
    return "success";
  }
}