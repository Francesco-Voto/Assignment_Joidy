package com.assignment.joidy.data.network;


import com.assignment.joidy.data.models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIMain {

    @GET("api/v1.0/product/category/recommended")
    Call<ArrayList<Product>> getRecommendedProducts();
}
