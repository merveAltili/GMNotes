package com.example.merve.butterknife.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.merve.butterknife.db.Entity.NoteEntity;

import java.util.List;

/**
 * Created by merve on 08.03.2018.
 */
@Dao
public interface NoteDao {
    @Insert
    public void InsertNote(NoteEntity note);
    @Delete
    public void DeleteNote(NoteEntity note);

    @Query("SELECT * FROM Note WHERE kategori= :kategori")
    public List<NoteEntity> getNotesByKategori(String kategori);


    @Query("SELECT distinct kategori FROM Note ")
    public List<NoteEntity>  getKategoris();

}

