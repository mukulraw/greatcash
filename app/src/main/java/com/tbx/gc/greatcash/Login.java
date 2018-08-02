package com.tbx.gc.greatcash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {

    Button emailSignup;
    TextView signin;

    SharedPreferences fcmPref;
    SharedPreferences.Editor fcmEdit;

    SharedPreferences pref;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    String[] PERMISSIONS = {
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_SMS
    };

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



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


        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        }
        else
        {
            if (pref.getString("id" , "").length() > 0)
            {

                Intent intent = new Intent(Login.this , MainActivity.class);
                startActivity(intent);
                finish();

            }
        }






    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                    ) {
//
                if (pref.getString("id" , "").length() > 0)
                {

                    Intent intent = new Intent(Login.this , MainActivity.class);
                    startActivity(intent);
                    finish();

                }


            } else {
                if (
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)
                        ) {

                    Toast.makeText(getApplicationContext(), "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                    finish();

                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }
        }


    }


}
