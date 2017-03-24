package com.pindiboy.weddingvideos.di.component;

import android.app.Activity;

import com.pindiboy.weddingvideos.di.module.ActivityModule;
import com.pindiboy.weddingvideos.di.scope.ActivityScope;
import com.pindiboy.weddingvideos.ui.activity.MainActivity;
import com.pindiboy.weddingvideos.ui.activity.PlayerActivity;
import com.pindiboy.weddingvideos.ui.activity.SearchActivity;
import com.pindiboy.weddingvideos.ui.activity.SplashActivity;

import dagger.Component;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(PlayerActivity playerActivity);

    void inject(SearchActivity searchActivity);
}
