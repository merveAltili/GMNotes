package com.example.merve.butterknife;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,AdapterOnCLickListener {

    final User u = new User();

    CursorAdapter cursorAdapter;
    String mCurFilter;
    @BindView(R.id.recyclerView)
    RecyclerView rvMain;

    private NoteAdapter mAdapter;


    @BindView(R.id.imgNoteAdd)
    ImageButton imgNoteAdd;

    @BindView(R.id.searchView)
    SearchView searchView;



    private SharedPreferences sharedPreferences;

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,

    };
    private StaggeredGridLayoutManager layoutManager;
    RecyclerView rvMain2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);



        rvMain.setHasFixedSize(true);

        layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        u.setUsername(sharedPreferences.getString("username", ""));


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


                final NoteAdapter adapter = new NoteAdapter(MainActivity.database.notedao().getNotesByUser(u.getUsername()),NoteActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        rvMain.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }
                });

            }
        }).start();
    }

    public void itemOnclick(View view){
        Intent i=new Intent(NoteActivity.this,DetailActivity.class);
        startActivity(i);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri;
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,
                    Uri.encode(mCurFilter));
        } else {
            baseUri = ContactsContract.Contacts.CONTENT_URI;
        }
        String select = "(" + ContactsContract.Contacts.DISPLAY_NAME + "like '%'" + mCurFilter + "%'" + ")";
        System.out.println(select);
        return new CursorLoader(this, baseUri, CONTACTS_SUMMARY_PROJECTION, select, null,
                ContactsContract.Contacts.DISPLAY_NAME + "ASC");

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (cursorAdapter != null)
            cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (cursorAdapter != null)
            cursorAdapter.swapCursor(null);
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

//SORUN-------------------------------------------------------------------------------->>>>>>>>>>>>>>><
    @Override
    public void onClick(View view, int position) {
        NoteEntity item= ((NoteAdapter) rvMain.getAdapter()).list.get(position);
        Intent i=new Intent(NoteActivity.this,DetailActivity.class);

        i.putExtra("item",item.getId());
        startActivity(i);

    }
}
