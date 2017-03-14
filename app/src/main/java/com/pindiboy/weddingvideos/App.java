package com.pindiboy.weddingvideos;

import android.app.Application;
import android.content.Context;

import com.pindiboy.weddingvideos.di.component.AppComponent;
import com.pindiboy.weddingvideos.di.component.DaggerAppComponent;
import com.pindiboy.weddingvideos.di.module.AppModule;
import com.pindiboy.weddingvideos.di.module.HttpModule;
import com.pindiboy.weddingvideos.di.module.PageModule;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
public class App extends Application {
    private static App mInstance;
    public static AppComponent appComponent;

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

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .httpModule(new HttpModule())
                    .pageModule(new PageModule())
                    .build();
        }
        return appComponent;
    }
}
