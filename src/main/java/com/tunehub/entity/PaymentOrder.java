package com.tunehub.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "payment_order")
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // your internal id/receipt to ensure idempotency
    @Column(unique = true, nullable = false)
    private String receiptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // razorpay order id (order_xxx)
    @Column(unique = true)
    private String razorpayOrderId;

    private Integer amount; // in paise
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public PaymentOrder(String receiptId, User user, String razorpayOrderId, Integer amount, String currency, PaymentStatus paymentStatus, Instant createdAt, Instant updatedAt) {
        this.receiptId = receiptId;
        this.user = user;
        this.razorpayOrderId = razorpayOrderId;
        this.amount = amount;
        this.currency = currency;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PaymentOrder() {
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public Long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "PaymentOrder{" +
                "id=" + id +
                ", receiptId='" + receiptId + '\'' +
                ", razorpayOrderId='" + razorpayOrderId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

}