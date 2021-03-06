package com.pindiboy.weddingvideos.di.module;

import com.pindiboy.weddingvideos.App;
import com.pindiboy.weddingvideos.model.db.RealmHelper;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.model.http.api.ChannelApi;
import com.pindiboy.weddingvideos.model.http.api.IpApi;
import com.pindiboy.weddingvideos.model.http.api.YouTubeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ApiService provideApiService(YouTubeApi youTubeApi, IpApi ipApi, ChannelApi channelApi) {
        return new ApiService(youTubeApi, ipApi, channelApi);
    }

    @Provides
    @Singleton
    RealmHelper provideRealmHelper() {
        return new RealmHelper(application);
    }
}
