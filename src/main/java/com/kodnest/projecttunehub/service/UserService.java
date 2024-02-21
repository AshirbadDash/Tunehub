package com.kodnest.projecttunehub.service;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.entity.User;

public interface UserService {



    public String addUser(User user);


  public   boolean emailExists(String email);

    public boolean validateUser(String email, String password);


    String getRole(String email);
}
