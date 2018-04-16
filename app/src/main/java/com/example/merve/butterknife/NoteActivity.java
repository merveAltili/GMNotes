package com.example.merve.butterknife;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.merve.butterknife.db.AppDatabase;
import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.example.merve.butterknife.db.Entity.Note;
import com.example.merve.butterknife.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends AppCompatActivity implements AdapterOnCLickListener, View.OnClickListener, SearchView.OnQueryTextListener {

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
    };
    final User u = new User();
    @BindView(R.id.recyclerView)
    RecyclerView rvMain;
    @BindView(R.id.imgNoteAdd)
    ImageButton imgNoteAdd;
    @BindView(R.id.searchView)
    SearchView searchView;
    RecyclerView rvMain2;
    @BindView(R.id.search)
    LinearLayout search;
    private AppDatabase database;
    private NoteAdapter mAdapter;
    private SharedPreferences sharedPreferences;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(this);
        rvMain.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        u.setUsername(sharedPreferences.getString("username", ""));
        mAdapter = new NoteAdapter(this);
        rvMain.setAdapter(mAdapter);
        database = Room.databaseBuilder(this, AppDatabase.class, "NoteDB").build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }

    @OnClick(R.id.imgNoteAdd)
    public void onViewClicked() {
        Intent i = new Intent(this, NoteAddActivity.class);
        startActivity(i);
    }

    void refreshAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Note> list = database.notedao().getNotesByUser(u.getUsername());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setList(list);
                    }
                });
            }
        }).start();
    }

    public void itemOnclick(View view) {
        Intent i = new Intent(NoteActivity.this, DetailActivity.class);
        startActivity(i);
    }

    private void commitQeury(String key) {
        Intent in = new Intent("android.intent.action.SEARCH");
        in.putExtra(SearchManager.QUERY, key);
        startActivity(in);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int positions) {
        final Note item = ((NoteAdapter) rvMain.getAdapter()).list.get(positions);
        Intent i = new Intent(NoteActivity.this, DetailActivity.class);
        i.putExtra("item", item);
        startActivity(i);
    }

    @Override
    public void onClickMedia(View view, int position) {
        final Note item = ((NoteAdapter) rvMain.getAdapter()).list.get(position);
        Intent i = new Intent(NoteActivity.this, DetailActivity.class);
        i.putExtra("item", item);
        startActivity(i);
    }


    @Override
    public void onClickCardView(View view, final int position) {
        final Note item = ((NoteAdapter) rvMain.getAdapter()).list.get(position);

        Intent i = new Intent(NoteActivity.this, DetailActivity.class);
        i.putExtra("item", item);
        startActivity(i);
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.notedao().getNoteById(item.noteEntity.getId());
            }
        }).start();
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Note> list = database.notedao().searchNote("%" + query + "%");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setList(list);
                    }
                });
            }
        }).start();
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Note> list = database.notedao().searchNote("%" + newText + "%");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter.setList(list);
                    }
                });
            }
        }).start();
        return false;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onLongClick(View view, int position) {
        final Note item = ((NoteAdapter) rvMain.getAdapter()).list.get(position);
        new AlertDialog.Builder(view.getContext())
                .setTitle("Sil")
                .setMessage("Silmek istediğinizden emin misiniz?")
                .setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    for (MediaEntity mediaEntity : item.mediaAdapterList) {
                                        database.mediaDao().DeleteMedia(mediaEntity);
                                    }
                                    database.notedao().DeleteNote(item.noteEntity);
                                    refreshAdapter();

                                } catch (Exception e) {
                                    Log.e("hata", e.toString());
                                }
                            }
                        }).start();
                    }

                })
                .setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    public void onLongClickMedia(View view, final int position) {
        final Note item = ((NoteAdapter) rvMain.getAdapter()).list.get(position);
        new AlertDialog.Builder(view.getContext())
                .setTitle("Sil")
                .setMessage("Silmek istediğinizden emin misiniz?")
                .setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    database.mediaDao().DeleteMedia(item.mediaAdapterList.get(position));


                                } catch (Exception e) {
                                    Log.e("hata", e.toString());
                                }
                            }
                        }).start();
                    }

                })
                .setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    public void onDeleteClick(View view, int position) {

    }
}