package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.PlayerContract;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class PlayerPresenter extends RxPresenter<PlayerContract.View> implements PlayerContract.Presenter {
    @Inject
    public PlayerPresenter(ApiService apiService) {
        mApiService = apiService;
    }
}
