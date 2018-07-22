package com.charlyge.android.globalacelchallengeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import static com.charlyge.android.globalacelchallengeapp.EditActivity.DESEXTRA;
import static com.charlyge.android.globalacelchallengeapp.EditActivity.IDEXTRA;


public class DetailsActivity extends AppCompatActivity {
    public static String DESCRIPTION ="description";
    public static String PHOTO = "photo";
    public static String ID = "id";
    public static String AGE = "age";
    private int DEFAULT=344;
    TextView nameTextView;
    TextView ageTextView;
    TextView descriptionTextView;
    ImageView imageView;
    Intent intent;
    ImageView editImageView;
    String description;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        intent = getIntent();
        nameTextView = (TextView)findViewById(R.id.tv_name_details);
        ageTextView = (TextView)findViewById(R.id.tv_age_details);
        descriptionTextView = (TextView)findViewById(R.id.tv_description);
        imageView = (ImageView)findViewById(R.id.imageView_details);
        editImageView = (ImageView)findViewById(R.id.edit_iv);
        imageEditClickListener();
        getIntentExtras();
    }

    private void getIntentExtras() {
        if(intent.hasExtra(PHOTO)){
            String photoUrl=  intent.getStringExtra(PHOTO);
            if (photoUrl.isEmpty()) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            } else{
                Picasso.get().load(photoUrl).into(imageView);
            }


        }
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            String name = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.i("DetailActivity","name issssss" + name);
            nameTextView.setText(name);
        }
        if(intent.hasExtra(DESCRIPTION)){
            description = intent.getStringExtra(DESCRIPTION);
            descriptionTextView.setText(description);

        }
        if(intent.hasExtra(AGE)){
            int age = intent.getIntExtra(AGE,DEFAULT);
            ageTextView.setText(String.valueOf(age));

        }

        if(intent.hasExtra(ID)){
            id = intent.getStringExtra(ID);


        }
    }

    private void imageEditClickListener(){
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,EditActivity.class);
                intent.putExtra(IDEXTRA,id);
                intent.putExtra(DESEXTRA,description);
                startActivity(intent);
            }
        });



    }
}
