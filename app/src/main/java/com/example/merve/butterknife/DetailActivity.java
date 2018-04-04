package com.example.merve.butterknife;

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
import com.example.merve.butterknife.db.Entity.Note;
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

public class DetailActivity extends AppCompatActivity implements AdapterOnCLickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    final User u = new User();
    final List<MediaEntity> mediaEntities = new ArrayList<>();
    public int colorr = 0;
    public Note entity;
    public int size;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.detailDetail)
    EditText detailDetail;
    String title;
    @BindView(R.id.detailText)
    EditText detailText;
    @BindView(R.id.txtView)
    TextView txtView;
    @BindView(R.id.noteAddRecyc2)
    RecyclerView noteAddRecyc2;
    @BindView(R.id.noteAddCardV2)
    CardView noteAddCardV2;
    @BindView(R.id.submitEditNote)
    ImageButton submitEditNote;
    @BindView(R.id.crdview2)
    CardView crdview2;
    @BindView(R.id.rv_content)
    LinearLayout rvContent;
    @BindView(R.id.detailMedia)
    Button detailMedia;
    @BindView(R.id.detailCamera)
    Button detailCamera;
    @BindView(R.id.detailColor)
    Button detailColor;
    private AppDatabase database;
    private MediaAdapter mAdapter;
    private android.support.v7.widget.LinearLayoutManager LinearLayoutManager;
    private SharedPreferences sharedPreferences;
    private TextWatcher tw = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            size = (int) detailDetail.getTextSize();
            if (!detailText.getText().toString().isEmpty() && !detailDetail.getText().toString().isEmpty()) {

                submitEditNote.setColorFilter(Color.GREEN);
            } else {
                submitEditNote.setColorFilter(Color.WHITE);
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

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setSupportActionBar(toolbar);
        database = Room.databaseBuilder(this, AppDatabase.class, "NoteDB").build();
        mAdapter = new MediaAdapter(this);
        noteAddRecyc2.setAdapter(mAdapter);

        LinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Bundle bundle = getIntent().getExtras();

        entity = bundle.getParcelable("item");
        mediaEntities.addAll(entity.mediaAdapterList);
        mAdapter.setList2(mediaEntities);
        u.setUsername(sharedPreferences.getString("username", ""));
        if (entity.mediaAdapterList.size() == 0) {
            noteAddCardV2.setVisibility(View.GONE);
        } else
            noteAddCardV2.setVisibility(View.VISIBLE);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
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
            crdview2.setCardBackgroundColor(Color.WHITE);
            noteAddCardV2.setCardBackgroundColor(Color.WHITE);
            noteAddRecyc2.setBackgroundColor(Color.WHITE);
        }
        if (entity.noteEntity.getColors() != 0) {
            crdview2.setCardBackgroundColor(entity.noteEntity.getColors());
            noteAddCardV2.setCardBackgroundColor(entity.noteEntity.getColors());
            noteAddRecyc2.setBackgroundColor(entity.noteEntity.getColors());
        }
        detailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAddRecyc2.setAdapter(mAdapter);
                detailText.setEnabled(true);
                detailText.setFocusableInTouchMode(true);
                detailText.setFocusable(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                detailText.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                detailText.setSelection(detailText.getText().length(), detailText.getText().length());
            }
        });
        detailDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.asd();
                noteAddRecyc2.setAdapter(mAdapter);
                detailDetail.setEnabled(true);
                detailDetail.setFocusableInTouchMode(true);
                detailDetail.setFocusable(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                detailText.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                detailText.setSelection(detailText.getText().length(), detailText.getText().length());
            }
        });


        detailText.setText(entity.noteEntity.getTitle());

        if (detailMedia != null) {
            detailMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);

                }
            });

        }
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        detailText.addTextChangedListener(tw);
        detailDetail.addTextChangedListener(tw);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri uri = getImageUri(getApplicationContext(), photo);

            MediaEntity mediaEntity = new MediaEntity();
            mediaEntity.setPath(getRealPathFromURI(uri));

            mediaEntities.add(mediaEntity);
            mAdapter.setList2(mediaEntities);

        } else if (resultCode == RESULT_OK) {
            MediaEntity mediaEntity = new MediaEntity();
            final Uri imageUri = data.getData();
            mediaEntity.setPath(getRealPathFromURI(imageUri));

            mediaEntities.add(mediaEntity);
            mAdapter.setList2(mediaEntities);
        }

    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @OnClick(R.id.submitEditNote)
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

                        database.notedao().UpdateNote(entity.noteEntity);
                        Long id = database.notedao().getLastNote().getId();
                        database.mediaDao().deleteMediasByNoteId(id);
                        for (MediaEntity mediaEntity : mediaEntities) {
                            mediaEntity.setNoteId(id);
                            database.mediaDao().InsertMedia(mediaEntity);
                        }

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
                        crdview2.setCardBackgroundColor(color);
                        noteAddRecyc2.setBackgroundColor(color);
                        noteAddCardV2.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

    @OnClick({R.id.detailColor, R.id.detailMedia, R.id.detailCamera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detailColor:
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
                                crdview2.setCardBackgroundColor(color);
                                noteAddCardV2.setCardBackgroundColor(color);
                                noteAddRecyc2.setBackgroundColor(color);
                                colorPicker.dismissDialog();
                            }
                        })
                        .show();

                break;
            case R.id.detailMedia:
                break;
            case R.id.detailCamera:
                dispatchTakePictureIntent();
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onClickMedia(View view, final int position) {


        final List<MediaEntity> item = ((MediaAdapter) noteAddRecyc2.getAdapter()).list2;


        ArrayList<File> fileList = new ArrayList<File>();
        for (int i = 0; i < item.size(); i++) {

            fileList.add(new File(item.get(i).getPath()));
        }

        new PhotoViewer.Builder(view.getContext())
                .file(fileList) // List of Uri, file or String url
                .placeHolder(R.drawable.ic_launcher_background) // placeHolder for images
                .position(position)

                .build()
                .show();

    }

    @Override
    public void onClickCardView(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onLongClickMedia(View view, final int position) {
        final Note item = ((NoteAdapter) noteAddRecyc2.getAdapter()).list.get(position);
        new AlertDialog.Builder(view.getContext())
                .setTitle("Delete")
                .setMessage("Are you want to delete ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    database.mediaDao().DeleteMedia(item.mediaAdapterList.get(position));


                                } catch (Exception e) {
                                    Log.e("hata", e.toString());
                                }
                                // notifyDataSetChanged();


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