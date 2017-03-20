package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
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
                .compose(RxUtil.<YouTubeBean<String>>rxSchedulerHelper())
                .subscribe(new Action1<YouTubeBean<String>>() {
                    @Override
                    public void call(YouTubeBean<String> youTubeBean) {
                        mView.onVideoDetailLoaded(youTubeBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("", throwable);
                    }
                }));
    }

    @Override
    public void getRelatedVideos(String videoId, String pageToken) {
        addSubscribe(mApiService.fetchRelatedVideos(videoId, pageToken)
                .compose(RxUtil.<YouTubeBean<ItemId>>rxSchedulerHelper())
                .subscribe(new Action1<YouTubeBean<ItemId>>() {
                    @Override
                    public void call(YouTubeBean<ItemId> youTubeBean) {
                        mView.onRelatedVideosLoaded(youTubeBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("", throwable);
                    }
                }));
    }
}
