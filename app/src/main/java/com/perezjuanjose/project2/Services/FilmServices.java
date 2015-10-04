package com.perezjuanjose.project2.Services;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.perezjuanjose.project2.Film;
import com.perezjuanjose.project2.data.FilmCursosAdapter;
import com.perezjuanjose.project2.data.FilmsColumns;
import com.perezjuanjose.project2.data.FilmsProvider;
import com.perezjuanjose.project2.data.TrailerColumns;
import com.perezjuanjose.project2.retrofit.FilmData;
import com.perezjuanjose.project2.retrofit.GetFilmsApi;
import com.perezjuanjose.project2.retrofit.GetMoviesApi;
import com.perezjuanjose.project2.retrofit.GetReviewApi;
import com.perezjuanjose.project2.retrofit.VideoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static android.database.DatabaseUtils.dumpCursorToString;

/**
 * Created by perez.juan.jose on 04/10/2015.
 */
public class FilmServices extends IntentService {
    private static final String LOG_TAG = FilmServices.class.getSimpleName();
    static final String THE_MOVIEDB_URL="http://api.themoviedb.org/";
    public static final String FILM_QUERY_EXTRA = "fqe";
    protected GetMoviesApi apiService;
    protected GetFilmsApi apiService1;
    protected GetReviewApi apiService2;
    private FilmCursosAdapter mFilmAdapter;
    static  private String ORDER_BY = "order_by";//Preference to fetch the movie information in the web POPULARITY, VOTE AVERAGE, VOTE COUNT
    private   ArrayList<ContentProviderOperation> batchOperations1;
    ContentResolver resolver;
    public FilmServices() {
        super("FilmServices");
    }

