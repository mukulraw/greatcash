package com.tbx.gc.greatcash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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

import im.delight.android.webview.AdvancedWebView;

/**
 * Created by Priyanka on 7/11/2018.
 */

public class YouTube extends AppCompatActivity implements AdvancedWebView.Listener
{
    private RelativeLayout rel_back;
    private AdvancedWebView mWebView;
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


        mWebView.setListener(this, this);
        mWebView.loadUrl(url);



    }

    private void setID()
    {
        rel_back=findViewById(R.id.rel_back);
        mWebView = findViewById(R.id.webView);

    }


    public class MyWebChromeClient extends WebChromeClient {


        @Override
        public void onShowCustomView(View view, CustomViewCallback callback)
        {
        }



    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }

}
