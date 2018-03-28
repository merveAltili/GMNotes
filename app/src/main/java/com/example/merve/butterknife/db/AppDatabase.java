package com.example.merve.butterknife.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.db.dao.MediaDao;
import com.example.merve.butterknife.db.dao.NoteDao;
/**
 * Created by merve on 08.03.2018.
 */
@Database(entities = {
        NoteEntity.class,
        MediaEntity.class
}, version = 21)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao notedao();

    public abstract MediaDao mediaDao();
}
