package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {

    Button emailSignup;
    TextView signin;

    SharedPreferences fcmPref;
    SharedPreferences.Editor fcmEdit;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        fcmPref = getSharedPreferences("fcm" , Context.MODE_PRIVATE);
        fcmEdit = fcmPref.edit();

        try {

            String tok = FirebaseInstanceId.getInstance().getToken();

            Log.d("token", tok);

            fcmEdit.putString("token" , tok);

            fcmEdit.apply();

        } catch (Exception e) {

            new Thread() {
                @Override
                public void run() {
                    //If there are stories, add them to the table
                    //try {
                    // code runs in a thread
                    //runOnUiThread(new Runnable() {
                    //  @Override
                    //public void run() {
                    new MyFirebaseInstanceIDService().onTokenRefresh();
                    //}
                    //});
                    //} catch (final Exception ignored) {
                    //}
                }
            }.start();

            e.printStackTrace();
        }


        emailSignup = findViewById(R.id.button);
        signin = findViewById(R.id.textView);



        emailSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this , Signup.class);
                startActivity(intent);
                finish();

            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this , SignIn.class);
                startActivity(intent);
                finish();

            }
        });


        if (pref.getString("id" , "").length() > 0)
        {

            Intent intent = new Intent(Login.this , MainActivity.class);
            startActivity(intent);
            finish();

        }


    }
}
