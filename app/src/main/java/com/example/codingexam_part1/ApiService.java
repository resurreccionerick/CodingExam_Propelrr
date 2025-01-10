package com.example.codingexam_part1;

import com.example.codingexam_part1.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("v3/ece7e69a-4cf0-4fe6-ae4b-7ef9d1401b01")
        //this is the endpoint
    Call<ApiResponse> submitForm(@Body FormData formData);
}
