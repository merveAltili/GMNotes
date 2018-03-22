package com.example.merve.butterknife;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.merve.butterknife.db.Entity.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

public class DetailActivity extends AppCompatActivity {


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
    public int colorr = 0;
    private SharedPreferences sharedPreferences;
    String title;

    NoteEntity entity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        entity = bundle.getParcelable("item");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setTitle(entity.getTitle().toString());
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        detailDetail.setText(entity.getDetail().toString());

        detailDetail.setTextColor(Color.BLACK);


    }

    @OnClick(R.id.btnSave2)
    public void onViewClicked() {
        if (!detailDetail.getText().toString().isEmpty()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        entity.setUser(sharedPreferences.getString("username", ""));
                        entity.setDetail(detailDetail.getText().toString());
                        entity.setTitle(toolbar.getTitle().toString());

                        entity.setColors(colorr);

                        Calendar calendar = Calendar.getInstance();
//
                        entity.setDate(calendar.getTimeInMillis());


                        MainActivity.database.notedao().UpdateNote(entity);

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
        detailDetail.setEnabled(true);
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

                                    entity.setUser(sharedPreferences.getString("username", ""));
                                    entity.setDetail(detailDetail.getText().toString());
                                    entity.setTitle(toolbar.getTitle().toString());

                                    entity.setColors(colorr);

                                    Calendar calendar = Calendar.getInstance();
//
                                    entity.setDate(calendar.getTimeInMillis());


                                    MainActivity.database.notedao().DeleteNote(entity);


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
