package com.io.tunehub.service;

import org.json.JSONObject;

public interface RazorpayService {
    JSONObject createOrderIfAbsent(String receiptId, int amountInPaise) throws Exception;

    void markPaid(String razorpayOrderId);

    void markFailed(String razorpayOrderId);

}
