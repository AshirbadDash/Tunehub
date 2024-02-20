package com.kodnest.projecttunehub.service;

import com.kodnest.projecttunehub.entity.User;

public interface UserService {

    public String addUser(User user);


    boolean emailExists(String email);

    boolean validateUser(String email, String password);
}
