package com.example.merve.butterknife.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by merve on 23.03.2018.
 */
@Entity(tableName = "Media", foreignKeys = {@ForeignKey(entity = NoteEntity.class, parentColumns = {"id"}, childColumns = {"NoteId"}, onDelete = ForeignKey.CASCADE)})
public class MediaEntity implements Parcelable {
//    public MediaEntity(Long id, Long noteId, String path) {
//        this.id = id;
//        NoteId = noteId;
//        Path = path;
//    }

    public static final Parcelable.Creator<MediaEntity> CREATOR = new Parcelable.Creator<MediaEntity>() {
        @Override
        public MediaEntity createFromParcel(Parcel source) {
            return new MediaEntity(source);
        }

        @Override
        public MediaEntity[] newArray(int size) {
            return new MediaEntity[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long NoteId;
    private String Path;

    public MediaEntity() {
    }

    protected MediaEntity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.NoteId = (Long) in.readValue(Long.class.getClassLoader());
        this.Path = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.NoteId);
        dest.writeString(this.Path);
    }
}
