package com.example.myapplication.repository;

import com.example.myapplication.model.Vehicle;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.network.VehicleApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleRepository {
    
    private final VehicleApi vehicleApi;
    
    public VehicleRepository() {
        vehicleApi = RetrofitClient.create(VehicleApi.class);
    }
    
    public void getVehicles(String brand, String color, Double minPrice, Double maxPrice, 
                           String dealerId, VehicleListCallback callback) {
        vehicleApi.getVehicles(brand, color, minPrice, maxPrice, dealerId)
            .enqueue(new Callback<List<Vehicle>>() {
                @Override
                public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Failed to load vehicles: " + response.code());
                    }
                }
                
                @Override
                public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                    callback.onError("Network error: " + t.getMessage());
                }
            });
    }
    
    public void getVehicleById(String id, VehicleCallback callback) {
        vehicleApi.getVehicleById(id).enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load vehicle: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    public void getAvailableVehicles(String dealerId, VehicleListCallback callback) {
        vehicleApi.getAvailableVehicles(dealerId).enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load available vehicles: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    public interface VehicleListCallback {
        void onSuccess(List<Vehicle> vehicles);
        void onError(String error);
    }
    
    public interface VehicleCallback {
        void onSuccess(Vehicle vehicle);
        void onError(String error);
    }
}
