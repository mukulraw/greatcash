package com.tbx.gc.greatcash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Priyanka on 7/11/2018.
 */

public class YouTube extends AppCompatActivity
{
    private RelativeLayout rel_back;
    private WebView webView;
    String url;

    private MyWebChromeClient mWebChromeClient = null;
    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);
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


        mWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);



    }

    private void setID()
    {
        rel_back=findViewById(R.id.rel_back);
        webView = findViewById(R.id.webView);

    }


    public class MyWebChromeClient extends WebChromeClient {


        @Override
        public void onShowCustomView(View view, CustomViewCallback callback)
        {
        }



    }
}
