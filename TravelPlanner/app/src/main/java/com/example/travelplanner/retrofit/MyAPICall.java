package com.example.travelplanner.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPICall {

    @GET("getjoke")
    Call<List<DataModel>> getData();
}
