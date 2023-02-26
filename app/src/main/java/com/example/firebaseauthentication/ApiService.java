package com.example.firebaseauthentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("products?")
    Call<ArrayList<ItemListModel>> getItems(@Query("limit") Integer limit);
}
