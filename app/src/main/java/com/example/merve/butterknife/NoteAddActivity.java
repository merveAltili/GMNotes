package com.example.merve.butterknife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merve.butterknife.db.Entity.NoteEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteAddActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
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
    @BindView(R.id.txtKategori)
    TextView txtKategori;
    @BindView(R.id.edtKategori)
    EditText edtKategori;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        if (!edtDetail.getText().toString().isEmpty() && edtDetail.getText().toString().length() < 121 && edtTitle.getText().toString().length() < 15 && !edtTitle.getText().toString().isEmpty() && !edtKategori.getText().toString().isEmpty()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NoteEntity note = new NoteEntity();
                        note.setUser(sharedPreferences.getString("username", ""));
                        note.setTitle(edtTitle.getText().toString());
                        note.setDetail(edtDetail.getText().toString());
                        note.setKategori(edtKategori.getText().toString());
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
