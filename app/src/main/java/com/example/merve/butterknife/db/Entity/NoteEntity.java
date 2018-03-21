package com.example.merve.butterknife.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by merve on 08.03.2018.
 */
@Entity(tableName = "Note")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String detail;
    private String user;
    private Long date;

    public Integer getColors() {
        return colors;
    }

    public void setColors(Integer colors) {
        this.colors = colors;
    }

    private Integer colors;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }





    public NoteEntity( String title, String detail, String user,Long date) {

        this.title = title;
        this.detail = detail;
        this.user = user;
        this.date=date;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;

    }
}
