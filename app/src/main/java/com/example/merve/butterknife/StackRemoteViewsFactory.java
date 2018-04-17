package com.example.merve.butterknife;

import android.appwidget.AppWidgetManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.merve.butterknife.db.AppDatabase;
import com.example.merve.butterknife.db.Entity.Note;
import com.example.merve.butterknife.model.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by merve on 10.04.2018.
 */

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    User u = new User();
    private List<Note> noteList = new ArrayList<>();
    private Context context;
    private int appWidgetId;
    private AppDatabase database;
    private NoteAdapter mAdapter;
    private SharedPreferences sharedPreferences;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        u.setUsername(sharedPreferences.getString("username", ""));
        database = Room.databaseBuilder(context, AppDatabase.class, "NoteDB").build();
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

    }

    private void getNotes() {
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Note> notes = database.notedao().getNotesByUser(u.getUsername());
                if (notes.size() > 0)
                    noteList.addAll(notes);
                latch.countDown();
            }
        }).start();
        try {
            latch.await();
        } catch (Exception ex) {

        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        getNotes();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (noteList != null) {
            return noteList.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_note_item);
        if (noteList != null && getCount() > position) {
            Note note = noteList.get(position);
            Date datee = new Date();
            String stringDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(datee);
            if (note != null) {
                remoteView.setTextViewText(R.id.widgettxtTitle, note.noteEntity.getTitle());
                remoteView.setTextViewText(R.id.widgettxtDetail, note.noteEntity.getDetail());
                remoteView.setTextViewText(R.id.widgettxtDate, stringDate);
            }
            Bundle extras = new Bundle();
            extras.putInt(ExampleAppWidgetProvider.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent(context, DetailActivity.class);
            fillInIntent.putExtras(extras);
            remoteView.setOnClickFillInIntent(R.id.noteItem, fillInIntent);
        }
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
