package com.charlyge.android.globalacelchallengeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditActivity extends AppCompatActivity {
    public static String IDEXTRA="IDEXTRA";
    public static String DESEXTRA="DES";
    EditText editText;
    Intent intent;
    String Id;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText = (EditText)findViewById(R.id.edit_text);
       progressBar = (ProgressBar)findViewById(R.id.progress);
        intent=getIntent();
        if(intent.hasExtra(DESEXTRA)){
            String editword= intent.getStringExtra(DESEXTRA);
            editText.setText(editword);

        }
        if(intent.hasExtra(IDEXTRA)){
            Id= intent.getStringExtra(IDEXTRA);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.save){

            Log.i("EDITACTIVITY", "personId iss " + Id);
            if (item.isEnabled()){
              new SendPostRequest().execute("https://intern-challenge.herokuapp.com/persons/"+Id);
                item.setEnabled(false);
            }


            //Save JSON data
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class SendPostRequest extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            int responseCode = 0;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            try {
                URL url = new URL(strings[0]);
                Log.i("EDITACTIVITY", "url iss " + url);
                JSONObject root = new JSONObject();

                String jsonNewWord= editText.getText().toString();

                root.put("description",jsonNewWord);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(30000 /* milliseconds */);
                conn.setConnectTimeout(30000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(root.toString());
                Log.i("EDITACTIVITY", "output iss " + writer);
                writer.flush();
                writer.close();
                outputStream.close();
                responseCode=conn.getResponseCode();




            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return String.valueOf(responseCode);
        }

        @Override
        protected void onPostExecute(String s) {
      progressBar.setVisibility(View.GONE);

            Log.i("EDITACTIVITY", "Response code " + s);
            if(s.equals("200")){
                Toast.makeText(EditActivity.this,"upload Successful " ,Toast.LENGTH_LONG).show();
                Intent intent =new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(EditActivity.this,"upload Failed Enable Internet !! Contact us if this persist... " ,Toast.LENGTH_LONG).show();
            }
        }
    }


}

