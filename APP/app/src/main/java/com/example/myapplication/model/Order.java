package com.example.myapplication.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String orderNumber;
    private String customerId;
    private String vehicleId;
    private String dealerId;
    private String staffId;
    private Date orderDate;
    private Date deliveryDate;
    private double totalAmount;
    private String status; // PENDING, APPROVED, COMPLETED, CANCELLED
    private String notes;
    private String paymentMethod; // FULL_PAYMENT, INSTALLMENT
    private double paidAmount;
    private String selectedColor;
    private String configuration;
    private String discount;
    private String createdAt;
    private String approvedAt;
    private String deliveredAt;
    private String approvedBy;
}
