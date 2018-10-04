package com.ibra.moviecatalog.api;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface MovieInterface {


    @GET("movie/upcoming")
    Call<com.ibra.moviecatalog.model.Movie> getUpcomingMovie(@Query("api_key") String apiKey);


}