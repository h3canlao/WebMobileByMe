package com.example.myapplication.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String source; // ONLINE, REFERRAL, WALK_IN
    private String preferredVehicleType;
    private String status; // POTENTIAL, PURCHASED, LOST
    private String dealerId;
    private String assignedStaffId;
    private String notes;
    private String createdAt;
}
