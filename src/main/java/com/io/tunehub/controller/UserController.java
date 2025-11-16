package com.io.tunehub.controller;

import com.io.tunehub.dto.CreateUserDTO;
import com.io.tunehub.entity.Role;
import com.io.tunehub.entity.User;
import com.io.tunehub.service.MusicService;
import com.io.tunehub.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * UserController class that handles user-related requests.
 */
@Controller
public class UserController {


    private final UserService userService;

    private MusicService musicService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @Autowired(required = false)
    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }


    @GetMapping("/register")
    public String showRegistrationForm() {
        return "users/createUserForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute CreateUserDTO createUserDTO, RedirectAttributes ra) {

        Optional<User> optionalUser = userService.findByEmail(createUserDTO.email());
        if (optionalUser.isPresent()) {
            System.out.println("User already exists with this email id. Please try with another email id.");
            ra.addFlashAttribute("message", "Email already exists!");
            return "redirect:/login";
        } else {

            userService.addUser(createUserDTO);

            ra.addFlashAttribute("message", "Registration successful!");
            return "redirect:/login";


        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if (!BCrypt.checkpw(password, user.getHashedPassword())) {
                System.out.println("Invalid email or password");
                return "users/login";
            }

            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("profileImg",user.getAvatarBase64());
            if (session.getAttribute("role") == Role.CUSTOMER) {

                return "music/musicList";
            } else {
                return "users/adminController";
            }
        } else {
            System.out.println("Invalid email or password. Please try again.");
            return "users/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "users/login";
    }


//    @GetMapping(path = {"/health", "/status"})
//    public String health() {
//        return ;
//    }

    @GetMapping("/admin")
    public String adminAuthentication(HttpSession session) {
        if (session.getAttribute("role") == Role.ADMIN) {
            System.out.println(session.getAttribute("role"));
            return "users/adminController";
        } else
            return "music/musicList";
    }

}