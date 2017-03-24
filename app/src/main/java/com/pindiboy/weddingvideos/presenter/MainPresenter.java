package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.MainContract;
import com.pindiboy.weddingvideos.util.Logger;

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
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(ipInfo -> mView.onIpInfoLoaded(ipInfo),
                        throwable -> Logger.e("", throwable)));
    }
}
