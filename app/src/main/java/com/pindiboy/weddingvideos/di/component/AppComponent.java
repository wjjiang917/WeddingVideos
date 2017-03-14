package com.pindiboy.weddingvideos.di.component;

import com.pindiboy.weddingvideos.App;
import com.pindiboy.weddingvideos.di.module.AppModule;
import com.pindiboy.weddingvideos.di.module.HttpModule;
import com.pindiboy.weddingvideos.di.module.PageModule;
import com.pindiboy.weddingvideos.model.http.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class, PageModule.class})
public interface AppComponent {
    App getContext();

    ApiService apiService();
}
