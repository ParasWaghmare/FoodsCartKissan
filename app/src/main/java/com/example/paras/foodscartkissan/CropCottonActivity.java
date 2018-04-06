package com.example.paras.foodscartkissan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class CropCottonActivity extends AppCompatActivity {
    private String url = "file:///android_asset/cotton.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_cotton);
        WebView webCotton = findViewById(R.id.webCotton);
        webCotton.getSettings().setJavaScriptEnabled(true);
        webCotton.getSettings().setLoadsImagesAutomatically(true);
        webCotton.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webCotton.loadUrl(url);
    }
}
