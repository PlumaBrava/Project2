package com.perezjuanjose.project2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.perezjuanjose.project2.Services.ReviewServices;
import com.perezjuanjose.project2.Services.TrailerServices;
import com.perezjuanjose.project2.data.FilmsColumns;
import com.perezjuanjose.project2.data.FilmsProvider;
import com.perezjuanjose.project2.data.ReviewCursorAdapter;
import com.perezjuanjose.project2.data.TrailerColumns;
import com.perezjuanjose.project2.data.TrailerCursorAdapter;
import com.squareup.picasso.Picasso;

import static android.database.DatabaseUtils.dumpCursorToString;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetalleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String MOVIDB_SHARE_HASHTAG = " #MoviDB";
    private ShareActionProvider mShareActionProvider;
    private String mfirstTrailer="you tube";
    private final String LOG_TAG = MovieDetalleFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";
    private Uri mUri;
    private static final int DETAIL_LOADER = 0;
    private ImageView movieImagen;
    private TextView mTitleView;
    private TextView mReleaseDateView;
    private TextView mVoteAverageView;
    private TextView mOverviewView;
    private CheckBox mfavorite;
    private int m_Ref_Id_Movi;
    private int m_Id_Movi_Db;
    private View rootView;
    private TrailerCursorAdapter mTrailerAdapter; //pueden ser valiables locales?
    private ReviewCursorAdapter mReviewAdapter;//pueden ser valiables locales?
    private Cursor mTrailerCursor;
    private Cursor mReviewCursor;
    private ListView listView;
    private ListView listViewReview;

    public MovieDetalleFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detalle_fragment, menu);
        //inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.

//        mShareActionProvider.setShareIntent(createShareIntent());
        if (mfirstTrailer != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mfirstTrailer + MOVIDB_SHARE_HASHTAG);
        //shareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(MovieDetalleFragment.DETAIL_URI);
            Log.i(LOG_TAG, "uri:" + mUri.toString());
        }


        rootView = inflater.inflate(R.layout.fragment_movi_detalle, container, false);




         movieImagen =(ImageView) rootView.findViewById(R.id.detail_image);


         mTitleView=(TextView) rootView.findViewById(R.id.detail_title);



         mReleaseDateView=(TextView) rootView.findViewById(R.id.releace_data);
         mVoteAverageView =(TextView) rootView.findViewById(R.id.vote_average);
         mOverviewView=(TextView) rootView.findViewById(R.id.overview);
        mfavorite=(CheckBox) rootView.findViewById(R.id.favorite);
        mfavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Create string buffer to
                ContentValues upDateValues = new ContentValues();
                upDateValues.put(FilmsColumns.FAVORITE, booleanToInt(mfavorite.isChecked()));

                getActivity().getContentResolver().update(FilmsProvider.Films.withId(m_Ref_Id_Movi),
                        upDateValues,null,null);

            }
        });


// Reviews
        mReviewCursor = getActivity().getContentResolver().query(FilmsProvider.Reviews.CONTENT_URI,
                null, null, null, null);




            // Get a reference to the ListView, and attach this adapter to it.
            listViewReview = (ListView) rootView.findViewById(R.id.reviewList);



