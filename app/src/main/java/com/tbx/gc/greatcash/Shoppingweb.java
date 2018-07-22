package com.tbx.gc.greatcash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class Shoppingweb extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingweb);
        webView = (WebView) findViewById(R.id.wb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(""+getIntent().getStringExtra("url"));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}
