package com.pindiboy.weddingvideos.di.module;

import com.pindiboy.weddingvideos.ui.fragment.WeddingFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Module
public class PageModule {
    @Singleton
    @Provides
    WeddingFragment provideWeddingFragment() {
        return new WeddingFragment();
    }
}
