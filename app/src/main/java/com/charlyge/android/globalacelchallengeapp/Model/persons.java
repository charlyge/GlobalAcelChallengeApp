package com.charlyge.android.globalacelchallengeapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL PC on 7/12/2018.
 */

public class persons {
    @SerializedName("id") @Expose
    private String id;
    @SerializedName("name") @Expose
    private String name;
    @SerializedName("age") @Expose
    private String age;
    @SerializedName("photo") @Expose
    private String photoUrl;
    @SerializedName("photo_thumb") @Expose
    private String photoUrl_thumb;
    @SerializedName("description") @Expose
    private String description;

    public persons(String id, String name, String age, String photoUrl, String photoUrl_thumb, String description){
        this.age=age;
        this.photoUrl=photoUrl;
        this.description=description;
        this.name=name;
        this.photoUrl_thumb=photoUrl_thumb;
        this.id =id;

    }

    public persons(String description){

        this.description=description;

    }


    public String getDescription() {
        return description;
    }

    public String getAge() {
        return age;
    }

    public String getPhotoUrl_thumb() {
        return photoUrl_thumb;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl_thumb(String photoUrl_thumb) {
        this.photoUrl_thumb = photoUrl_thumb;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }


}
