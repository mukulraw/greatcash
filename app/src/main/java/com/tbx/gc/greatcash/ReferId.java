package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbx.gc.greatcash.referRequestPOJO.Data;
import com.tbx.gc.greatcash.referRequestPOJO.referRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;

import java.sql.Ref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ReferId extends AppCompatActivity {

    EditText refer;
    Button suubmit;
    ProgressBar progress;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_id);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        refer = findViewById(R.id.editText10);
        suubmit = findViewById(R.id.button5);
        progress = findViewById(R.id.progressBar5);

        suubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String r = refer.getText().toString();

                if (r.length() > 0)
                {

                    progress.setVisibility(View.VISIBLE);

                    bean b = (bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    ApiInterface cr = retrofit.create(ApiInterface.class);

                    referRequestBean body = new referRequestBean();

                    body.setAction("refer");

                    Data data = new Data();

                    data.setUserId(pref.getString("id" , ""));
                    data.setRefer(r);

                    body.setData(data);

                    Call<registerResponseBean> call = cr.referId(body);

                    call.enqueue(new Callback<registerResponseBean>() {
                        @Override
                        public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {


                            if (response.body().getStatus().equals("1"))
                            {

                                edit.putString("id" , response.body().getData().getUserId());
                                edit.putString("refId" , response.body().getData().getRefId());
                                edit.putString("parentId" , response.body().getData().getParentId());
                                edit.putString("name" , response.body().getData().getName());
                                edit.putString("email" , response.body().getData().getEmail());
                                edit.putString("phone" , response.body().getData().getMobile());
                                edit.putString("pic" , response.body().getData().getUserPic());
                                edit.putString("dob" , response.body().getData().getBirthDate());
                                edit.putString("country" , response.body().getData().getCountry());
                                edit.putString("state" , response.body().getData().getState());
                                edit.putString("city" , response.body().getData().getCity());
                                edit.putString("pid" , response.body().getData().getPid());
                                edit.apply();


                                Intent intent = new Intent(ReferId.this , MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(ReferId.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();
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
                    refer.setError("");
                }


            }
        });

    }
}
