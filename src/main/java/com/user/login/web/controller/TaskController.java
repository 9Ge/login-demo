package com.user.login.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @GetMapping("/getTasks")
  @ResponseBody
  public String listTasks(){
    return "任务列表";
  }

  @PostMapping
  @RequestMapping("/addTask")
  @PreAuthorize("hasRole('ADMIN')")
  public String newTasks(){
    return "创建了一个新的任务";
  }  
}