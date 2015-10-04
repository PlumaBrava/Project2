package com.perezjuanjose.project2.Services;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import com.perezjuanjose.project2.data.FilmCursosAdapter;
import com.perezjuanjose.project2.data.FilmsProvider;
import com.perezjuanjose.project2.data.TrailerColumns;
import com.perezjuanjose.project2.retrofit.Films;
import com.perezjuanjose.project2.retrofit.GetFilmsApi;
import com.perezjuanjose.project2.retrofit.GetMoviesApi;
import com.perezjuanjose.project2.retrofit.GetReviewApi;
import com.perezjuanjose.project2.retrofit.VideoData;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static android.database.DatabaseUtils.dumpCursorToString;

/**
 * Created by perez.juan.jose on 04/10/2015.
 */
public class TrailerServices extends IntentService {
    private static final String LOG_TAG = TrailerServices.class.getSimpleName();
    static final String THE_MOVIEDB_URL="http://api.themoviedb.org/";
    public static final String TRILER_QUERY_EXTRA = "Tqe";
    protected GetMoviesApi apiService;
    protected GetFilmsApi apiService1;
    protected GetReviewApi apiService2;
    private FilmCursosAdapter mFilmAdapter;
    static  private String ORDER_BY = "order_by";//Preference to fetch the movie information in the web POPULARITY, VOTE AVERAGE, VOTE COUNT
    private ArrayList<ContentProviderOperation> batchOperations1;
    ContentResolver resolver;


    public TrailerServices() {
        super("TraileServices");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String filmQuery = intent.getStringExtra(TRILER_QUERY_EXTRA);
        insertTrailer(135397);
        insertTrailer(76341);
        insertTrailer(87101);

    }

    public void insertTrailer(int moviId) {


/// Trailers
        resolver = this.getContentResolver();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(THE_MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(GetMoviesApi.class); // create the interface
        Log.d(LOG_TAG, "GetWeatherRestAdapter -- created");

        // aSynchronous Call in Retrofit 2.0

        Call<VideoData> call = apiService.getMoviesFromApi(moviId, VideoData.API_KEY);
        Log.d(LOG_TAG, "se llama a la web: " + call.getClass().getFields().toString());
        batchOperations1 = new ArrayList<>();
        call.enqueue(new Callback<VideoData>() {
            @Override
            public void onResponse(Response<VideoData> response) {
                // Get result Repo from response.body()
                Log.d(LOG_TAG, "Llegaron los datos %d" + response.code());
// Hacer algo con Response cod para evitarr errores, ejemplo sin datos..distinto de 202...
                //VideoData data = response.body();

                // Declaramos el Iterador e imprimimos los Elementos del ArrayList
                VideoData videos = response.body();
                Iterator<Films> video = videos.getResults().iterator();
               // ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(videos.getResults().size());

                while (video.hasNext()) {

                    Films elemento = video.next();


                    ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                            FilmsProvider.Trailes.CONTENT_URI);
                    builder.withValue(TrailerColumns.REF_ID_MOVIE, videos.getId());
                    builder.withValue(TrailerColumns.ID_TRILER_DB, elemento.getId());
                    builder.withValue(TrailerColumns.ISO6391, elemento.getIso6391());
                    builder.withValue(TrailerColumns.KEY, elemento.getKey());
                    builder.withValue(TrailerColumns.NAME, elemento.getName());
                    builder.withValue(TrailerColumns.SICE, elemento.getSize());
                    builder.withValue(TrailerColumns.TYPE, elemento.getType());
                    batchOperations1.add(builder.build());
                    Log.d(LOG_TAG, "Llegaron los datos");
                    Log.d(LOG_TAG, "Llegaron los datos" + elemento.getKey() + "|" + elemento.getName());


//
                }

                try {
                    resolver.applyBatch(FilmsProvider.AUTHORITY, batchOperations1);
                    Cursor c = resolver.query(FilmsProvider.Trailes.CONTENT_URI,
                            null, null, null, null);

                    Log.d(LOG_TAG, "Lectura del cursor cantidadi" + c.getCount());
                    Log.d(LOG_TAG, "Lectura del cursos cantidadi" + dumpCursorToString(c));

                    Cursor c1 = resolver.query(FilmsProvider.Trailes.CONTENT_URI,
                            null, null, null, null);
                    Log.d(LOG_TAG, "Lectura del cursos trailers" + dumpCursorToString(c1));



                } catch (RemoteException | OperationApplicationException e) {
                    Log.e(LOG_TAG, "Error applying batch insert", e);


                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "Falla: " + t.toString());
            }
        });





    }



}
