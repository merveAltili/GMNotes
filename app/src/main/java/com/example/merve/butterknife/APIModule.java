package com.example.merve.butterknife;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by merve on 07.03.2018.
 */

public class APIModule {
    private static  final String NodeAdd_URL="http://10.17.0.46";
    public static Retrofit connectNodeAPI(){
        return new Retrofit.Builder().baseUrl(NodeAdd_URL)
                .build();
    }
}
