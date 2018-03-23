package com.example.merve.butterknife.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by merve on 23.03.2018.
 */
@Entity(tableName = "Media", foreignKeys = {@ForeignKey(entity = NoteEntity.class, parentColumns = {"id"}, childColumns = {"NoteId"}, onDelete = ForeignKey.CASCADE)})
public class MediaEntity {
//    public MediaEntity(Long id, Long noteId, String path) {
//        this.id = id;
//        NoteId = noteId;
//        Path = path;
//    }

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long NoteId;
    private String Path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoteId() {
        return NoteId;
    }

    public void setNoteId(Long noteId) {
        NoteId = noteId;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

}
