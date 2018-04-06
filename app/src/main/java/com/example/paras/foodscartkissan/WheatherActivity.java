package com.example.paras.foodscartkissan;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class WheatherActivity extends AppCompatActivity {
    private String url =  "https://weather.com/en-IN/weather/today/l/3b8f8bc89c9cc5779cbab69a50847c5d0ed8c2012e423f64e5ec67eed43df5da";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather);
        WebView webView = findViewById(R.id.webView);


        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }
}
