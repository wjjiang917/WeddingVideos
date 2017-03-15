package com.pindiboy.weddingvideos.di.component;

import android.app.Activity;

import com.pindiboy.weddingvideos.di.module.FragmentModule;
import com.pindiboy.weddingvideos.di.scope.FragmentScope;
import com.pindiboy.weddingvideos.ui.fragment.ChannelFragment;

import dagger.Component;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(ChannelFragment channelFragment);
}
