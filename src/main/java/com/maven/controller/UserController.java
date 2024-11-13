package com.maven.controller;

import com.maven.modules.User;
import com.maven.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    public UserService userService;

    @GetMapping("/test")
    public String testApi()
    {
        return "Test";
    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }


}
