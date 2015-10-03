package com.perezjuanjose.project2.retrofit;

import android.util.Log;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by JJ_PEREZ on 23/09/2015.
 */
public class GetVideosRestAdapter {
//
//    protected final String TAG = getClass().getSimpleName();
//    protected Retrofit mRestAdapter;
//    protected GetMoviesApi apiService;
//    static final String THE_MOVIEDB_URL="http://api.themoviedb.org";
//    //   http://api.themoviedb.org/3/movie/232/videos?api_key=79424eca98daa0b906a464bf7d8f9f0f
//    public GetVideosRestAdapter() {
//
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(THE_MOVIEDB_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        apiService = retrofit.create(GetMoviesApi.class); // create the interface
//        Log.d(TAG, "GetWeatherRestAdapter -- created");
//
//        // Synchronous Call in Retrofit 2.0
//
//        Call<VideoData> call = apiService.getMoviesFromApi(232,VideoData.API_KEY);
//            call.enqueue(new Callback<VideoData>() {
//            @Override
//            public void onResponse(Response<VideoData> response) {
//                // Get result Repo from response.body()
//                Log.d(TAG, "Llegaron los datos");
//                Log.d(TAG, "Llegaron los datos");
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.d(TAG, "Falla: "+t.toString());
//            }
//        });
//
//
//    }
//
//    public void testMoviesApi(Integer filmId, String key_id){
//        Log.d(TAG, "testMovirApi: for MOvi: " + filmId);
//        apiService.getMoviesFromApi(filmId, key_id);
//    }
//

}



