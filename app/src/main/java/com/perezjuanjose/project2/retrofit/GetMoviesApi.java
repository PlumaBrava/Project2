package com.perezjuanjose.project2.retrofit;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by JJ_PEREZ on 23/09/2015.
 */

//http://api.themoviedb.org/3/movie/232/videos?api_key=79424eca98daa0b906a464bf7d8f9f0f
public interface  GetMoviesApi {


    @GET("3/movie/{id}/videos")
    Call<VideoData> getMoviesFromApi(
            @Path("id") int filmId,
            @Query("api_key") String api_key);

}
