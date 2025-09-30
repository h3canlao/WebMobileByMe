package com.example.myapplication.repository;

import com.example.myapplication.model.response.DashboardResponse;
import com.example.myapplication.network.DashboardApi;
import com.example.myapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {
    
    private final DashboardApi dashboardApi;
    
    public DashboardRepository() {
        dashboardApi = RetrofitClient.create(DashboardApi.class);
    }
    
    public void getDashboardStats(String role, String dealerId, String staffId, DashboardCallback callback) {
        dashboardApi.getDashboardStats(role, dealerId, staffId).enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load dashboard: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    public interface DashboardCallback {
        void onSuccess(DashboardResponse dashboard);
        void onError(String error);
    }
}
