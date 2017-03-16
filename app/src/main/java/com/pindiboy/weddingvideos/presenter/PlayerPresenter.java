package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.bean.YouTubeBean;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.PlayerContract;
import com.pindiboy.weddingvideos.util.Logger;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class PlayerPresenter extends RxPresenter<PlayerContract.View> implements PlayerContract.Presenter {
    @Inject
    public PlayerPresenter(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public void getVideoDetail(String videoId) {
        addSubscribe(mApiService.fetchVideoDetail(videoId)
                .compose(RxUtil.<YouTubeBean>rxSchedulerHelper())
                .subscribe(new Action1<YouTubeBean>() {
                    @Override
                    public void call(YouTubeBean youTubeBean) {
                        mView.onVideoDetailLoaded(youTubeBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("", throwable);
                    }
                }));
    }
}
