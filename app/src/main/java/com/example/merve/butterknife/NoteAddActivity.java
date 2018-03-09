package com.example.merve.butterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merve.butterknife.db.Entity.NoteEntity;
import com.example.merve.butterknife.db.Note;
import com.example.merve.butterknife.db.User;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        if(!edtDetail.getText().toString().isEmpty()&& edtDetail.getText().toString().length()<121 && edtTitle.getText().toString().length()<15 && !edtTitle.getText().toString().isEmpty()){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NoteEntity note=new NoteEntity();
                        note.setUser(1);
                        note.setTitle(edtTitle.getText().toString());
                        note.setDetail(edtDetail.getText().toString());

                        MainActivity.database.notedao().InsertNote(note);
                    }catch (Exception e){
                        Log.e("hata",e.toString());
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Intent i=new Intent(getApplicationContext(),NoteActivity.class);
                                i.putExtra("detail",edtDetail.getText());
                                i.putExtra("title",edtTitle.getText());
                                startActivity(i);
                            }catch (Exception e){

                                Log.e("hata",e.toString());
                            }

                        }
                    });

                }
            }).start();


        }
        else{
            Toast.makeText(this,"Lütfen geçerli bir değer giriniz",Toast.LENGTH_SHORT).show();
        }
    }
}
