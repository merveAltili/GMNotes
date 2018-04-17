package com.example.merve.butterknife;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.hololo.library.photoviewer.PhotoViewer;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
    final User u = new User();
    final List<MediaEntity> list2 = new ArrayList<>();
    public int colorr = 0;
    public MediaAdapter mAdapter2;
    NoteEntity noteEntity = new NoteEntity();
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
    ProgressDialog progress;
    private AppDatabase database;
    private SharedPreferences sharedPreferences;
    private LinearLayoutManager LinearLayoutManager;
    private TextWatcher tw = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!edtDetail.getText().toString().isEmpty() && !edtTitle.getText().toString().isEmpty()) {
                toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
                submitAddNote.setVisibility(View.VISIBLE);
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

        edtDetail.setCursorVisible(false);
        edtTitle.setCursorVisible(false);
        progress = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        submitAddNote.setVisibility(GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mod) {
                    v = getCurrentFocus();
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    finish();
                } else {
                    new AlertDialog.Builder(NoteAddActivity.this)
                            .setTitle("Sil")
                            .setMessage("Silmek istediğinizden emin misiniz?")
                            .setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    edtDetail.setText(null);
                                    edtTitle.setText(null);
                                    edtTitle.requestFocusFromTouch();
                                    edtDetail.setHint("Not giriniz");
                                    edtTitle.setHint("Başlık giriniz");
                                    list2.clear();
                                    mAdapter2.setList2(list2);
                                    crdview.setCardBackgroundColor(Color.WHITE);
                                    noteAddCardV.setCardBackgroundColor(Color.WHITE);
                                    noteAddRecyc.setBackgroundColor(Color.WHITE);
                                    mod = false;
                                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                                    submitAddNote.setVisibility(GONE);

                                }
                            }).setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
            }
        });
        toolbar.setTitle("");
        database = Room.databaseBuilder(this, AppDatabase.class, "NoteDB").build();
        noteAddRecyc.setHasFixedSize(true);
        LinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        u.setUsername(sharedPreferences.getString("username", ""));
        mAdapter2 = new MediaAdapter(this, -1);
        noteAddRecyc.setAdapter(mAdapter2);
        noteAddRecyc.setLayoutManager(LinearLayoutManager);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        noteAddCardV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
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
                            .setTitle("Renk seçiniz")
                            .setColors(colors)
                            .setColumns(5)
                            .setRoundColorButton(true)
                            .addListenerButton("", buton3, new ColorPicker.OnButtonListener() {
                                @Override
                                public void onClick(View v, int position, int color) {
                                }
                            })
                            .addListenerButton("VAZGEÇ", buton2, new ColorPicker.OnButtonListener() {
                                @Override
                                public void onClick(View v, int position, int color) {
                                    colorPicker.dismissDialog();
                                }
                            })
                            .addListenerButton("TAMAM", buton, new ColorPicker.OnButtonListener() {
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
                    colorPicker.getmDialog().setCancelable(false);

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
        edtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAddRecyc.setAdapter(mAdapter2);
                edtTitle.setEnabled(true);
                edtTitle.setFocusableInTouchMode(true);
                edtTitle.setFocusable(true);
                edtTitle.setCursorVisible(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                edtTitle.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                edtTitle.setSelection(edtTitle.getText().length(), edtTitle.getText().length());
            }
        });
        edtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAddRecyc.setAdapter(mAdapter2);
                edtDetail.setEnabled(true);
                edtDetail.setFocusableInTouchMode(true);
                edtDetail.setFocusable(true);
                edtDetail.setCursorVisible(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                edtDetail.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                edtDetail.setSelection(edtDetail.getText().length(), edtDetail.getText().length());
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    public void showProgressBar() {
        progress.setMessage("Yükleniyor..");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    @Override
    public void onBackPressed() {
        if (!edtTitle.getText().toString().isEmpty() && !edtDetail.getText().toString().isEmpty()) {
            if (mod) {
                showProgressBar();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            noteEntity.setUser(sharedPreferences.getString("username", ""));
                            noteEntity.setTitle(edtTitle.getText().toString());
                            noteEntity.setDetail(edtDetail.getText().toString());
                            noteEntity.setColors(colorr);
                            Calendar calendar = Calendar.getInstance();
                            noteEntity.setDate(calendar.getTimeInMillis());
                            database.notedao().InsertNote(noteEntity);
                            Long id = database.notedao().getLastNote().getId();

                            for (MediaEntity mediaEntity : list2) {
                                mediaEntity.setNoteId(id);
                                database.mediaDao().InsertMedia(mediaEntity);
                            }
                            progress.dismiss();

                        } catch (Exception e) {
                            Log.e("hata editnotesave2", e.toString());
                        }

                    }
                }).start();
            }

            super.onBackPressed();
        } else {

            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Integer temp = 0;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri uri = getImageUri(getApplicationContext(), photo);

            final MediaEntity mediaEntity2 = new MediaEntity();
            mediaEntity2.setPath(getRealPathFromURI(uri));

            list2.add(mediaEntity2);
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
            temp = size;
            if (size > 0) {
                toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
                submitAddNote.setVisibility(View.VISIBLE);
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
        if (cursor == null) {
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
        if (!mAdapter2.selectedMod) {
            final List<MediaEntity> item = ((MediaAdapter) noteAddRecyc.getAdapter()).list2;


            ArrayList<File> fileList = new ArrayList<File>();
            for (int i = 0; i < item.size(); i++) {

                fileList.add(new File(item.get(i).getPath()));
            }

            new PhotoViewer.Builder(view.getContext())
                    .file(fileList)
                    .placeHolder(R.drawable.ic_image_black_24dp)
                    .position(position)

                    .build()
                    .show();
        }
    }


    @Override
    public void onClickCardView(View view, int position) {
    }

    @Override
    public void onLongClick(View view, int position) {
    }

    @Override
    public void onLongClickMedia(View view, int position) {


        if (mAdapter2.selectedMod) {
            mod = true;
            mAdapter2.closeSelectedMod();

        } else
            mAdapter2.startSelectedMod();

    }


    @OnClick(R.id.submitAddNote)
    public void onViewClicked() {
        if (!edtDetail.getText().toString().isEmpty() && !edtTitle.getText().toString().isEmpty()) {
            showProgressBar();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        noteEntity.setUser(sharedPreferences.getString("username", ""));
                        noteEntity.setTitle(edtTitle.getText().toString());
                        noteEntity.setDetail(edtDetail.getText().toString());
                        noteEntity.setColors(colorr);
                        Calendar calendar = Calendar.getInstance();
                        noteEntity.setDate(calendar.getTimeInMillis());
                        database.notedao().InsertNote(noteEntity);
                        Long id = database.notedao().getLastNote().getId();
                        for (MediaEntity mediaEntity : list2) {
                            mediaEntity.setNoteId(id);
                            database.mediaDao().InsertMedia(mediaEntity);
                        }
                        progress.dismiss();
                    } catch (Exception e) {
                        Log.e("hata1", e.toString());
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                finish();
                            } catch (Exception e) {

                                Log.e("hata2", e.toString());
                            }
                        }
                    });
                }
            }).start();
        } else if (edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Notunuza başlık girmediniz ", Toast.LENGTH_SHORT).show();
        } else if (edtDetail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Not girmediniz ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(View view, final int position) {
        final MediaEntity item = mAdapter2.list2.get(position);
        new AlertDialog.Builder(view.getContext())
                .setTitle("Sil")
                .setMessage("Silmek istediğinizden emin misiniz? ")
                .setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    database.mediaDao().deleteMediaById(item.getId());
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {

                                            list2.remove(item);
                                            mAdapter2.setList2(list2);
                                        }
                                    });
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

}