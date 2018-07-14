package com.charlyge.android.globalacelchallengeapp;

/**
 * Created by DELL PC on 7/12/2018.
 */

public class persons {
    private String id;
    private String name;
    private int age;
    private String photoUrl;
    private String photoUrl_thumb;
    private String description;

    public persons(String id,String name,int age,String photoUrl,String photoUrl_thumb,String description){
        this.age=age;
        this.photoUrl=photoUrl;
        this.description=description;
        this.name=name;
        this.photoUrl_thumb=photoUrl_thumb;
        this.id =id;

    }

    public persons(String name,String photoUrl_thumb){
        this.name=name;
        this.photoUrl_thumb=photoUrl_thumb;

    }

    public String getDescription() {
        return description;
    }

    public int getAge() {
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

}