    public FilmServices(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy(){
//        Intent intent1 = new Intent(this, TrailerServices.class);
//        intent1.putExtra(TrailerServices.TRILER_QUERY_EXTRA, "SomeDAta");
//        this.startService(intent1);
//        Log.i(LOG_TAG, "Se llamo TrailerService");
//
//        Intent intent2 = new Intent(this, ReviewServices.class);
//        intent2.putExtra(ReviewServices.REVIER_QUERY_EXTRA, "SomeDAta");
//        this.startService(intent2);
//        Log.i(LOG_TAG, "Se llamo REVIEWService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String filmQuery = intent.getStringExtra(FILM_QUERY_EXTRA);
        insertData();



          }


    public void insertTrailersAndRevies(){

        Cursor c = this.getContentResolver().query(FilmsProvider.Films.CONTENT_URI,
                null, null, null, null);


        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                Intent intent1 = new Intent(this, TrailerServices.class);
        intent1.putExtra(TrailerServices.TRILER_QUERY_EXTRA, c.getColumnIndex(FilmsColumns.ID_MOVI_DB));
        this.startService(intent1);
        Log.i(LOG_TAG, "Se llamo TrailerService");

        Intent intent2 = new Intent(this, ReviewServices.class);
        intent2.putExtra(ReviewServices.REVIER_QUERY_EXTRA, c.getColumnIndex(FilmsColumns.ID_MOVI_DB));
        this.startService(intent2);
        Log.i(LOG_TAG, "Se llamo REVIEWService");

            } while (c.moveToNext());
        }
    }




    public void insertData() {
        Log.d(LOG_TAG, "insert");



        //  Cursor c = getActivity().getContentResolver().query(FilmsProvider.Films.CONTENT_URI, null, null, null, null);

        // if (c != null || c.getCount()<> 0){
        Log.i(LOG_TAG, "Se llama a delete");
      //  this.getContentResolver().delete(FilmsProvider.Films.CONTENT_URI,
       //         null, null);

        // }

        ///// Filmas

        resolver = this.getContentResolver();

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(THE_MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService1 = retrofit1.create(GetFilmsApi.class); // create the interface
        Log.d(LOG_TAG, "GeFilmsAp -- created");

        // aSynchronous Call in Retrofit 2.0

        // aSynchronous Call in Retrofit 2.0


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String order_by=prefs.getString(ORDER_BY, "popularity.");



        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", VideoData.API_KEY);
        params.put("sort_by", order_by + "desc");

        Call<FilmData> call1 = apiService1.getFilmsFromApi(params);
        Log.d(LOG_TAG, "se llama a la web: " + call1.getClass().getFields().toString());

        //ArrayList<ContentProviderOperation> batchOperations1 = new ArrayList<>();
       batchOperations1 = new ArrayList<>();
        call1.enqueue(new Callback<FilmData>() {
            @Override
            public void onResponse(Response<FilmData> response) {
                // Get result Repo from response.body()
                Log.d(LOG_TAG, "Llegaron los datos de Films %d" + response.code());

                //VideoData data = response.body();

                // Declaramos el Iterador e imprimimos los Elementos del ArrayList

                FilmData videos1 = response.body();
                Log.d(LOG_TAG, "Llegaron los datos Films>: Total Pag %d" + videos1.getTotal_pages()+ "|totalresultaos:%d" + videos1.getTotal_results());
                Iterator<FilmData.Result> videoa = videos1.getResults().iterator();

                while (videoa.hasNext()) {

                    FilmData.Result elemento1 = videoa.next();

                    if (existeFilmenInDB(elemento1.getId())) {
                        //up date else
                    }
                    else {


                        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(FilmsProvider.Films.CONTENT_URI);
                        builder.withValue(FilmsColumns.ADULT, elemento1.isAdult());
                        builder.withValue(FilmsColumns.BACKDROP_PATH, elemento1.getBackdrop_path());
                        builder.withValue(FilmsColumns.ID_MOVI_DB, elemento1.getId());
                        builder.withValue(FilmsColumns.ORIGINAL_TITLE, elemento1.getOriginal_title());
                        builder.withValue(FilmsColumns.ORIGINAL_LANGUAGE, elemento1.getOriginal_language());
                        builder.withValue(FilmsColumns.OVERVIEW, elemento1.getOverview());
                        builder.withValue(FilmsColumns.RELEASSE_DATE, elemento1.getRelease_date());
                        builder.withValue(FilmsColumns.POSTER_PATH, elemento1.getPoster_path());
                        builder.withValue(FilmsColumns.POPULARITY, elemento1.getPopularity());
                        builder.withValue(FilmsColumns.TITLE, elemento1.getTitle());
                        builder.withValue(FilmsColumns.VIDEO, elemento1.isVideo());
                        builder.withValue(FilmsColumns.VOTE_AVERAGE, elemento1.getVote_average());
                        builder.withValue(FilmsColumns.VOTE_COUNT, elemento1.getVote_count());
//                        builder.withValue(FilmsColumns.MOST_POPULAR, mMostPolular);
//                        builder.withValue(FilmsColumns.HIGHEST_RATED, mHightestrated);
//                        builder.withValue(FilmsColumns.FAVORITE, mFavorite);

                        batchOperations1.add(builder.build());
                        Log.d(LOG_TAG, "Llegaron los datos Films>: " + elemento1.getOriginal_title() + "|" + elemento1.getTitle());
                        Log.d(LOG_TAG, "Llegaron los datos relise date>: " + elemento1.getRelease_date() + "| Poster Path" + elemento1.getPoster_path());
                        Log.d(LOG_TAG, "Llegaron los datos Back?drop>: " + elemento1.getBackdrop_path());


                    }

                }

                try{
                    resolver.applyBatch(FilmsProvider.AUTHORITY, batchOperations1);
                    Cursor c = resolver.query(FilmsProvider.Films.CONTENT_URI,
                            null, null, null, null);

                    Log.d(LOG_TAG, "Lectura del cursor de films " + c.getCount());
                    Log.d(LOG_TAG, "Lectura del cursor Fillms" + dumpCursorToString(c));
                  //  insertTrailersAndRevies();


                } catch(RemoteException | OperationApplicationException e){
                    Log.e(LOG_TAG, "Error applying batch insert", e);


                }



            }
            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "Falla: " + t.toString());
            }
        });



    }
    public boolean existeFilmenInDB(int idFilm){
        Cursor c = this.getContentResolver().query(FilmsProvider.Films.withId(idFilm),
                null, null, null, null);
        if (c.getCount()>0) return true;
        else return false;
    }

}
