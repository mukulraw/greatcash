package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbx.gc.greatcash.offerEarnRequestPOJO.Data;
import com.tbx.gc.greatcash.offerEarnRequestPOJO.offerEarnBean;
import com.tbx.gc.greatcash.offerEarnRequestPOJO.offerEarnRequestBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class InstallApp extends AppCompatActivity {

    ImageView image;
    TextView name;
    Button install;
    ProgressBar progress;


    String packageName;

    Toolbar toolbar;

    String id , amount;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_app);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        image = findViewById(R.id.imageView8);
        name = findViewById(R.id.textView105);
        install = findViewById(R.id.button8);
        progress = findViewById(R.id.progressBar12);

        toolbar = findViewById(R.id.toolbar3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Install App");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });



        ImageLoader loader = ImageLoader.getInstance();

        loader.displayImage(getIntent().getStringExtra("image") , image);

        name.setText(getIntent().getStringExtra("name"));

        packageName = getIntent().getStringExtra("package");
        id = getIntent().getStringExtra("id");
        amount = getIntent().getStringExtra("amount");


        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

                //Copy App URL from Google Play Store.
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));

                startActivity(intent);

            }
        });





    }

    public boolean isPackageInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isPackageInstalled(this , packageName))
        {


            progress.setVisibility(View.VISIBLE);


            bean b = (bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            ApiInterface cr = retrofit.create(ApiInterface.class);

            offerEarnRequestBean body = new offerEarnRequestBean();
            body.setAction("single_offer_earn");

            Data data = new Data();
            data.setEarnAmount(amount);
            data.setOfferId(id);
            data.setUserId(pref.getString("id", ""));

            body.setData(data);


            Call<offerEarnBean> call = cr.offersEarn(body);

            call.enqueue(new Callback<offerEarnBean>() {
                @Override
                public void onResponse(Call<offerEarnBean> call, Response<offerEarnBean> response) {
                    if (response.body().getStatus().equals("1"))
                    {
                        Toast.makeText(InstallApp.this , "Amount added successfully" , Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<offerEarnBean> call, Throwable t) {

                }
            });


        }

    }
}
