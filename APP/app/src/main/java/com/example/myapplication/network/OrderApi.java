package com.example.myapplication.network;

import com.example.myapplication.model.Order;
import com.example.myapplication.model.request.CreateOrderRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderApi {
    
    @GET("v1/orders")
    Call<List<Order>> getOrders(
        @Query("status") String status,
        @Query("dealerId") String dealerId,
        @Query("staffId") String staffId
    );
    
    @GET("v1/orders/{id}")
    Call<Order> getOrderById(@Path("id") String id);
    
    @POST("v1/orders")
    Call<Order> createOrder(@Body CreateOrderRequest request);
    
    @PUT("v1/orders/{id}/approve")
    Call<Order> approveOrder(@Path("id") String id);
    
    @PUT("v1/orders/{id}/reject")
    Call<Order> rejectOrder(@Path("id") String id);
}
