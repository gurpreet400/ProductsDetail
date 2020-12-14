package com.example.productsdetails;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("add_product")
    Call<String> Add(@Field("c_date") String date, @Field("c_time") String time, @Field("name") String name, @Field("price") String price, @Field("description") String description);

}
