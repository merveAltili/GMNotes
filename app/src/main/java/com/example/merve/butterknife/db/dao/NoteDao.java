package com.example.merve.butterknife.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Update
    public void UpdateNote(NoteEntity note);

    @Query("SELECT * FROM Note WHERE user= :user ORDER BY date DESC")
    public List<NoteEntity> getNotesByUser(String user);

    @Query("SELECT * FROM Note WHERE id= :id")
    public List<NoteEntity> getNoteById(Long id);


    @Query("SELECT * FROM Note WHERE title LIKE :query OR detail LIKE :query ORDER BY date DESC")
    public List<NoteEntity> searchNote(String query);

    @Query("SELECT COUNT(*) FROM Note")
    public int getCount();



}

