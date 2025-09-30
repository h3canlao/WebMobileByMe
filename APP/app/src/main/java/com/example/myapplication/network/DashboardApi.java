package com.example.myapplication.network;

import com.example.myapplication.model.response.DashboardResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DashboardApi {
    
    @GET("v1/dashboard/stats")
    Call<DashboardResponse> getDashboardStats(
        @Query("role") String role,
        @Query("dealerId") String dealerId,
        @Query("staffId") String staffId
    );
}
