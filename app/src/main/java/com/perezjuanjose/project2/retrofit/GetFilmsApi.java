package com.perezjuanjose.project2.retrofit;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;

import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */


//https://api.themoviedb.org/3/discover/movie?api_key=79424eca98daa0b906a464bf7d8f9f0f&sort_by=popularity.asc

    public interface  GetFilmsApi {

    @GET("3/discover/movie")
    Call<FilmData> getFilmsFromApi(
            @QueryMap Map<String, String> options);
}



