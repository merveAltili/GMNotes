package com.example.merve.butterknife;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merve.butterknife.db.AppDatabase;
import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

import static android.view.View.GONE;

public class NoteAddActivity extends AppCompatActivity implements AdapterOnCLickListener {


    private static final int CAMERA_REQUEST = 1888;
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
    @BindView(R.id.txtView)
    TextView txtView;
    @BindView(R.id.txtMediaNote)
    TextView txtMediaNote;
    @BindView(R.id.submitAddNote)
    ImageButton submitAddNote;
    @BindView(R.id.rv_content)
    LinearLayout rvContent;
    @BindView(R.id.btn)
    Button btn;
    Integer size = 0;

    boolean mod = false;
    private AppDatabase database;
    private SharedPreferences sharedPreferences;
    private LinearLayoutManager LinearLayoutManager;

    private TextWatcher tw = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!edtDetail.getText().toString().isEmpty() && !edtTitle.getText().toString().isEmpty()) {
                toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
                submitAddNote.setVisibility(View.VISIBLE);
                submitAddNote.setColorFilter(Color.GREEN);
                mod = true;
            } else {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                submitAddNote.setVisibility(GONE);
            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        submitAddNote.setVisibility(GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mod) {

                    finish();
                } else {
                    edtDetail.setText(null);
                    edtTitle.setText(null);
                    edtTitle.requestFocusFromTouch();
                    edtDetail.setHint("Note");
                    edtTitle.setHint("Type your note");
                    crdview.setCardBackgroundColor(Color.WHITE);
                    noteAddCardV.setVisibility(GONE);
                    mod = false;
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                    submitAddNote.setVisibility(GONE);


                }

            }
        });

        database = Room.databaseBuilder(this, AppDatabase.class, "NoteDB").build();
        noteAddRecyc.setHasFixedSize(true);
        LinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        u.setUsername(sharedPreferences.getString("username", ""));
        mAdapter2 = new MediaAdapter(this, -1);
        noteAddRecyc.setAdapter(mAdapter2);
        noteAddRecyc.setLayoutManager(LinearLayoutManager);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        if (getSupportActionBar() != null) {
//
//            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//            setSupportActionBar(toolbar);
//
//        }



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
                    colorPicker.disableDefaultButtons(true);
                    Button buton = new Button(getApplicationContext());
                    buton.setTextColor(Color.BLUE);
                    buton.setBackgroundColor(Color.WHITE);
                    Button buton2 = new Button(getApplicationContext());
                    buton2.setBackgroundColor(Color.WHITE);
                    Button buton3 = new Button(getApplicationContext());
                    buton3.setBackgroundColor(Color.WHITE);
                    colorPicker
                            .setColorButtonTickColor(Color.WHITE)
                            .setDefaultColorButton(Color.parseColor("#f84c44"))
                            .setColors(colors)
                            .setColumns(5)
                            .setRoundColorButton(true)
                            .addListenerButton("", buton3, new ColorPicker.OnButtonListener() {
                                @Override
                                public void onClick(View v, int position, int color) {


                                }
                            })
                            .addListenerButton("Cancel", buton2, new ColorPicker.OnButtonListener() {
                                @Override
                                public void onClick(View v, int position, int color) {

                                    colorPicker.dismissDialog();

                                }
                            })

                            .addListenerButton("OK", buton, new ColorPicker.OnButtonListener() {
                                @Override
                                public void onClick(View v, int position, int color) {
                                    Log.d("position", "" + position);
                                    colorr = color;
                                    crdview.setCardBackgroundColor(color);
                                    noteAddCardV.setCardBackgroundColor(color);
                                    noteAddRecyc.setBackgroundColor(color);
                                    colorPicker.dismissDialog();
                                }
                            })
                            .show();
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
        edtDetail.addTextChangedListener(tw);
        edtTitle.addTextChangedListener(tw);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri uri = getImageUri(getApplicationContext(), photo);


            MediaEntity mediaEntity = new MediaEntity();
            mediaEntity.setPath(getRealPathFromURI(uri));

            list2.add(mediaEntity);
            mAdapter2.setList2(list2);
            txtMediaNote.setVisibility(GONE);
            noteAddCardV.setVisibility(View.VISIBLE);

        } else if (resultCode == RESULT_OK) {
            MediaEntity mediaEntity = new MediaEntity();
            final Uri imageUri = data.getData();
            mediaEntity.setPath(getRealPathFromURI(imageUri));

            list2.add(mediaEntity);
            mAdapter2.setList2(list2);
            txtMediaNote.setVisibility(GONE);
            noteAddCardV.setVisibility(View.VISIBLE);
            size = list2.size();
            if (size > 0) {
                toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
                submitAddNote.setVisibility(View.VISIBLE);
                submitAddNote.setColorFilter(Color.GREEN);
                mod = true;
            }


        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
    public void onClick(View view, int position) {

    }

    @Override
    public void onClickMedia(View view, int position) {

    }


    @Override
    public void onClickCardView(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onLongClickMedia(View view, int position) {

    }

    @OnClick(R.id.submitAddNote)
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


                        database.notedao().InsertNote(noteEntity);
                        Long id = database.notedao().getLastNote().getId();
                        for (MediaEntity mediaEntity : list2) {
                            mediaEntity.setNoteId(id);

                            database.mediaDao().InsertMedia(mediaEntity);
                        }


                    } catch (Exception e) {
                        Log.e("hata1", e.toString());
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Intent i = new Intent(getApplicationContext(), NoteActivity.class);
                                i.putExtra("detail", edtDetail.getText());
                                i.putExtra("title", edtTitle.getText());
                                startActivity(i);
                                finish();
                            } catch (Exception e) {

                                Log.e("hata2", e.toString());
                            }

                        }
                    });

                }
            }).start();


        } else if (edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title can not go blank ", Toast.LENGTH_SHORT).show();
        } else if (edtDetail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Detail can not go blank ", Toast.LENGTH_SHORT).show();
        }
    }




}