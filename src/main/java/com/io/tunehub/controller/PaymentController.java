//package com.kodnest.projecttunehub.controller;
//
//import com.kodnest.projecttunehub.entity.User;
//import com.kodnest.projecttunehub.service.UserService;
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//import com.razorpay.Utils;
//import jakarta.servlet.http.HttpSession;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class PaymentController {
//    @Autowired
//    UserService userService;
//
//    @GetMapping("/pay")
//    public String pay(@ModelAttribute User user) {
//
//        boolean isPremium = user.isPremium();
//        if (isPremium) {
//            return "DisplaySongs";
//        } else {
//            return "Pay";
//        }
//
//    }
//
//    @SuppressWarnings("finally")
//    @PostMapping("/createOrder")
//    @ResponseBody
//    public String createOrder(HttpSession session) {
//
//        int amount = 1;
//        Order order = null;
//        try {
//            RazorpayClient razorpay = new RazorpayClient("rzp_test_nyLo8f8zHUX1ZS", "J1Heyv31cKECzT72hcDihxF9");
//
//            JSONObject orderRequest = new JSONObject();
//            orderRequest.put("amount", amount * 100); // amount in the smallest currency unit
//            orderRequest.put("currency", "INR");
//            orderRequest.put("receipt", "order_rcptid_11");
//
//            order = razorpay.orders.create(orderRequest);
//
//        } catch (RazorpayException e) {
//            e.printStackTrace();
//        } finally {
//            return order.toString();
//
//        }
//    }
//
//    @PostMapping("/verify")
//    @ResponseBody
//    public boolean verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,
//                                 @RequestParam String signature) {
//        try {
//            // Initialize Razorpay client with your API key and secret
//            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_nyLo8f8zHUX1ZS", "J1Heyv31cKECzT72hcDihxF9");
//            // Create a signature verification data string
//            String verificationData = orderId + "|" + paymentId;
//
//            // Use Razorpay's utility function to verify the signature
//            boolean isValidSignature = Utils.verifySignature(verificationData, signature, "J1Heyv31cKECzT72hcDihxF9");
//
//            return isValidSignature;
//        } catch (RazorpayException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @GetMapping("/paymentSuccess")
//    public String paymentSuccess(HttpSession session, Model model) {
//        String mail = (String) session.getAttribute("email");
//
//        // Debugging: Print all session attributes
//        session.getAttributeNames().asIterator()
//                .forEachRemaining(attr -> System.out.println(attr + ": " + session.getAttribute(attr)));
//
//        // ✅ If no email in session, redirect to login
//        if (mail == null) {
//            return "redirect:/login";
//        }
//
//        User user = userService.getUser(mail);
//
//        // ✅ If user not found in database, redirect to login
//        if (user == null) {
//            return "redirect:/login";
//        }
//
//        // ✅ Set user as premium and update DB
//        user.setPremium(true);
//        userService.updateUser(user);
//
//        // ✅ Also update session attribute
//        session.setAttribute("isPremium", true);
//        model.addAttribute("isPremium", true);
//
//        return "Customer"; // ✅ User should now see premium content
//    }
//
//    @GetMapping("/paymentFailed")
//    public String paymentFailed() {
//        return "Customer";
//    }
//
//}