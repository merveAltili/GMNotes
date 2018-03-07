package com.example.merve.butterknife;

import com.example.merve.butterknife.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by merve on 07.03.2018.
 */

public interface NodeAPI {
    @GET("/Login")
    Call<LoginResponse> sendLogin(@Query("username") String username, @Query("password") String passs );


}
