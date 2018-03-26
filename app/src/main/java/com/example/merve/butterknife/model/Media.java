package com.example.merve.butterknife.model;

import android.arch.persistence.room.PrimaryKey;

/**
 * Created by merve on 26.03.2018.
 */

public class Media {
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
