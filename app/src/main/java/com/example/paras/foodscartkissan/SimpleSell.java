package com.example.paras.foodscartkissan;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Paras on 2/11/2018.
 */

public class SimpleSell extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Picasso.Builder pBuilder = new Picasso.Builder(this);
        pBuilder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = pBuilder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
