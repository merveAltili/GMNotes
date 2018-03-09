package com.example.merve.butterknife;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merve.butterknife.db.AppDatabase;
import com.example.merve.butterknife.model.LoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txtGmNotes)
    TextView txtGmNotes;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    public static AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        database=Room.databaseBuilder(this,AppDatabase.class,"NoteDB").build();

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

                    Call<LoginResponse> call = apiService.sendLogin(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                String res = "Giriş Başarılı ";
                                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),NoteActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Böyle bir kullanıcı bulunamadı !", Toast.LENGTH_SHORT).show();

                        }
                    });

                }

    }
}
