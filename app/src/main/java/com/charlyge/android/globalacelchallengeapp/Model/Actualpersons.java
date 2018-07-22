package com.charlyge.android.globalacelchallengeapp.Model;

import com.charlyge.android.globalacelchallengeapp.Model.persons;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by DELL PC on 7/20/2018.
 */

public class Actualpersons {

    @SerializedName("persons")

    @Expose  public ArrayList<persons> persons = null;

    public ArrayList<persons> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<persons> persons) {
        this.persons = persons;
    }
}
