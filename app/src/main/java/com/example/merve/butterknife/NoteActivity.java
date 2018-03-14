package com.example.merve.butterknife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends AppCompatActivity {

    @BindView(R.id.imgNoteAdd)
    ImageButton imgNoteAdd;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.spinnerKategori)
    Spinner spinnerKategori;
    @BindView(R.id.activity_dashboard)
    CoordinatorLayout activityDashboard;

    private SharedPreferences sharedPreferences;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);


new Thread(new Runnable() {
    @Override
    public void run() {
        for (NoteEntity noteEntity : MainActivity.database.notedao().getKategoris()) {
            list.add(noteEntity.getKategori());
        }
        spinnerKategori.setPrompt("Lüften bir değer seçiniz");
        spinnerKategori.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, list));
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                refreshAdapter(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }
}).start();


        // inflater.inflate(R.xml.search,xml);

    }

    @OnClick(R.id.imgNoteAdd)
    public void onViewClicked() {
        Intent i = new Intent(this, NoteAddActivity.class);
        startActivity(i);
    }
    void refreshAdapter(final int i){
        new Thread(new Runnable() {
            @Override
            public void run() {

                final NoteAdapter adapter = new NoteAdapter(MainActivity.database.notedao().getNotesByKategori(list.get(i)));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });

            }
        }).start();
    }
}
