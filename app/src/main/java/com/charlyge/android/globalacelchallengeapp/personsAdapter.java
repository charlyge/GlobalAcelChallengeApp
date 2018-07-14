package com.charlyge.android.globalacelchallengeapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.AGE;
import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.DESCRIPTION;
import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.ID;
import static com.charlyge.android.globalacelchallengeapp.DetailsActivity.PHOTO;

/**
 * Created by DELL PC on 7/12/2018.
 */


public class personsAdapter extends RecyclerView.Adapter<personsAdapter.personViewHolder> {
    ArrayList<persons> personsArrayList = new ArrayList<>();
    Context context;

    public personsAdapter(Context context){
        this.context=context;

    }



    @NonNull
    @Override
    public personViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_persons_list_item,parent,false);

        return new personViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull personViewHolder holder, int position) {
        persons newpersons = personsArrayList.get(position);
        final int age = newpersons.getAge();
        final String photo = newpersons.getPhotoUrl();
        final String photo_thumb= newpersons.getPhotoUrl_thumb();
        final String name = newpersons.getName();
        final String description = newpersons.getDescription();
        final String id = newpersons.getId();
        holder.nameTextView.setText(name);
        Picasso.get().load(photo_thumb).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,name);
                intent.putExtra(DESCRIPTION,description);

                intent.putExtra(PHOTO,photo);
                intent.putExtra(ID,id);
                intent.putExtra(AGE,age);

                context.startActivity(intent);

            }
        });


    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (personsArrayList==null){
            return 0;
        }
        else {
            return personsArrayList.size();
        }

    }

    class personViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        ImageView imageView;
        public View layout;

        public personViewHolder(View itemView) {
            super(itemView);
            layout=itemView;
            nameTextView = (TextView)itemView.findViewById(R.id.tv_name);
            imageView = (ImageView)itemView.findViewById(R.id.tv_image);
        }
    }

    public void setWeatherData(ArrayList<persons> personsArrayList) {
        this.personsArrayList =personsArrayList;
        notifyDataSetChanged();
    }
}

