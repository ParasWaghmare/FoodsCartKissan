package com.example.paras.foodscartkissan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class AboutActivity extends AppCompatActivity {
    private String url =  "file:///android_asset/assistant.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        WebView webRice = findViewById(R.id.chatbot);
        webRice.getSettings().setLoadsImagesAutomatically(true);
        webRice.getSettings().setJavaScriptEnabled(true);
        webRice.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webRice.loadUrl(url);


    }







}
