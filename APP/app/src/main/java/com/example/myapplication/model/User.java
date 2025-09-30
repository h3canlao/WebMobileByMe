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
    private String role; // ADMIN, EVN_STAFF, DEALER_MANAGER, DEALER_STAFF
    private String dealerId; // null for ADMIN and EVN_STAFF
    private String avatar;
    private boolean isActive;
    private String createdAt;
    private String lastLoginAt;
}
