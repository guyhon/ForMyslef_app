package com.example.formyself;

import android.app.Application;
import com.example.formyself.Utilities.ImageLoader;
import com.example.formyself.Utilities.SignalGenerator;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SignalGenerator.init(this);
        ImageLoader.initImageLoader(this);
    }
}
