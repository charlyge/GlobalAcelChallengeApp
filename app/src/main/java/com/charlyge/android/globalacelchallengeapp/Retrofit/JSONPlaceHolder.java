package com.charlyge.android.globalacelchallengeapp.Retrofit;

import com.charlyge.android.globalacelchallengeapp.Model.Actualpersons;
import com.charlyge.android.globalacelchallengeapp.Model.persons;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DELL PC on 7/18/2018.
 */

public interface JSONPlaceHolder {
    @GET("/persons/")
    Call<Actualpersons> personList(@Query("page") String param);


    @FormUrlEncoded
    @POST("/persons/{id}")
    Call<persons> editDes(@Path("id") String id , @Field("description") String description);
}
