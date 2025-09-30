package com.example.myapplication.model.request;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestDriveRequest {
    private String customerId;
    private String vehicleId;
    private String scheduledDate;
    private String scheduledTime;
    private String notes;
}
