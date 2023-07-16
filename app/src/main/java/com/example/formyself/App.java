package com.example.formyself;

import android.app.Application;
import com.example.formyself.ImageLoader;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SignalGenerator.init(this);
        ImageLoader.initImageLoader(this);
    }
}
