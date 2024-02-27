package com.kodnest.projecttunehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {


    @GetMapping("/pay")
    public String pay() {
        return "Pay";
    }



}
