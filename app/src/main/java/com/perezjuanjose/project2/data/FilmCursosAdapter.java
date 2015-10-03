package com.perezjuanjose.project2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.perezjuanjose.project2.R;
import com.squareup.picasso.Picasso;


/**
 * Created by JJ_PEREZ on 22/09/2015.
 */
public class FilmCursosAdapter extends CursorAdapter{

    public FilmCursosAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        DatabaseUtils.dumpCursor(cursor);


//        TextView versionNameView = (TextView)  view.findViewById(R.id.list_item_title);
//        versionNameView.setText(cursor.getString(cursor.getColumnIndex(FilmsColumns.TITLE)));


        ImageView iconView = (ImageView) view.findViewById(R.id.list_item_icon);
        //iconView.setImageResource(R.drawable.cupcake);

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185" + (cursor.getString(cursor.getColumnIndex(FilmsColumns.POSTER_PATH))))
                        .into(iconView);

//        TextView versionNumberView = (TextView)  view.findViewById(R.id.list_item_popularity);
//        versionNumberView.setText(cursor.getString(cursor.getColumnIndex(FilmsColumns.POPULARITY)));
//
//        RatingBar ratingBar= (RatingBar) view.findViewById(R.id.rating);
//        ratingBar.setRating((float) cursor.getFloat(cursor.getColumnIndex(FilmsColumns.VOTE_AVERAGE)) /100 * 5);





    }

}
