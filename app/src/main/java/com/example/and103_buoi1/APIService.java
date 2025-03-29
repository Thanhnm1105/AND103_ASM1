package com.example.and103_buoi1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    String DOMAIN = "http://10.0.2.2:3000";
    // Dành cho giả lập

    @GET("/api/list")
    Call<List<CarModel>> getCars();


    @POST("/api/add_xe")
    Call<List<CarModel>> addCar(@Body CarModel xe);

    @PUT("/api/update/{id}")
    Call<List<CarModel>> updateCar(@Path("id") String id, @Body CarModel car);


    @GET("/api/xoa/{id}")
    Call<Void> xoaXe(@Path("id")String id);

}