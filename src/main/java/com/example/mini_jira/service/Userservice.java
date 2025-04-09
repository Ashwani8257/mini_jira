package com.example.mini_jira.service;

import com.example.mini_jira.entity.User;
import com.example.mini_jira.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Userservice
{
    @Autowired
    private UserRepository repo;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public User saveUser(User user)
    {
//        if (user.getId() != null) {
//            // It's an update
//            User existingUser = repo.findById(user.getId())
//                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getId()));
//
//            existingUser.setUsername(user.getUsername());
//            existingUser.setRole(user.getRole());
//
//            // Update password only if changed (optional)
//            if (!user.getPassword().startsWith("$2a$")) { // Bcrypt passwords start with $2a$
//                existingUser.setPassword(encoder.encode(user.getPassword()));
//            }
//
//            return repo.save(existingUser);
//        } else {
            // It's a new user
            user.setPassword(encoder.encode(user.getPassword()));
            return repo.save(user);
        }
        public List<?> getUsers()
        {
            return repo.findAll();
        }
    }

