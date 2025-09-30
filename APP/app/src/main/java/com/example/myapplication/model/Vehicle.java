package com.example.myapplication.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String id;
    private String name;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String color;
    private String configuration; // Added configuration field
    private int batteryCapacity; // kWh
    private int range; // km
    private String imageUrl;
    private String description;
    private String status; // AVAILABLE, SOLD, MAINTENANCE
    private String dealerId;
    private int stockQuantity;
}
