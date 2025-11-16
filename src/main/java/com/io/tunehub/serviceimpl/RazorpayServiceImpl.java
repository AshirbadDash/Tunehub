package com.io.tunehub.serviceimpl;

import com.io.tunehub.entity.PaymentOrder;
import com.io.tunehub.entity.PaymentStatus;
import com.io.tunehub.repository.PaymentOrderRepository;
import com.io.tunehub.service.RazorpayService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RazorpayServiceImpl implements RazorpayService {
    private final RazorpayClient razorpayClient;

    private final PaymentOrderRepository paymentOrderRepository;

    public RazorpayServiceImpl(@Value("${razorpay.key}") String key, @Value("${razorpay.secret}") String secret, PaymentOrderRepository paymentOrderRepository) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(key, secret);
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @Override
    public JSONObject createOrderIfAbsent(String receiptId, int amountInPaise) throws Exception {
        Optional<PaymentOrder> existing = paymentOrderRepository.findByReceiptId(receiptId);
        if (existing.isPresent()) {
            PaymentOrder po = existing.get();
            if (po.getRazorpayOrderId() != null) {
                // return minimal JSON compatible with frontend expectation
                JSONObject res = new JSONObject();
                res.put("id", po.getRazorpayOrderId());
                res.put("amount", po.getAmount());
                res.put("amount_due", po.getAmount());
                res.put("currency", po.getCurrency());
                return res;
            }
        }
        PaymentOrder po = new PaymentOrder();
        po.setReceiptId(receiptId);
        po.setAmount(amountInPaise);
        po.setCurrency("INR");
        po.setPaymentStatus(PaymentStatus.PENDING);
        po = paymentOrderRepository.save(po);

        return null;
    }

    @Override
    public void markPaid(String razorpayOrderId) {

    }

    @Override
    public void markFailed(String razorpayOrderId) {

    }
}
