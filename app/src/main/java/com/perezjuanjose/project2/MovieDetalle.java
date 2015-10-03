package com.perezjuanjose.project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MovieDetalle extends AppCompatActivity {

    private final String LOG_TAG = MovieDetalle.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movi_detalle);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
        Bundle args = new Bundle();
        args.putParcelable(MovieDetalleFragment.DETAIL_URI, getIntent().getData());
        Log.i(LOG_TAG, "uri:" + args.toString());

        MovieDetalleFragment fragment = new MovieDetalleFragment();
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.film_detail_container, fragment)
                .commit();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movi_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
