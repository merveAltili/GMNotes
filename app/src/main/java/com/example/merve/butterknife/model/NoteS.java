package com.example.merve.butterknife.model;

import android.arch.persistence.room.PrimaryKey;

/**
 * Created by merve on 19.03.2018.
 */

public class NoteS {
    @PrimaryKey
    private Long id;
    private String title;
    private String detail;

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
