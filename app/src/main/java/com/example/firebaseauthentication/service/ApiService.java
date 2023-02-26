package com.example.firebaseauthentication.service;

import com.example.firebaseauthentication.datamodel.ItemListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("products?")
    Call<ArrayList<ItemListModel>> getItems(@Query("limit") Integer limit);
}
