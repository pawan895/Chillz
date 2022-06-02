package com.example.chillz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @POST("3/movie/now_playing?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US&page=undefined")
    Call<MoviesList> getMoviesList();


    @GET("3/movie/{id}?api_key=55957fcf3ba81b137f8fc01ac5a31fb5")
    Call<DataOnMovie> getDataOnMovie(@Path("id") int movieId);



}
