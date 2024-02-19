package com.kodnest.projecttunehub.serviceimpl;


import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.repository.UserRepository;
import com.kodnest.projecttunehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository userRepository;


    public String addUser(User user) {
        userRepository.save(user);
        return "User added successfully";
    }



}
