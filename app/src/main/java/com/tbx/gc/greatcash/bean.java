package com.tbx.gc.greatcash;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class bean extends Application {


    String BASE_URL = "http://nationproducts.in/";

    boolean locked = true;

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));

    }
}
