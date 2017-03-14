package com.pindiboy.weddingvideos;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
public class App extends Application {
    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
