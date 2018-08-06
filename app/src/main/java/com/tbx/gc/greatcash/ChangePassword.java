package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbx.gc.greatcash.passwordRequestPOJO.Data;
import com.tbx.gc.greatcash.passwordRequestPOJO.passwordRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ChangePassword extends AppCompatActivity {

    Toolbar toolbar;
    EditText password;
    Button submit;
    ProgressBar progress;
    String id;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = pref.edit();

        id = getIntent().getStringExtra("id");

        toolbar = findViewById(R.id.toolbar2);
        password = findViewById(R.id.editText13);
        submit = findViewById(R.id.button7);
        progress = findViewById(R.id.progressBar7);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Change Password");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String p = password.getText().toString();

                if (p.length() > 0)
                {

                    progress.setVisibility(View.VISIBLE);

                    bean b = (bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    ApiInterface cr = retrofit.create(ApiInterface.class);

                    passwordRequestBean body = new passwordRequestBean();

                    body.setAction("reset_password");

                    Data data = new Data();
                    data.setUserId(id);
                    data.setPassword(p);

                    body.setData(data);

                    Call<registerResponseBean> call = cr.changePassword(body);

                    call.enqueue(new Callback<registerResponseBean>() {
                        @Override
                        public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {

                                edit.putString("passwordSave", p);
                                edit.apply();

                                Toast.makeText(ChangePassword.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            else
                            {
                                Toast.makeText(ChangePassword.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<registerResponseBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    password.setError("");
                }

            }
        });

    }
}
