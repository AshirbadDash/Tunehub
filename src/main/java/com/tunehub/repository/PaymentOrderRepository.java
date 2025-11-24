package com.tunehub.repository;

import com.tunehub.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    // Used for idempotency
    Optional<PaymentOrder> findByReceiptId(String receiptId);

    // Used during verifyPayment() + webhook
    Optional<PaymentOrder> findByRazorpayOrderId(String razorpayOrderId);
}
