package com.example.merve.butterknife.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.db.User;

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

    @Query("SELECT * FROM Note WHERE user= :user")
    public List<NoteEntity> getNotesByUser(Integer user);

}

