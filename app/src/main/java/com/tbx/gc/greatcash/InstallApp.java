package com.tbx.gc.greatcash;

import android.content.Intent;
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

import com.nostra13.universalimageloader.core.ImageLoader;

public class InstallApp extends AppCompatActivity {

    ImageView image;
    TextView name;
    Button install;
    ProgressBar progress;


    String packageName;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_app);

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
}
