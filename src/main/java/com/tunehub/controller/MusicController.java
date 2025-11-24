//package com.io.tunehub.controller;
//
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//public class MusicController {
//
//    @GetMapping
//    public String uploadFile(@RequestParam("title") String title ,
//                             @RequestParam("artistName") String artistName,
//                             @RequestParam(value = "genre", required = false) String genre,
//                             @RequestParam(name="audioFile") MultipartFile audioFile, HttpSession session, Model Model) {
//
//
//
//        return "music/uploadMusic";
//    }
//
//
//}