package com.charlyge.android.globalacelchallengeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyge.android.globalacelchallengeapp.Model.Actualpersons;
import com.charlyge.android.globalacelchallengeapp.Retrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.AGE;
import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.DESCRIPTION;
import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.ID;
import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.PHOTO;
import static com.charlyge.android.globalacelchallengeapp.preferences.pagePreference.changePageNo;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener, personsAdapter.personsAdapterItemClickListener {
    RecyclerView recyclerView;
    personsAdapter adapter;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private boolean PREFERENCE_UPDATED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorTextView = (TextView) findViewById(R.id.error_view);
        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new personsAdapter(MainActivity.this,this);
        recyclerView.setAdapter(adapter);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
      loadJSON();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {
            MainActivity.this.recreate();

return true;
        }

        if (id==R.id.pageno){
            Intent intent = new Intent(this, PageNoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void onClick(String name, String description, String PhotoId, String age, String id) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, name);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(PHOTO, PhotoId);
        intent.putExtra(ID, id);
        intent.putExtra(AGE, age);
        startActivity(intent);
    }


    private void loadJSON() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (networkInfo != null) {
            String pref = changePageNo(this);

            NetworkService.getInstance().getApi().personList(pref).enqueue(new Callback<Actualpersons>() {
                @Override
                public void onResponse(Call<Actualpersons> call, Response<Actualpersons> response) {
                    if(response.body()!=null){
                        Log.i("ResponseBody", response.body().toString());
                        adapter.setWeatherData(response.body());
                        Log.i("MAINACTIVITY","sucess" + response.body());
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<Actualpersons> call, Throwable t) {
                    Log.i("MAINACTIVITY","fail " + t.getMessage());
                }
            });
        }




        else{

            progressBar.setVisibility(View.INVISIBLE);
            errorTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();


        if (PREFERENCE_UPDATED) {
            Log.d("main", "onStart: preferences were updated");
      MainActivity.this.recreate();
            PREFERENCE_UPDATED = false;
        }
    }


}