package com.example.mini_jira.controller;

import com.example.mini_jira.entity.User;

import com.example.mini_jira.service.JwtService;
import com.example.mini_jira.service.Userservice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController
{
    @Autowired
    private Userservice userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

//    @PostMapping("login")
//    public String login(@RequestBody User user) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        if (authentication.isAuthenticated())
//            return jwtService.generateToken(user.getUsername());
//        else
//            return "Login Failed";
//    }
@PostMapping("login")
public ResponseEntity<?> login(@RequestBody User user) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
    );

    if (authentication.isAuthenticated()) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(jwtService.generateToken(user.getUsername()));
        String username = userDetails.getUsername();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtService.generateToken(username);

        //  Print values for debugging
        System.out.println("Username: " + username);
        System.out.println("Roles: " + roles);
        System.out.println("Token: " + token);

        Map<String, Object> response = new HashMap<>();
     //   response.put("token", token);
        response.put("username", username);
        response.put("roles", roles);
        response.put("Token",token);

        return ResponseEntity.ok(response);
    } else {
        System.out.println("Login failed for user: " + user.getUsername());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
    }
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
