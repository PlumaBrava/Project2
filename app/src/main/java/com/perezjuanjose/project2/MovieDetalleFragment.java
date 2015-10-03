package com.perezjuanjose.project2;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.perezjuanjose.project2.data.FilmsColumns;
import com.squareup.picasso.Picasso;

import static android.database.DatabaseUtils.dumpCursorToString;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetalleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private final String LOG_TAG = MovieDetalleFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";
    private Uri mUri;
    private static final int DETAIL_LOADER = 0;
    private ImageView movieImagen;
    private TextView mTitleView;
    private TextView mReleaseDateView;
    private TextView mVoteAverageView;
    private TextView mOverviewView;
    private View rootView;


    public MovieDetalleFragment() {
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

        return rootView;


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
        Log.i(LOG_TAG, "Lectura del curosr cargado" + dumpCursorToString(data));
        if (data != null && data.moveToFirst()) {
            Log.d(LOG_TAG, "Lectura del  cantidad de lineas" + data.getCount());

            mTitleView.setText(data.getString(data.getColumnIndex(FilmsColumns.TITLE)));
            Log.d(LOG_TAG, "Lectura del  Titulo" + data.getString(data.getColumnIndex(FilmsColumns.TITLE)));

            mReleaseDateView.setText(data.getString(data.getColumnIndex(FilmsColumns.RELEASSE_DATE)));
            Log.d(LOG_TAG, "Lectura del  Titulo" + data.getString(data.getColumnIndex(FilmsColumns.RELEASSE_DATE)));

            mVoteAverageView.setText(Float.toString(data.getFloat(data.getColumnIndex(FilmsColumns.VOTE_AVERAGE))));
            mOverviewView.setText(data.getString(data.getColumnIndex(FilmsColumns.OVERVIEW)));

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
