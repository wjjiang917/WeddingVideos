package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter(ApiService apiService) {
        mApiService = apiService;
    }
}
