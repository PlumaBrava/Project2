package com.perezjuanjose.project2.retrofit;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

//      http://api.themoviedb.org/3/movie/76341/reviews?api_key=79424eca98daa0b906a464bf7d8f9f0f
public interface GetReviewApi {
    @GET("3/movie/{id}/reviews")
    Call<ReviewData> getReviewFromApi(
            @Path("id") int filmId,
            @Query("api_key") String api_key);

}
