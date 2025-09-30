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
    private String customerName; // Added for display
    private String vehicleId;
    private String vehicleName; // Added for display
    private String dealerId;
    private String staffId;
    private Date orderDate;
    private Date deliveryDate;
    private int quantity; // Added quantity field
    private double unitPrice; // Added unit price
    private double totalAmount;
    private String status; // PENDING, APPROVED, COMPLETED, CANCELLED, IN_PROGRESS
    private String paymentStatus; // PENDING, PAID, PARTIAL
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