// Trailers

        mTrailerCursor = getActivity().getContentResolver().query(FilmsProvider.Trailes.withId(m_Id_Movi_Db),
                null, null, null, null);
            // Get a reference to the ListView, and attach this adapter to it.
           listView = (ListView) rootView.findViewById(R.id.trailerList);
           // listView.setAdapter(mTrailerAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // CursorAdapter returns a cursor at the correct position for getItem(), or null
                    // if it cannot seek to that position.
                    Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                    if (cursor != null) {
                        //       String locationSetting = Utility.getPreferredLocation(getActivity());

                        try {

                            String key = cursor.getString(cursor.getColumnIndex(TrailerColumns.KEY));
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                            startActivity(intent);

                        } catch(Exception e) {
                            Toast.makeText(getActivity(), "Plase install Youtube app to see this video", Toast.LENGTH_LONG).show();
                        }


                    }

                }
            });



        return rootView;



    }

    public int booleanToInt(boolean b){

        if(b){return 1;}
        else return 0;
    }

    public boolean intToBoolean (int i){
        if (i==0){
            return false;}
        else {return true;}

    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    null,
                    null,
                    null,
                    null
            );
        }
        return null;
            }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(LOG_TAG, "curosorCargado");
        Log.i(LOG_TAG, "Lectura del curosr cargado" + dumpCursorToString(data).toString());
        if (data != null && data.moveToFirst()) {
            Log.d(LOG_TAG, "Lectura del  cantidad de lineas" + data.getCount());
            m_Ref_Id_Movi=data.getInt(data.getColumnIndex(FilmsColumns._ID));

            m_Id_Movi_Db=data.getInt(data.getColumnIndex(FilmsColumns.ID_MOVI_DB));


     //Trailers
           mTrailerCursor = getActivity().getContentResolver().query(FilmsProvider.Trailes.withId(m_Id_Movi_Db),
                   null, null, null, null);
            if (mTrailerCursor != null && mTrailerCursor.moveToFirst()) {
                mfirstTrailer="https://www.youtube.com/watch?v="+ mTrailerCursor.getString(mTrailerCursor.getColumnIndex(TrailerColumns.KEY));
                mTrailerAdapter = new TrailerCursorAdapter(getActivity(), mTrailerCursor, 0);
                listView.setAdapter(mTrailerAdapter);
                Log.i(LOG_TAG, "mfirstTrailer:" + mfirstTrailer);
                Log.i(LOG_TAG, "FilmsColumns.ID_MOVI_DB:" + FilmsColumns.ID_MOVI_DB);
                Log.i(LOG_TAG, "Lectura del  m_Id_Movi_Db" + data.getInt(data.getColumnIndex(FilmsColumns.ID_MOVI_DB)));
            }else {
                Intent intent1 = new Intent( getActivity(), TrailerServices.class);
                intent1.putExtra(TrailerServices.TRILER_QUERY_EXTRA, m_Id_Movi_Db);
                getActivity().startService(intent1);
                Log.i(LOG_TAG, "Se llamo TrailerService");

            }

      //Reviews


            mReviewCursor = getActivity().getContentResolver().query(FilmsProvider.Reviews.withId(m_Id_Movi_Db),
                    null, null, null, null);
            if (mReviewCursor != null && mReviewCursor.moveToFirst()) {
                mReviewAdapter = new ReviewCursorAdapter(getActivity(), mReviewCursor, 0);
                listViewReview.setAdapter(mReviewAdapter);
            }else {
                Intent intent2 = new Intent(getActivity(), ReviewServices.class);
                intent2.putExtra(ReviewServices.REVIER_QUERY_EXTRA,m_Id_Movi_Db);
                getActivity().startService(intent2);
                Log.i(LOG_TAG, "Se llamo REVIEWService");
            }


            mTitleView.setText(data.getString(data.getColumnIndex(FilmsColumns.TITLE)));


                mReleaseDateView.setText(data.getString(data.getColumnIndex(FilmsColumns.RELEASSE_DATE)));

            mVoteAverageView.setText(Float.toString(data.getFloat(data.getColumnIndex(FilmsColumns.VOTE_AVERAGE))));
            mOverviewView.setText(data.getString(data.getColumnIndex(FilmsColumns.OVERVIEW)));

            mfavorite.setChecked(intToBoolean(data.getInt(data.getColumnIndex(FilmsColumns.FAVORITE))));

            Picasso.with(rootView.getContext())
                   .load("http://image.tmdb.org/t/p/w185" + data.getString(data.getColumnIndex(FilmsColumns.POSTER_PATH)))
                    .into(movieImagen);



        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
