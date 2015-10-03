package com.perezjuanjose.project2;


import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.perezjuanjose.project2.data.FilmCursosAdapter;
import com.perezjuanjose.project2.data.FilmsColumns;
import com.perezjuanjose.project2.data.FilmsProvider;
import com.perezjuanjose.project2.data.TrailerColumns;
import com.perezjuanjose.project2.retrofit.FilmData;
import com.perezjuanjose.project2.retrofit.Films;
import com.perezjuanjose.project2.retrofit.GetFilmsApi;
import com.perezjuanjose.project2.retrofit.GetMoviesApi;
import com.perezjuanjose.project2.retrofit.GetReviewApi;
import com.perezjuanjose.project2.retrofit.ReviewData;
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
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private static final int CURSOR_LOADER_ID = 0;
    static final String THE_MOVIEDB_URL="http://api.themoviedb.org/";
    protected GetMoviesApi apiService;
    protected GetFilmsApi apiService1;
    protected GetReviewApi apiService2;
    private FilmCursosAdapter mFilmAdapter;
    static  private String ORDER_BY = "order_by";//Preference to fetch the movie information in the web POPULARITY, VOTE AVERAGE, VOTE COUNT
    static  private String ASC_DESC = "asc_desc";//Preference to fetch the movie information in the web aSCENDENTE OR DESCENDENTE

    private int mPosition = ListView.INVALID_POSITION;

    public static boolean preferenceHasChanged = false;

    public interface CallbackFrame {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    public MainActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart(){

        Log.i("Prererencias", "On Start");
        Log.i("Prererencias", "mthis" + preferenceHasChanged);
        if(preferenceHasChanged) {

            updateData();
            preferenceHasChanged=false;

            //updateData();
        }
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order_by=prefs.getString(ORDER_BY, "popularity.");
        String asc_desc = prefs.getString(ASC_DESC, "desc");

        Log.i("Prererencias", "order_by:" + order_by + "asc_desc: " + asc_desc);

        Cursor c = getActivity().getContentResolver().query(FilmsProvider.Films.CONTENT_URI,
                null, null, null, null);

        if (c == null || c.getCount() == 0){
            Log.i(LOG_TAG, "Se llama a insertData");
            insertData();
        }
        Log.i(LOG_TAG, "cursor count: %d" + c.getCount());

        mFilmAdapter = new FilmCursosAdapter(getActivity(), c, 0);

        View rootView = inflater.inflate(R.layout.moviefragment, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView listView = (GridView) rootView.findViewById(R.id.movies_list);
        listView.setAdapter(mFilmAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    //       String locationSetting = Utility.getPreferredLocation(getActivity());
                    long _id = cursor.getLong(cursor.getColumnIndex(FilmsColumns._ID));

                    ((CallbackFrame) getActivity())
                            .onItemSelected(FilmsProvider.Films.withId(_id));

                }


                mPosition = position;
            }
        });



        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), FilmsProvider.Films.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFilmAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFilmAdapter.swapCursor(null);
    }

    public void insertData() {
        Log.d(LOG_TAG, "insert");




/// Trailers

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(THE_MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(GetMoviesApi.class); // create the interface
        Log.d(LOG_TAG, "GetWeatherRestAdapter -- created");

        // aSynchronous Call in Retrofit 2.0

        Call<VideoData> call = apiService.getMoviesFromApi(232, VideoData.API_KEY);
        Log.d(LOG_TAG, "se llama a la web: " + call.getClass().getFields().toString());
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
                ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(videos.getResults().size());

                while (video.hasNext()) {

                    Films elemento = video.next();



                    ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                            FilmsProvider.Trailes.CONTENT_URI);
                    builder.withValue(TrailerColumns.REF_ID_MOVIE, 232);
                    builder.withValue(TrailerColumns.ID_TRILER_DB, elemento.getId());
                    builder.withValue(TrailerColumns.ISO6391, elemento.getIso6391());
                    builder.withValue(TrailerColumns.KEY,elemento.getKey());
                    builder.withValue(TrailerColumns.NAME,elemento.getName());
                    builder.withValue(TrailerColumns.SICE,elemento.getSize());
                    builder.withValue(TrailerColumns.TYPE, elemento.getType());
                    batchOperations.add(builder.build());
                    Log.d(LOG_TAG, "Llegaron los datos");
                    Log.d(LOG_TAG, "Llegaron los datos" + elemento.getKey() + "|" + elemento.getName());




//
                }

                try{
                    getActivity().getContentResolver().applyBatch(FilmsProvider.AUTHORITY, batchOperations);
                    Cursor c = getActivity().getContentResolver().query(FilmsProvider.Trailes.CONTENT_URI,
                            null, null, null, null);

                    Log.d(LOG_TAG, "Lectura del cursor cantidadi" + c.getCount());
                    Log.d(LOG_TAG, "Lectura del cursos cantidadi" + dumpCursorToString(c));

                    for(int i=1;i<c.getCount();i++) {

                        getActivity().getContentResolver().delete(
                                FilmsProvider.Trailes.withId(i), null, null);

                    }

                    Cursor c1 = getActivity().getContentResolver().query(FilmsProvider.Trailes.CONTENT_URI,
                            null, null, null, null);
                    Log.d(LOG_TAG, "Lectura del cursos cantidadi con delete" + dumpCursorToString(c1));

                } catch(RemoteException | OperationApplicationException e){
                    Log.e(LOG_TAG, "Error applying batch insert", e);



                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "Falla: " + t.toString());
            }
        });



        ///// Filmas

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(THE_MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService1 = retrofit1.create(GetFilmsApi.class); // create the interface
        Log.d(LOG_TAG, "GeFilmsAp -- created");

        // aSynchronous Call in Retrofit 2.0

        // aSynchronous Call in Retrofit 2.0



        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", VideoData.API_KEY);
        params.put("sort_by", "vote_average.desc");

        Call<FilmData> call1 = apiService1.getFilmsFromApi(params);
        Log.d(LOG_TAG, "se llama a la web: " + call.getClass().getFields().toString());


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
                ArrayList<ContentProviderOperation> batchOperations1 = new ArrayList<>(videos1.getResults().size());
                while (videoa.hasNext()) {

                    FilmData.Result elemento1 = videoa.next();




                    ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                            FilmsProvider.Films.CONTENT_URI);
                    builder.withValue(FilmsColumns.ADULT,elemento1.isAdult());
                    builder.withValue(FilmsColumns.BACKDROP_PATH,elemento1.getBackdrop_path());
                    builder.withValue(FilmsColumns.ID_MOVI_DB,elemento1.getId());
                    builder.withValue(FilmsColumns.ORIGINAL_TITLE,elemento1.getOriginal_title());
                    builder.withValue(FilmsColumns.ORIGINAL_LANGUAGE,elemento1.getOriginal_language());
                    builder.withValue(FilmsColumns.OVERVIEW,elemento1.getOverview());
                    builder.withValue(FilmsColumns.RELEASSE_DATE,elemento1.getRelease_date());
                    builder.withValue(FilmsColumns.POSTER_PATH,elemento1.getPoster_path());
                    builder.withValue(FilmsColumns.POPULARITY,elemento1.getPopularity());
                    builder.withValue(FilmsColumns.TITLE,elemento1.getTitle());
                    builder.withValue(FilmsColumns.VIDEO,elemento1.isVideo());
                    builder.withValue(FilmsColumns.VOTE_AVERAGE,elemento1.getVote_average());
                    builder.withValue(FilmsColumns.VOTE_COUNT,elemento1.getVote_count());


                    batchOperations1.add(builder.build());
                    Log.d(LOG_TAG, "Llegaron los datos Films>: " + elemento1.getOriginal_title()+ "|" + elemento1.getTitle());
                    Log.d(LOG_TAG, "Llegaron los datos relise date>: " + elemento1.getRelease_date()+ "| Poster Path" + elemento1.getPoster_path());
                    Log.d(LOG_TAG, "Llegaron los datos Back?drop>: " + elemento1.getBackdrop_path());
//
                }

                try{
                    getActivity().getContentResolver().applyBatch(FilmsProvider.AUTHORITY, batchOperations1);
                    Cursor c = getActivity().getContentResolver().query(FilmsProvider.Films.CONTENT_URI,
                            null, null, null, null);

                    Log.d(LOG_TAG, "Lectura del cursor de films " + c.getCount());
                    Log.d(LOG_TAG, "Lectura del cursor Fillms" + dumpCursorToString(c));

//                    for(int i=1;i<c.getCount();i++) {
//
//                        getActivity().getContentResolver().delete(
//                                FilmsProvider.Trailes.withId(i), null, null);
//
//                    }

                    Cursor c1 = getActivity().getContentResolver().query(FilmsProvider.Films.CONTENT_URI,
                            null, null, null, null);
                    Log.d(LOG_TAG, "Lectura del cursos cantidadi con delete" + dumpCursorToString(c1));

                } catch(RemoteException | OperationApplicationException e){
                    Log.e(LOG_TAG, "Error applying batch insert", e);


                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "Falla: " + t.toString());
            }
        });


        ///// review

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(THE_MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService2 = retrofit1.create(GetReviewApi.class); // create the interface
        Log.d(LOG_TAG, "reviw -- created");

        // aSynchronous Call in Retrofit 2.0

        // aSynchronous Call in Retrofit 2.0



        Call<ReviewData> call2 = apiService2.getReviewFromApi(76341,VideoData.API_KEY);
        Log.d(LOG_TAG, "se llama a la web x reviews: " + call.getClass().getFields().toString());


        call2.enqueue(new Callback<ReviewData>() {
            @Override
            public void onResponse(Response<ReviewData> response) {
                // Get result Repo from response.body()
                Log.d(LOG_TAG, "Llegaron los datos comentarios %d" + response.code());

                //VideoData data = response.body();

                // Declaramos el Iterador e imprimimos los Elementos del ArrayList

                ReviewData videos1 = response.body();
                Log.d(LOG_TAG, "Llegaron los datos Films>:ID %d" + videos1.getId()+ "|totalresultaos:%d" + videos1.getTotalResults());
                Iterator<ReviewData.Result> videoa = videos1.getResults().iterator();
                while (videoa.hasNext()) {

                    ReviewData.Result elemento1 = videoa.next();


                    Log.d(LOG_TAG, "Llegaron los datos Review>: " + elemento1.getAuthor() +"|" + elemento1.getContent() );

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "Falla: " + t.toString());
            }
        });



    }


    public void updateData(){

        // Read the Preference to call the web
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order_by=prefs.getString(ORDER_BY, "popularity.");
        String asc_desc = prefs.getString(ASC_DESC, "desc");

        Log.i("Prererencias", "order_by:" + order_by + "asc_desc: " + asc_desc);


     // DELETE FILMS WHITH FAVORITE = FALSE

     // CALL WEB

        //Fetche the movis data
        //FetchMoviesTask moviesTask = new FetchMoviesTask();
        //moviesTask.execute(order_by, asc_desc);
    }

}
