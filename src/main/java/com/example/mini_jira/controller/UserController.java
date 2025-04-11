package com.example.mini_jira.controller;

import com.example.mini_jira.entity.User;

import com.example.mini_jira.service.JwtService;
import com.example.mini_jira.service.Userservice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private Userservice userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("login")
    public String login(@RequestBody User user){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        else
            return "Login Failed";

    }
    @GetMapping("/")
    public String hello(HttpServletRequest request)
    {
        return "Hello jira "+request.getSession().getId();
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
