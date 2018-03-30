package com.example.merve.butterknife;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

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
    CursorAdapter cursorAdapter;
    String mCurFilter;
    @BindView(R.id.recyclerView)
    RecyclerView rvMain;
    @BindView(R.id.imgNoteAdd)
    ImageButton imgNoteAdd;
    @BindView(R.id.searchView)
    SearchView searchView;
    RecyclerView rvMain2;
    @BindView(R.id.search)
    LinearLayout search;
    private NoteAdapter mAdapter;
    private MediaAdapter mAdapter2;
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
        //rvMain.setAdapter(mAdapter2);
        // inflater.inflate(R.xml.search,xml);

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

                final List<Note> list = MainActivity.database.notedao().getNotesByUser(u.getUsername());


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
    public void onClick(View view, int position) {

        Note item = ((NoteAdapter) rvMain.getAdapter()).list.get(position);
        Intent i = new Intent(NoteActivity.this, DetailActivity.class);
        i.putExtra("item", item);
        startActivity(i);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Note> list = MainActivity.database.notedao().searchNote("%" + query + "%");
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

                final List<Note> list = MainActivity.database.notedao().searchNote("%" + newText + "%");
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
}
