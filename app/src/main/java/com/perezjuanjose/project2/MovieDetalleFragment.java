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
    private String mfirstTrailer="xx";
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
    private ListView listView;

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
//        Intent intent = getActivity().getIntent();
//        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT) ){
//            String sStr = intent.getStringExtra(Intent.EXTRA_TEXT);
//
//
//            Boolean adults= intent.getBooleanExtra("ADULTS", false) ;
//            String backdrop_path=intent.getStringExtra("BACKDROP_PATH");
//            String origianlLanguaje=intent.getStringExtra("ORIGINLANGUAJE");
//            String originalTitle=intent.getStringExtra("ORIGINALTITLE");
//            String overview=intent.getStringExtra("OVERVIEW");
//            String releaseDate=intent.getStringExtra("RELEASEDATE");
//            String posterPath=intent.getStringExtra("POSTERPATH");
//            Double popularity=intent.getDoubleExtra("POPULARITY", 0);
//            String title=intent.getStringExtra("TITLE");
//            Boolean video= intent.getBooleanExtra("VIDEO", false) ;
//            Double voteAverage=intent.getDoubleExtra("VOTEAVERAGE", 0);
//            int vote_count=intent.getIntExtra("VOTECOUNT",0);



         movieImagen =(ImageView) rootView.findViewById(R.id.detail_image);
//            Picasso.with(rootView.getContext())
//                    .load("http://image.tmdb.org/t/p/w185"+posterPath)
//                    .into(movieImagen);

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

//        Cursor cReview = getActivity().getContentResolver().query(FilmsProvider.Reviews.CONTENT_URI,
//                null, null, null, null);
//
//        if ((cReview == null || cReview.getCount() == 0))
//        {
//            // no hay comentarios
//        }
//        else {
//            mReviewAdapter = new ReviewCursorAdapter(getActivity(), cReview, 0);
//
//
//            // Get a reference to the ListView, and attach this adapter to it.
//            ListView listViewReview = (ListView) rootView.findViewById(R.id.reviewList);
//            listViewReview.setAdapter(mReviewAdapter);
//        }


//        mTrailerCursor = getActivity().getContentResolver().query(FilmsProvider.Trailes.withId(m_Id_Movi_Db),
//                null, null, null, null);




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

                        String key = cursor.getString(cursor.getColumnIndex(TrailerColumns.KEY));
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:7voEoWRKbAE" ));
                        startActivity(intent);

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
           mTrailerCursor = getActivity().getContentResolver().query(FilmsProvider.Trailes.withId(m_Id_Movi_Db),
                   null, null, null, null);
            mTrailerAdapter = new TrailerCursorAdapter(getActivity(), mTrailerCursor, 0);
            listView.setAdapter(mTrailerAdapter);

            Log.i(LOG_TAG, "FilmsColumns.ID_MOVI_DB:"+FilmsColumns.ID_MOVI_DB);
            Log.i(LOG_TAG, "Lectura del  m_Id_Movi_Db" + data.getInt(data.getColumnIndex(FilmsColumns.ID_MOVI_DB)));

            mTitleView.setText(data.getString(data.getColumnIndex(FilmsColumns.TITLE)));
            Log.d(LOG_TAG, "Lectura del  Titulo" + data.getString(data.getColumnIndex(FilmsColumns.TITLE)));

            mReleaseDateView.setText(data.getString(data.getColumnIndex(FilmsColumns.RELEASSE_DATE)));
            Log.d(LOG_TAG, "Lectura del  Titulo" + data.getString(data.getColumnIndex(FilmsColumns.RELEASSE_DATE)));

            mVoteAverageView.setText(Float.toString(data.getFloat(data.getColumnIndex(FilmsColumns.VOTE_AVERAGE))));
            mOverviewView.setText(data.getString(data.getColumnIndex(FilmsColumns.OVERVIEW)));

            mfavorite.setChecked(intToBoolean(data.getInt(data.getColumnIndex(FilmsColumns.FAVORITE))));

            Picasso.with(rootView.getContext())
                   .load("http://image.tmdb.org/t/p/w185" + data.getString(data.getColumnIndex(FilmsColumns.POSTER_PATH)))
                    .into(movieImagen);

//        versionNumberView.setText(cursor.getString(cursor.getColumnIndex(FilmsColumns.POPULARITY)));
//
//        RatingBar ratingBar= (RatingBar) view.findViewById(R.id.rating);
//        ratingBar.setRating((float) cursor.getFloat(cursor.getColumnIndex(FilmsColumns.VOTE_AVERAGE)) /100 * 5);

            // Use weather art image

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
