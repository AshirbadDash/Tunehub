package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String addUser(@ModelAttribute User user) {


        userService.addUser(user);

        return "home";
    }


}



