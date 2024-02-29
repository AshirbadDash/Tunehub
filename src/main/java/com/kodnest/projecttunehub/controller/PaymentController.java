package com.kodnest.projecttunehub.controller;


import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {
    @Autowired
    UserService userService;


    @GetMapping("/pay")
    public String pay(@ModelAttribute User user) {


        boolean isPremium = user.isPremium();
        if (isPremium) {
            return "PremiumUser";
        }else {
            return "Pay";
        }

    }

    @SuppressWarnings("finally")
    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder(HttpSession session) {

        int amount = 50;
        Order order = null;
        try {
            RazorpayClient razorpay = new RazorpayClient("rzp_test_nyLo8f8zHUX1ZS", "J1Heyv31cKECzT72hcDihxF9");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");

            order = razorpay.orders.create(orderRequest);

            String mail = (String) session.getAttribute("email");

            User user = userService.getUser(mail);
            user.setPremium(true);
            userService.updateUser(user);



        } catch (RazorpayException e) {
            e.printStackTrace();
        } finally {
            return order.toString();

        }
    }
}