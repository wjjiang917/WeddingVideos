package com.pindiboy.weddingvideos.presenter;

import android.Manifest;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.bean.IpInfo;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.MainContract;
import com.pindiboy.weddingvideos.util.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public void getIpInfo() {
        addSubscribe(mApiService.fetchIpInfo()
                .compose(RxUtil.<IpInfo>rxSchedulerHelper())
                .subscribe(ipInfo -> mView.onIpInfoLoaded(ipInfo),
                        throwable -> Logger.e("", throwable)));
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                });
    }
}
