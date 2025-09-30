package com.example.myapplication.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDrive {
    private String id;
    private String customerId;
    private String customerName; // Added for display
    private String vehicleId;
    private String vehicleName; // Added for display
    private String dealerId;
    private String staffId;
    private Date scheduledDate; // Changed from String to Date
    private String scheduledTime;
    private String status; // SCHEDULED, COMPLETED, CANCELLED, NO_SHOW, IN_PROGRESS
    private int rating; // 1-5 stars
    private String feedback;
    private int purchaseProbability; // 0-100%
    private String notes;
    private String createdAt;
    private String completedAt;
}
