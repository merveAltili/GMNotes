package com.example.merve.butterknife.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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

    @Query("SELECT * FROM Media WHERE NoteId= :NoteId")
    public List<Note> getMediaByNoteId(Long NoteId);


}
