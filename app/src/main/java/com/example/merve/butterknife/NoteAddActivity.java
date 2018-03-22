package com.example.merve.butterknife;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merve.butterknife.db.Entity.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

public class NoteAddActivity extends AppCompatActivity {
    String millisstring;
    int millisec = 0, sec = 0, min = 0, hour = 0;

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.edtDetail)
    EditText edtDetail;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnRenk)
    Button btnRenk;
    @BindView(R.id.btnMedia)
    Button btnMedia;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private SharedPreferences sharedPreferences;
    private static final int PICK_FROM_GALLERY = 1;
    public int colorr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setTitle("New Note");
            setSupportActionBar(toolbar);

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        if(btnRenk!=null){
            btnRenk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ColorPicker colorPicker = new ColorPicker(NoteAddActivity.this);
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

                                    colorr=color;
                                }

                                @Override
                                public void onCancel() {

                                }
                            }).show();
                }

            });
        }
        if(btnMedia!=null) {
            btnMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,0);
                }
            });

        }
    }


    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        if (!edtDetail.getText().toString().isEmpty() && edtDetail.getText().toString().length() < 121 && edtTitle.getText().toString().length() < 15 && !edtTitle.getText().toString().isEmpty()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NoteEntity note = new NoteEntity();
                        note.setUser(sharedPreferences.getString("username", ""));
                        note.setTitle(edtTitle.getText().toString());
                        note.setDetail(edtDetail.getText().toString());


                        note.setColors(colorr);
//                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                        long milliSeconds=1390361405210L;
                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTimeInMillis(milliSeconds);
//                        Log.d("time",formatter.format(calendar.getTime()));
//                        System.out.println(formatter.format(date));

                        note.setDate(calendar.getTimeInMillis());


                        MainActivity.database.notedao().InsertNote(note);

                    } catch (Exception e) {
                        Log.e("hata", e.toString());
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Intent i = new Intent(getApplicationContext(), NoteActivity.class);
                                i.putExtra("detail", edtDetail.getText());
                                i.putExtra("title", edtTitle.getText());
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


}
