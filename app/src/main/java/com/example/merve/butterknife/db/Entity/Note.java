package com.example.merve.butterknife.db.Entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by merve on 26.03.2018.
 */

public class Note implements Parcelable {
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    @Embedded
    public NoteEntity noteEntity;
    @Relation(parentColumn = "id", entityColumn = "NoteId", entity = MediaEntity.class)
    public List<MediaEntity> mediaAdapterList;

    public Note() {
    }

    protected Note(Parcel in) {
        this.noteEntity = in.readParcelable(NoteEntity.class.getClassLoader());
        this.mediaAdapterList = new ArrayList<MediaEntity>();
        in.readList(this.mediaAdapterList, MediaEntity.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.noteEntity, flags);
        dest.writeList(this.mediaAdapterList);
    }
}
