package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;
import com.tbx.gc.greatcash.verifyOtpRequestPOJO.Data;
import com.tbx.gc.greatcash.verifyOtpRequestPOJO.cerifyOtpRequestBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class OTP extends AppCompatActivity implements OTPListener {

    EditText otp;
    Button verify;
    ProgressBar progress;
    String o;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        OtpReader.bind(this,"INFOSM");

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        o = getIntent().getStringExtra("otp");
        uid = getIntent().getStringExtra("uid");


        Log.d("oottpp" , o);


        otp = findViewById(R.id.editText9);
        verify = findViewById(R.id.button4);
        progress = findViewById(R.id.progressBar4);

        //otp.setText(o);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ot = otp.getText().toString();

                if (ot.length() > 0)
                {

                    progress.setVisibility(View.VISIBLE);


                    bean b = (bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    ApiInterface cr = retrofit.create(ApiInterface.class);

                    cerifyOtpRequestBean body = new cerifyOtpRequestBean();

                    body.setAction("verify_otp");
                    Data data = new Data();

                    data.setOtp(ot);
                    data.setUserId(uid);
                    body.setData(data);

                    Call<registerResponseBean> call = cr.verifyOtp(body);

                    call.enqueue(new Callback<registerResponseBean>() {
                        @Override
                        public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {

                                //edit.putString("id" , response.body().getData().getUserId());
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


                                Intent intent = new Intent(OTP.this , ReferId.class);
                                intent.putExtra("uid" , response.body().getData().getUserId());
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(OTP.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();
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
                    otp.setError("");
                }

            }
        });


    }

    @Override
    public void otpReceived(String messageText) {
        Log.d("otpp" , messageText);

        String sub = messageText.substring(messageText.length() - 6,messageText.length());

        Log.d("otpp" , sub);

        otp.setText(sub);

    }
}
