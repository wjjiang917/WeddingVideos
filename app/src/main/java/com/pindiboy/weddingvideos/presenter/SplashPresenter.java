package com.pindiboy.weddingvideos.presenter;

import android.Manifest;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.SplashContract;
import com.pindiboy.weddingvideos.util.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/24.
 */

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {
    @Inject
    public SplashPresenter(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public void getChannels() {
        addSubscribe(mApiService.fetchChannels()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(channels -> mView.onChannelsLoaded(channels),
                        throwable -> {
                            Logger.e("", throwable);
                            mView.onChannelsFailed();
                        }));
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                });
    }
}
