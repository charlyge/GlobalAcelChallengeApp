package com.charlyge.android.globalacelchallengeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlyge.android.globalacelchallengeapp.Model.Actualpersons;
import com.charlyge.android.globalacelchallengeapp.Model.persons;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DELL PC on 7/12/2018.
 */


public class personsAdapter extends RecyclerView.Adapter<personsAdapter.personViewHolder> {
    ArrayList<persons> personsArrayList = new Actualpersons().getPersons();
    Context context;
    final private personsAdapterItemClickListener personsAdapterItemClickListener;

    public personsAdapter(personsAdapterItemClickListener personsAdapterItemClickListener,Context context ) {

        this.personsAdapterItemClickListener=personsAdapterItemClickListener;
        this.context=context;

    }


    public interface personsAdapterItemClickListener {

        void onClick(String name,String description, String PhotoId, String age,String id);
    }

    @NonNull
    @Override
    public personViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_persons_list_item, parent, false);

        return new personViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull personViewHolder holder, int position) {
        persons newpersons = personsArrayList.get(position);
        final String age = newpersons.getAge();
        final String photo = newpersons.getPhotoUrl();
        final String photo_thumb= newpersons.getPhotoUrl_thumb();
        final String name = newpersons.getName();
        final String description = newpersons.getDescription();
        final String id = newpersons.getId();
        holder.nameTextView.setText(name);
        if (photo.isEmpty()) {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else{
            Picasso.get().load(photo).into(holder.imageView);
        }





    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (personsArrayList == null) {
            return 0;
        } else {
            return personsArrayList.size();
        }

    }

    class personViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        ImageView imageView;

        public personViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.tv_name);
            imageView = (ImageView)itemView.findViewById(R.id.tv_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            persons newpersons = personsArrayList.get(clickedPosition);
             String age = newpersons.getAge();
             String photo = newpersons.getPhotoUrl();
            String name = newpersons.getName();
            String description = newpersons.getDescription();
            String id = newpersons.getId();

            personsAdapterItemClickListener.onClick(name,description,photo,age,id);

        }
    }

    public void setWeatherData(Actualpersons personsArrayList) {
        this.personsArrayList = personsArrayList.getPersons();
        notifyDataSetChanged();
    }


}

