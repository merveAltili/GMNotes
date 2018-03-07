package com.example.merve.butterknife.model;

//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("LoginSuccess")
    @Expose
    private String loginSuccess;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(String loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

}
