package com.perezjuanjose.project2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perezjuanjose.project2.R;


/**
 * Created by JJ_PEREZ on 22/09/2015.
 */
public class ReviewCursorAdapter extends CursorAdapter{

    public ReviewCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        DatabaseUtils.dumpCursor(cursor);


      TextView reviewAutor = (TextView)  view.findViewById(R.id.review_autor);
        reviewAutor.setText((cursor.getString(cursor.getColumnIndex(ReviewColumns.AUTOR))));
        TextView reviewComent = (TextView)  view.findViewById(R.id.review_coment);
        reviewComent.setText((cursor.getString(cursor.getColumnIndex(ReviewColumns.COMENT))));
    }

}
