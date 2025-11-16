package com.io.tunehub.repository;

import com.io.tunehub.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    // Used for idempotency
    Optional<PaymentOrder> findByReceiptId(String receiptId);

    // Used during verifyPayment() + webhook
    Optional<PaymentOrder> findByRazorpayOrderId(String razorpayOrderId);
}
