package com.charlyge.android.globalacelchallengeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<persons>>,
        SharedPreferences.OnSharedPreferenceChangeListener{
    RecyclerView recyclerView;
    personsAdapter adapter;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private static final int LOADER_ID = 1;
    ArrayList<persons> arrayList = null;
    String queryparam;
    private boolean PREFERENCE_UPDATED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        errorTextView = (TextView) findViewById(R.id.error_view);
        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            adapter = new personsAdapter(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (networkInfo != null) {

            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);

        }
        else{

            progressBar.setVisibility(View.INVISIBLE);
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

                queryparam = pagePreference.changePageNo(MainActivity.this);
                URL url = networkUtils.buildUrl(queryparam);

                try {
                    String jsonData = networkUtils.makeHttpUrlConnection(url);
                    arrayList = networkUtils.extractFeaturesFromJson(jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
                return arrayList;

            }


            @Override
            protected void onStartLoading() {
                if (arrayList != null) {
                    deliverResult(arrayList);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
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
        errorTextView.setVisibility(View.INVISIBLE);

        if (data != null && !data.isEmpty()) {
            adapter.setWeatherData(data);

        } else {
            errorTextView.setText(R.string.no_data);
            errorTextView.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<persons>> loader) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            if (networkInfo != null) {

          resetData();
                      progressBar.setVisibility(View.VISIBLE);
                    getSupportLoaderManager().restartLoader(LOADER_ID, null, this);

            } else {
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setText("An Error Occured Enable Internet Connection and Retry");
                errorTextView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Enable Internet Connection and Retry", Toast.LENGTH_LONG).show();
            }

return true;
        }

        if (id==R.id.pageno){
            Intent intent = new Intent(this, PageNoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
}

    private void resetData() {
       adapter.setWeatherData(null);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCE_UPDATED =true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister to avoid any memory leaks. */
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (PREFERENCE_UPDATED) {

            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            PREFERENCE_UPDATED = false;
        }
    }
}