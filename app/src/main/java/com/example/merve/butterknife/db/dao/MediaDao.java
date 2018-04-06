package com.example.merve.butterknife.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.example.merve.butterknife.db.Entity.Note;

import java.util.List;

/**
 * Created by merve on 23.03.2018.
 */
@Dao
public interface MediaDao {
    @Insert
    public void InsertMedia(MediaEntity media);

    @Delete
    public void DeleteMedia(MediaEntity media);

    @Update
    public void UpdateMedia(MediaEntity media);

    @Query("SELECT * FROM Media WHERE NoteId= :NoteId")
    public List<Note> getMediaByNoteId(Long NoteId);

    @Query("SELECT * FROM Media WHERE id=:id")
    public List<Note> getMediaById(Long id);

    @Query("DELETE  FROM Media WHERE NoteId=:NoteId ")
    public void deleteMediasByNoteId(Long NoteId);

    @Query("DELETE  FROM Media WHERE id=:id ")
    public void deleteMediaById(Long id);
}
