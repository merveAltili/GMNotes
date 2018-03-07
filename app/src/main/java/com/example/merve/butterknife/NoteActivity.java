package com.example.merve.butterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends AppCompatActivity {

    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imgNoteAdd)
    ImageButton imgNoteAdd;
    @BindView(R.id.listNote)
    ListView listNote;
    @BindView(R.id.searchView)
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        MenuInflater inflater=getMenuInflater();
       // inflater.inflate(R.xml.search,xml);

    }

    @OnClick(R.id.imgNoteAdd)
    public void onViewClicked() {
        Intent i = new Intent(this, NoteAddActivity.class);
        startActivity(i);
    }
}
