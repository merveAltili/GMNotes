package com.example.merve.butterknife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.example.merve.butterknife.db.Entity.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

public class DetailActivity extends AppCompatActivity {


    final List<MediaEntity> list2 = new ArrayList<>();
    public int colorr = 0;
    public Note entity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.detailDetail)
    EditText detailDetail;
    @BindView(R.id.detailEdit)
    Button detailEdit;
    @BindView(R.id.detailColor)
    Button detailColor;
    @BindView(R.id.detailSil)
    Button detailSil;
    @BindView(R.id.btnSave2)
    Button btnSave2;
    String title;
    @BindView(R.id.detailText)
    EditText detailText;
    @BindView(R.id.crdview)
    CardView crdview;
    @BindView(R.id.txtView)
    TextView txtView;
    @BindView(R.id.noteAddRecyc2)
    RecyclerView noteAddRecyc2;
    @BindView(R.id.noteAddCardV2)
    CardView noteAddCardV2;
    MediaEntity mediaEntity = new MediaEntity();
    List<Note> list = new ArrayList<Note>();
    private SharedPreferences sharedPreferences;
    private MediaAdapter mAdapter = new MediaAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noteAddRecyc2.setAdapter(mAdapter);

        Bundle bundle = getIntent().getExtras();

        entity = bundle.getParcelable("item");


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DetailActivity.this, NoteActivity.class);
                    startActivity(i);
                    finish();

                }
            });
            toolbar.setTitle("Detail");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        detailDetail.setText(entity.noteEntity.getDetail());

        detailDetail.setTextColor(Color.BLACK);
        if (entity.noteEntity.getColors() == 0) {
            crdview.setCardBackgroundColor(Color.WHITE);
            noteAddCardV2.setCardBackgroundColor(Color.WHITE);
            noteAddRecyc2.setBackgroundColor(Color.WHITE);
        } else {
            crdview.setCardBackgroundColor(entity.noteEntity.getColors());
            noteAddCardV2.setCardBackgroundColor(entity.noteEntity.getColors());
            noteAddRecyc2.setBackgroundColor(entity.noteEntity.getColors());
        }


        detailText.setText(entity.noteEntity.getTitle());


        mAdapter.setList2(entity.mediaAdapterList);



    }

    @OnClick(R.id.btnSave2)
    public void onViewClicked() {
        if (!detailDetail.getText().toString().isEmpty()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        entity.noteEntity.setUser(sharedPreferences.getString("username", ""));
                        entity.noteEntity.setDetail(detailDetail.getText().toString());
                        entity.noteEntity.setTitle(detailText.getText().toString());

                        if (colorr == 0) {
                            entity.noteEntity.setColors(entity.noteEntity.getColors());
                        } else
                            entity.noteEntity.setColors(colorr);

                        Calendar calendar = Calendar.getInstance();
//
                        entity.noteEntity.setDate(calendar.getTimeInMillis());


                        MainActivity.database.notedao().UpdateNote(entity.noteEntity);

                    } catch (Exception e) {
                        Log.e("hata", e.toString());
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Intent i = new Intent(getApplicationContext(), NoteActivity.class);
                                i.putExtra("detail", detailDetail.getText());
                                startActivity(i);
                                finish();
                            } catch (Exception e) {

                                Log.e("hata", e.toString());
                            }

                        }
                    });

                }
            }).start();


        } else {
            Toast.makeText(this, "Lütfen geçerli bir değer giriniz", Toast.LENGTH_SHORT).show();
        }

    }


    public void onClickEdit(View view) {
        mAdapter.asd();
        noteAddRecyc2.setAdapter(mAdapter);
        detailDetail.setEnabled(true);
        detailDetail.setFocusableInTouchMode(true);
        detailDetail.setFocusable(true);
        detailText.setEnabled(true);
        detailText.setFocusableInTouchMode(true);
        detailText.setFocusable(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        detailText.requestFocus();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        detailText.setSelection(detailText.getText().length(), detailText.getText().length());


    }

    public void onClickRenk(View view) {

        final ColorPicker colorPicker = new ColorPicker(DetailActivity.this);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#FFCDD2");
        colors.add("#D1C4E9");
        colors.add("#B3E5FC");
        colors.add("#C8E6C9");
        colors.add("#FFE0B2");
        colors.add("#F9FBE7");
        colors.add("#F5F5F5");
        colors.add("#E0F2F1");

        colorPicker
                .setDefaultColorButton(Color.parseColor("#f84c44"))
                .setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        Log.d("position", "" + position);// will be fired only when OK button was tapped
                        colorr = color;
                        crdview.setCardBackgroundColor(color);
                        noteAddRecyc2.setBackgroundColor(color);
                        noteAddCardV2.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

    public void onClickSil(View view) {
        new AlertDialog.Builder(DetailActivity.this)
                .setTitle("Delete")
                .setMessage("Are you want to delete ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    entity.noteEntity.setUser(sharedPreferences.getString("username", ""));
                                    entity.noteEntity.setDetail(detailDetail.getText().toString());
                                    entity.noteEntity.setTitle(detailText.getText().toString());

                                    entity.noteEntity.setColors(colorr);

                                    Calendar calendar = Calendar.getInstance();
//
                                    entity.noteEntity.setDate(calendar.getTimeInMillis());


                                    MainActivity.database.notedao().DeleteNote(entity.noteEntity);


                                } catch (Exception e) {
                                    Log.e("hata", e.toString());
                                }

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try {
                                            Intent i = new Intent(getApplicationContext(), NoteActivity.class);
                                            i.putExtra("detail", detailDetail.getText());
                                            startActivity(i);
                                            finish();
                                        } catch (Exception e) {

                                            Log.e("hata", e.toString());
                                        }

                                    }
                                });

                            }
                        }).start();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


    }

}