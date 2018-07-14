package com.charlyge.android.globalacelchallengeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<persons>> {
    RecyclerView recyclerView;
    personsAdapter adapter;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private static final int LOADER_ID = 1;
    ArrayList<persons> arrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        errorTextView = (TextView) findViewById(R.id.error_view);
        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (networkInfo != null) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            adapter = new personsAdapter(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);


            progressBar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(MainActivity.this, "Enable Internet ", Toast.LENGTH_LONG).show();
            errorTextView.setVisibility(View.VISIBLE);

        }


    }

    @NonNull
    @Override
    public Loader<ArrayList<persons>> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<ArrayList<persons>>(this) {

            @Nullable
            @Override
            public ArrayList<persons> loadInBackground() {

                try {
                    String jsonData = networkUtils.makeHttpUrlConnection("https://intern-challenge.herokuapp.com/persons");
                    arrayList = networkUtils.extractFeaturesFromJson(jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return arrayList;

            }


            @Override
            protected void onStartLoading() {
                if (arrayList != null) {
                    deliverResult(arrayList);
                } else {
                    forceLoad();
                }


            }


            @Override
            public void deliverResult(@Nullable ArrayList<persons> data) {
                arrayList = data;
                super.deliverResult(data);
            }
        };
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<persons>> loader, ArrayList<persons> data) {
        progressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            adapter.setWeatherData(data);

        } else {
            errorTextView.setText(R.string.no_data_found);
            errorTextView.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<persons>> loader) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            if (networkInfo != null) {
                MainActivity.this.recreate();
            } else {

                Toast.makeText(MainActivity.this, "Enable Internet Connection and Retry", Toast.LENGTH_LONG).show();
            }

return true;
        }

        return super.onOptionsItemSelected(item);
}
   /* private void resetData() {
       adapter.setWeatherData(null);
    }*/
}