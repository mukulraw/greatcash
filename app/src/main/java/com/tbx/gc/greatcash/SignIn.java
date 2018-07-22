package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.SigningInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.loginRequestPOJO.Data;
import com.tbx.gc.greatcash.loginRequestPOJO.loginRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;

import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SignIn extends AppCompatActivity {

    EditText phone , password;
    Button signin;
    ProgressBar progress;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
   // private TextView text_forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        edit = pref.edit();

        phone = findViewById(R.id.editText11);
        password = findViewById(R.id.editText12);
        progress = findViewById(R.id.progressBar6);
        signin = findViewById(R.id.button6);
        //text_forgotPassword=findViewById(R.id.text_forgotPassword);

//        text_forgotPassword.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent=new Intent(SignIn.this,Support.class);
//                startActivity(intent);
//            }
//        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ph = phone.getText().toString();
                final String pa = password.getText().toString();

                if (ph.length() > 0)
                {
                    if (pa.length() > 0)
                    {

                        progress.setVisibility(View.VISIBLE);

                        bean b = (bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        ApiInterface cr = retrofit.create(ApiInterface.class);

                        loginRequestBean body = new loginRequestBean();

                        body.setAction("login");


                        SharedPreferences fcmPref = getSharedPreferences("fcm" , Context.MODE_PRIVATE);

                        String keey = fcmPref.getString("token" , "");



                        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(SignIn.this);


                        Data data = new Data();

                        data.setPassword(pa);
                        data.setPhone(ph);
                        data.setDeviceId(easyDeviceMod.getDevice());
                        data.setFirbaseId(keey);

                        body.setData(data);

                        Call<registerResponseBean> call = cr.login(body);

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

                                    edit.putString("passwordSave",pa);
                                    edit.apply();


                                    Intent intent = new Intent(SignIn.this , MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(SignIn.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();


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
                        Toast.makeText(SignIn.this , "Invalid Password" , Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignIn.this , "Invalid Phone" , Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
