package com.perezjuanjose.project2.Services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import com.perezjuanjose.project2.data.FilmCursosAdapter;
import com.perezjuanjose.project2.data.FilmsColumns;
import com.perezjuanjose.project2.data.FilmsProvider;
import com.perezjuanjose.project2.data.ReviewColumns;
import com.perezjuanjose.project2.data.TrailerColumns;
import com.perezjuanjose.project2.retrofit.GetFilmsApi;
import com.perezjuanjose.project2.retrofit.GetMoviesApi;
import com.perezjuanjose.project2.retrofit.GetReviewApi;
import com.perezjuanjose.project2.retrofit.ReviewData;
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
public class ReviewServices extends IntentService {

    private static final String LOG_TAG = ReviewServices.class.getSimpleName();
    static final String THE_MOVIEDB_URL="http://api.themoviedb.org/";
    public static final String REVIER_QUERY_EXTRA = "Tqe";
    protected GetMoviesApi apiService;
    protected GetFilmsApi apiService1;
    protected GetReviewApi apiService2;
    private FilmCursosAdapter mFilmAdapter;
    static  private String ORDER_BY = "order_by";//Preference to fetch the movie information in the web POPULARITY, VOTE AVERAGE, VOTE COUNT
    private ArrayList<ContentProviderOperation> batchOperations1;
    ContentResolver resolver;


    public ReviewServices() {
        super("ReviewServices");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int idMovi = intent.getIntExtra(REVIER_QUERY_EXTRA,20);

                insertReview(idMovi);






//        insertReview(135397);
//        insertReview(76341);
//        insertReview(87101);


    }


    public void insertReview(int moviId){
        ///// review

        resolver = this.getContentResolver();


        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(THE_MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService2 = retrofit2.create(GetReviewApi.class); // create the interface
        Log.d(LOG_TAG, "reviw -- created");

        // aSynchronous Call in Retrofit 2.0

        // aSynchronous Call in Retrofit 2.0



        Call<ReviewData> call2 = apiService2.getReviewFromApi(moviId, VideoData.API_KEY);
        Log.d(LOG_TAG, "se llama a la web x reviews: " + call2.getClass().getFields().toString());

        batchOperations1 = new ArrayList<>();
        call2.enqueue(new Callback<ReviewData>() {
            @Override
            public void onResponse(Response<ReviewData> response) {
                // Get result Repo from response.body()
                Log.d(LOG_TAG, "Llegaron los datos comentarios %d" + response.code());

                //VideoData data = response.body();

                // Declaramos el Iterador e imprimimos los Elementos del ArrayList

                ReviewData videos1 = response.body();
             //   Log.d(LOG_TAG, "Llegaron los datos Films>:ID %d" + videos1.getId()+ "|totalresultaos:%d" + videos1.getTotalResults());
                Iterator<ReviewData.Result> videoa = videos1.getResults().iterator();
                while (videoa.hasNext()) {

                    ReviewData.Result elemento1 = videoa.next();


                    ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                            FilmsProvider.Reviews.CONTENT_URI);
                    builder.withValue(ReviewColumns.REF_ID_MOVIE, videos1.getId());
                    builder.withValue(ReviewColumns.AUTOR, elemento1.getAuthor());
                    builder.withValue(ReviewColumns.COMENT, elemento1.getContent());
                    builder.withValue(ReviewColumns.URL, elemento1.getUrl());
                    batchOperations1.add(builder.build());
                    Log.d(LOG_TAG, "Llegaron los datos Review>: " + elemento1.getAuthor() +"|" + elemento1.getContent() );

                }

                try {
                    resolver.applyBatch(FilmsProvider.AUTHORITY, batchOperations1);
                    Cursor c = resolver.query(FilmsProvider.Reviews.CONTENT_URI,
                            null, null, null, null);

                    Log.d(LOG_TAG, "Lectura del cursor cantidadi" + c.getCount());
                    Log.d(LOG_TAG, "Lectura del cursos cantidadi" + dumpCursorToString(c).toString());




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
