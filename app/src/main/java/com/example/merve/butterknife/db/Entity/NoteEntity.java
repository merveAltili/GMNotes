package com.example.merve.butterknife.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.merve.butterknife.db.User;

/**
 * Created by merve on 08.03.2018.
 */
@Entity(tableName = "Note")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String detail;
    private Integer user;

    public NoteEntity(Long id, String title, String detail, Integer user) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.user = user;
    }
    public NoteEntity(){

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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;

    }
}
