package com.example.merve.butterknife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

public class NoteAddActivity extends AppCompatActivity {
    private static final int PICK_FROM_GALLERY = 1;
    final User u = new User();
    final List<MediaEntity> list2 = new ArrayList<>();
    public int colorr = 0;
    public MediaAdapter mAdapter2;
    NoteEntity noteEntity = new NoteEntity();
    String millisstring;
    int millisec = 0, sec = 0, min = 0, hour = 0;
    @BindView(R.id.edtTitle)
    EditText edtTitle;
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
    @BindView(R.id.crdview)
    CardView crdview;
    @BindView(R.id.noteAddRecyc)
    RecyclerView noteAddRecyc;
    @BindView(R.id.noteAddCardV)
    CardView noteAddCardV;
    private SharedPreferences sharedPreferences;
    private LinearLayoutManager LinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        ButterKnife.bind(this);

        noteAddRecyc.setHasFixedSize(true);

        LinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        u.setUsername(sharedPreferences.getString("username", ""));
        mAdapter2 = new MediaAdapter();
        noteAddRecyc.setAdapter(mAdapter2);
        noteAddRecyc.setLayoutManager(LinearLayoutManager);
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
        if (btnRenk != null) {
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
                                    Log.d("position", "" + position);
                                    colorr = color;
                                    crdview.setCardBackgroundColor(color);
                                }

                                @Override
                                public void onCancel() {

                                }
                            }).show();
                }

            });
        }
        if (btnMedia != null) {
            btnMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);

                }
            });

        }


    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            MediaEntity mediaEntity = new MediaEntity();
            final Uri imageUri = data.getData();
            mediaEntity.setPath(getRealPathFromURI(imageUri));

            list2.add(mediaEntity);
            mAdapter2.setList2(list2);

        }

    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        if (!edtDetail.getText().toString().isEmpty() && !edtTitle.getText().toString().isEmpty()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {


                        noteEntity.setUser(sharedPreferences.getString("username", ""));
                        noteEntity.setTitle(edtTitle.getText().toString());
                        noteEntity.setDetail(edtDetail.getText().toString());


                        noteEntity.setColors(colorr);
//                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                        long milliSeconds=1390361405210L;
                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTimeInMillis(milliSeconds);
//                        Log.d("time",formatter.format(calendar.getTime()));
//                        System.out.println(formatter.format(date));

                        noteEntity.setDate(calendar.getTimeInMillis());


                        MainActivity.database.notedao().InsertNote(noteEntity);
                        for (MediaEntity mediaEntity : list2) {
                            mediaEntity.setNoteId(Long.valueOf(MainActivity.database.notedao().getCount()));

                            MainActivity.database.mediaDao().InsertMedia(mediaEntity);
                        }




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