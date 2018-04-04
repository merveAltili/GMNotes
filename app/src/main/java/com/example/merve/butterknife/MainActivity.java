package com.example.merve.butterknife;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merve.butterknife.db.AppDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txtGmNotes)
    TextView txtGmNotes;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private AppDatabase database;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        database=Room.databaseBuilder(this,AppDatabase.class,"NoteDB").build();

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sharedPreferences.getString("username","")!=""){
            Intent i=new Intent(getApplicationContext(),NoteActivity.class);
            startActivity(i);

            finish();
        }

    }


    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        if (edtPassword.getText().toString().isEmpty() || edtUsername.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Try again with correct username or password", Toast.LENGTH_SHORT).show();

        }
        else if(edtUsername.getText().toString().length()>10) {
            Toast.makeText(getApplicationContext(), "maximum 10 characters", Toast.LENGTH_SHORT).show();

        }else  {

            NodeAPI apiService = APIModule.connectNodeAPI().create(NodeAPI.class);
//
//                    Call<LoginResponse> call = apiService.sendLogin(edtUsername.getText().toString(), edtPassword.getText().toString());
//                    call.enqueue(new Callback<LoginResponse>() {
//                        @Override
//                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                            if (response.body().getLoginSuccess().equals("true")) {
            String res = "Giriş Başarılı ";
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("username",edtUsername.getText().toString());
            editor.apply();
            Intent i=new Intent(getApplicationContext(),NoteActivity.class);
            startActivity(i);

            finish();
//                            } else {
//                                Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<LoginResponse> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(), "Böyle bir kullanıcı bulunamadı !", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });

        }

    }
}