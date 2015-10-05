package com.perezjuanjose.project2;


import android.content.ContentProviderOperation;
import android.content.Intent;
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

import com.perezjuanjose.project2.Services.FilmServices;
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

    public int mMostPolular;
    public int mHightestrated;
    public int mFavorite;

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
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Add this line in order for this fragment to handle menu events.
//        setHasOptionsMenu(true);
//    }
    @Override
    public void onStart(){

        Log.i("Prererencias", "On Start");
        Log.i("Prererencias", "mthis" + preferenceHasChanged);
        if(preferenceHasChanged) {


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String order_by=prefs.getString(ORDER_BY, "popularity.");

            Log.i("Prererencias", "order_by:" + order_by );

            mMostPolular =0;
            mHightestrated=0;
            mFavorite=0;

            if(order_by.equals("popularity.")){

                mMostPolular =1;
                updateData();
                Log.i("Prererencias", " casse popularity: ");
            } else if (order_by.equals("vote_average.")){
                mHightestrated=1;
                updateData();
                Log.i("Prererencias", " casse hight rated: ");
            }else if (order_by.equals("favorites.")){

            mFavorite=1;
            Log.i("Prererencias", " casse favorito: ");
            //it's not necesary update
                }
            Log.i("Prererencias", "popularity: " + mMostPolular+ " Hightestrated: "+ mHightestrated +" favorites: "+ mFavorite );


           // updateData();
            preferenceHasChanged=false;

            //updateData();
        }
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Cursor c = getActivity().getContentResolver().query(FilmsProvider.Films.CONTENT_URI,
                null, null, null, null);

        if (c == null || c.getCount() == 0){
            Log.i(LOG_TAG, "Se llama a insertData desde create view");
           updateData();
        }
        Log.i(LOG_TAG, "cursor count: %d" + c.getCount());



        View rootView = inflater.inflate(R.layout.moviefragment, container, false);
        mFilmAdapter = new FilmCursosAdapter(getActivity(), c, 0);
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
       Uri uri;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order_by=prefs.getString(ORDER_BY, "popularity.");

        if(order_by.equals("popularity.")){
            uri=FilmsProvider.Films.CONTENT_URI;
            mMostPolular =1;
            Log.i("Prererencias", " casse popularity: ");

        } else if (order_by.equals("vote_average.")){
            uri=FilmsProvider.Films.CONTENT_URI;
            mHightestrated=1;
            Log.i("Prererencias", " casse hight rated: ");

        }else if (order_by.equals("favorites.")){
            uri=FilmsProvider.Films.withFavoritos(1);
            mFavorite=1;
            Log.i("Prererencias", " casse favorito: ");
            //it's not necesary update
        }else {

            return new CursorLoader(getActivity(), FilmsProvider.Films.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }
        Log.i("Prererencias", "popularity: " + mMostPolular + " Hightestrated: " + mHightestrated + " favorites: " + mFavorite);

        if ( null != uri ) {
            return new CursorLoader(getActivity(), uri,
                    null,
                    null,
                    null,
                    null);
        }

        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFilmAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFilmAdapter.swapCursor(null);
    }

public boolean existeFilmenInDB(int idFilm){
    Cursor c = getActivity().getContentResolver().query(FilmsProvider.Films.withId(idFilm),
            null, null, null, null);
    if (c.getCount()>0) return true;
    else return false;
}




    public void updateData(){

        // Read the Preference to call the web
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order_by=prefs.getString(ORDER_BY, "popularity.");


        Log.i("Prererencias", "order_by:" + order_by );

        Intent intent = new Intent(getActivity(), FilmServices.class);
        intent.putExtra(FilmServices.FILM_QUERY_EXTRA,"SomeDAta");
        getActivity().startService(intent);
        // DELETE FILMS WHITH FAVORITE = FALSE

     // CALL WEB
        //insertData();
        //Fetche the movis data
        //FetchMoviesTask moviesTask = new FetchMoviesTask();

    }

}
