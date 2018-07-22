package com.charlyge.android.globalacelchallengeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.charlyge.android.globalacelchallengeapp.Retrofit.NetworkService;
import com.charlyge.android.globalacelchallengeapp.Model.persons;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    public static String IDEXTRA = "IDEXTRA";
    public static String DESEXTRA = "DES";
    EditText editText;
    Intent intent;
    String Id;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText = (EditText) findViewById(R.id.edit_text);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        intent = getIntent();
        if (intent.hasExtra(DESEXTRA)) {
            String editword = intent.getStringExtra(DESEXTRA);
            editText.setText(editword);

        }
        if (intent.hasExtra(IDEXTRA)) {
            Id = intent.getStringExtra(IDEXTRA);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            progressBar.setVisibility(View.VISIBLE);

            Log.i("EDITACTIVITY", "personId iss " + Id);
            if (item.isEnabled()) {
                EditDescription();
                item.setEnabled(false);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    private void EditDescription() {
        String jsonNewWord = editText.getText().toString();
        NetworkService.getInstance().getApi().editDes(Id,jsonNewWord).enqueue(new Callback<persons>() {
            @Override
            public void onResponse(Call<persons> call, Response<persons> response) {
                progressBar.setVisibility(View.GONE);

                Log.i("EDITACTIVITY", "Response code " + response.code());
                if (response.code()==200) {
                    Toast.makeText(EditActivity.this, "upload Successful ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "upload Failed Enable Internet !! Contact us if this persist... ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<persons> call, Throwable t) {
                Log.i("EDITACTIVITY", "fail "+ t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}

