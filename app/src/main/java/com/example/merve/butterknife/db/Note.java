package com.example.merve.butterknife.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by merve on 08.03.2018.
 */
@Entity
public class Note {
    @PrimaryKey
    private Long id;
    private String title;
    private String detail;

    public Note(Long id,String title, String detail){
        this.id=id;
        this.title=title;
        this.detail=detail;
    }
    public Note(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
