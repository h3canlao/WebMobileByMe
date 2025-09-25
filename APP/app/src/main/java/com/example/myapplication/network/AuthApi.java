package com.example.myapplication.network;



import com.example.myapplication.model.request.LoginRequest;
import com.example.myapplication.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("v1/auth/login")  // tương ứng với backend @PostMapping("/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}

