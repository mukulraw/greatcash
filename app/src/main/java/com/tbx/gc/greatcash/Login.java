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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import com.tbx.gc.greatcash.loginRequestPOJO.loginRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;
import com.tbx.gc.greatcash.socialRequestPOJO.Data;
import com.tbx.gc.greatcash.socialRequestPOJO.socialRequestBean;

import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {

    Button emailSignup, google;
    TextView signin;

    ProgressBar progress;
    SharedPreferences fcmPref;
    SharedPreferences.Editor fcmEdit;



    SharedPreferences pref;
    SharedPreferences.Editor edit;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    String[] PERMISSIONS = {
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    int RC_SIGN_IN = 12;

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


    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = pref.edit();
        fcmPref = getSharedPreferences("fcm", Context.MODE_PRIVATE);
        fcmEdit = fcmPref.edit();

        try {

            String tok = FirebaseInstanceId.getInstance().getToken();

            Log.d("token", tok);

            fcmEdit.putString("token", tok);

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

/*
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
*/

        emailSignup = findViewById(R.id.button);
        google = findViewById(R.id.button2);
        signin = findViewById(R.id.textView);

        progress = findViewById(R.id.progressBar18);

        emailSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();

            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, SignIn.class);
                startActivity(intent);
                finish();

            }
        });


        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            if (pref.getString("id", "").length() > 0) {

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                signIn();

            }
        });


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ) {
//
                if (pref.getString("id", "").length() > 0) {

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }


            } else {
                if (
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("asasd", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void updateUI(GoogleSignInAccount account) {

        String em = account.getEmail();
        final String pid = account.getId();


        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        socialRequestBean body = new socialRequestBean();

        body.setAction("social_login");


        SharedPreferences fcmPref = getSharedPreferences("fcm", Context.MODE_PRIVATE);

        String keey = fcmPref.getString("token", "");


        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(Login.this);


        Data data = new Data();


        data.setEmail(em);
        data.setPid(pid);


        body.setData(data);

        Call<registerResponseBean> call = cr.socialLogin(body);

        call.enqueue(new Callback<registerResponseBean>() {
            @Override
            public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                if (response.body().getStatus().equals("1")) {

                    edit.putString("id", response.body().getData().getUserId());
                    edit.putString("refId", response.body().getData().getRefId());
                    edit.putString("parentId", response.body().getData().getParentId());
                    edit.putString("name", response.body().getData().getName());
                    edit.putString("email", response.body().getData().getEmail());
                    edit.putString("phone", response.body().getData().getMobile());
                    edit.putString("pic", response.body().getData().getUserPic());
                    edit.putString("dob", response.body().getData().getBirthDate());
                    edit.putString("country", response.body().getData().getCountry());
                    edit.putString("state", response.body().getData().getState());
                    edit.putString("city", response.body().getData().getCity());
                    edit.putString("pid", response.body().getData().getPid());

                    edit.putString("passwordSave", pid);
                    edit.apply();


                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<registerResponseBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

}
