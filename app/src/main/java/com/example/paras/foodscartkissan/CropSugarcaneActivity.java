package com.example.paras.foodscartkissan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class CropSugarcaneActivity extends AppCompatActivity {
    private String url =  "file:///android_asset/sugarcane.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_sugarcane);
        WebView webSugar = findViewById(R.id.sugarcaneView);
        webSugar.getSettings().setLoadsImagesAutomatically(true);
        webSugar.getSettings().setJavaScriptEnabled(true );
        webSugar.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSugar.loadUrl(url);
    }
}
