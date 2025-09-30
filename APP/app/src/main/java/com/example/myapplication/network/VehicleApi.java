package com.example.myapplication.network;

import com.example.myapplication.model.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VehicleApi {
    
    @GET("v1/vehicles")
    Call<List<Vehicle>> getVehicles(
        @Query("brand") String brand,
        @Query("color") String color,
        @Query("minPrice") Double minPrice,
        @Query("maxPrice") Double maxPrice,
        @Query("dealerId") String dealerId
    );
    
    @GET("v1/vehicles/{id}")
    Call<Vehicle> getVehicleById(@Path("id") String id);
    
    @GET("v1/vehicles/available")
    Call<List<Vehicle>> getAvailableVehicles(@Query("dealerId") String dealerId);
}
