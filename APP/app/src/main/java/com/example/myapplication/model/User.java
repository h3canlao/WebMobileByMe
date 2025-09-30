package com.example.myapplication.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String role; // ADMIN, DEALER_MANAGER, DEALER_STAFF, EVN_STAFF
    private String status; // ACTIVE, INACTIVE, PENDING_APPROVAL, SUSPENDED
    private String dealerId; // null for ADMIN and EVN_STAFF
    private String createdAt;
    private String lastLoginAt;
    private String password; // In real app, this should be hashed
    private String address;
    private String department;
    private String position;
    private boolean isApproved;
    private String approvedBy;
    private String approvedAt;
}