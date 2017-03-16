package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.bean.IpInfo;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.MainContract;
import com.pindiboy.weddingvideos.util.Logger;

import javax.inject.Inject;

import rx.functions.Action1;

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
                .subscribe(new Action1<IpInfo>() {
                    @Override
                    public void call(IpInfo ipInfo) {
                        mView.onIpInfoLoaded(ipInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("", throwable);
                    }
                }));
    }
}
