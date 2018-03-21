package com.example.merve.butterknife.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.db.dao.NoteDao;
/**
 * Created by merve on 08.03.2018.
 */
@Database(entities = {
        NoteEntity.class
},version = 10)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao notedao();
}
