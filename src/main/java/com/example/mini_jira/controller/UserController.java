package com.example.mini_jira.controller;

import com.example.mini_jira.entity.User;

import com.example.mini_jira.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private Userservice userService;


    @GetMapping("/")
    public String hello()
    {
        return "Hello jira";
    }

    @GetMapping("/getUsers")
    public List<?> listUsers()
    {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public String register(@RequestBody User user)
    {
        userService.saveUser(user);
        return "Added successfully";
    }

    @GetMapping("/admin")
    public String adminDashboard()
    {
        return "Admin Dashboard";
    }
    @GetMapping("/user")
    public String userDashboard()
    {
        return "user Dashboard";
    }
}
