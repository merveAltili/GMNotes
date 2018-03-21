package com.example.merve.butterknife.model;

import android.arch.persistence.room.PrimaryKey;

/**
 * Created by merve on 08.03.2018.
 */

public class User {
    @PrimaryKey
    private Long id;
    private String username;
    private String password;

public User(Long id, String username,String password){
    this.id=id;
    this.username=username;
    this.password=password;
}

public User(){

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
