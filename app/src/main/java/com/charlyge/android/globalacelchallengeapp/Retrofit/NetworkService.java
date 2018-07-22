package com.charlyge.android.globalacelchallengeapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL PC on 7/18/2018.
 */

public class NetworkService {

    private final Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl("https://intern-challenge.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    private final JSONPlaceHolder api =
            retrofit.create(JSONPlaceHolder.class);

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public JSONPlaceHolder getApi() {
        return api;
    }

    private NetworkService() {}
    private static final NetworkService ourInstance = new NetworkService();

    public static NetworkService getInstance () {
        return ourInstance;
    }
}
