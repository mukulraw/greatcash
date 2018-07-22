package com.tbx.gc.greatcash;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

public class WWView extends AppCompatActivity {

    Toolbar toolbar;
   // WebView webView;

    String title;
    String url;
    private RelativeLayout rel_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wwview);

        rel_back=findViewById(R.id.rel_back);

        rel_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        //toolbar = findViewById(R.id.toolbar4);
        //webView = findViewById(R.id.web);


//        title = getIntent().getStringExtra("title");
//        url = getIntent().getStringExtra("url");

        //setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setTitleTextColor(Color.BLACK);
        //toolbar.setTitle(title);
       // toolbar.setNavigationIcon(R.drawable.back);
       // toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//
//        });


       // webView.loadUrl(url);



    }
}
