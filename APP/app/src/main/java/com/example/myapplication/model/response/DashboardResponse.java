package com.example.myapplication.model.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private int totalOrders;
    private int pendingOrders;
    private int completedOrders;
    private double totalRevenue;
    private int totalCustomers;
    private int totalVehicles;
    private int todayTestDrives;
    private int upcomingTestDrives;
}
