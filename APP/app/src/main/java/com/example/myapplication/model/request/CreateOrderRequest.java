package com.example.myapplication.model.request;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private String customerId;
    private String vehicleId;
    private String paymentMethod;
    private String selectedColor;
    private String configuration;
    private String discount;
    private String notes;
}
