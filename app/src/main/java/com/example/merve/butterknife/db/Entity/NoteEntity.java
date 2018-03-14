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

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    private String kategori;

    public Long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        this.kategoriId = kategoriId;
    }

    private Long kategoriId;

    public NoteEntity( String title, String detail, String user, Long kategoriId) {
        this.kategoriId=kategoriId;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;

    }
}
