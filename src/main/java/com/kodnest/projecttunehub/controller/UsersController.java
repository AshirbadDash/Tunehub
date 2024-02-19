package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @Autowired
    public UserService userService;


    @PostMapping( "/register")
    public String addUser(@ModelAttribute User user) {
        // Add user to the database
//
//        System.out.println(user.getUsername());     // For testing
//        System.out.println(user.getEmail());     // For testing
//        System.out.println(user.getPassword());     // For testing
//        System.out.println(user.getGender());     // For testing
//        System.out.println(user.getRole());     // For testing
//        System.out.println(user.getAddress());     // For testing

        userService.addUser(user);

        return "home";
    }


//    @PostMapping ( "/register")
//    public String postUser(@ModelAttribute User user) {
//        userService.addUser(user);
//        return "home";
//    }


}



