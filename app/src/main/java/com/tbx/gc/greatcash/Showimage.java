package com.tbx.gc.greatcash;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class Showimage extends AppCompatActivity
{
    private RelativeLayout rel_back;
    String url;
    private ImageView img_show;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimage);
        setID();

        url = getIntent().getStringExtra("url");
        rel_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        Glide.with(getApplicationContext()).load(Uri.parse(url)).into(img_show);





    }

    private void setID()
    {
        rel_back=findViewById(R.id.rel_back);
        img_show=findViewById(R.id.image);

    }



}
