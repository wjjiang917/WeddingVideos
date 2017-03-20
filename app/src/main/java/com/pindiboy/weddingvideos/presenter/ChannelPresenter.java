package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.ChannelContract;
import com.pindiboy.weddingvideos.util.Logger;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class ChannelPresenter extends RxPresenter<ChannelContract.View> implements ChannelContract.Presenter {
    @Inject
    public ChannelPresenter(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public void getChannelVideos(String channelId, String pageToken) {
        addSubscribe(mApiService.fetchChannelVideos(channelId, pageToken)
                .compose(RxUtil.<YouTubeBean<ItemId>>rxSchedulerHelper())
                .subscribe(new Action1<YouTubeBean<ItemId>>() {
                    @Override
                    public void call(YouTubeBean<ItemId> youTubeBean) {
                        mView.onChannelVideosLoaded(youTubeBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("", throwable);
                    }
                }));
    }
}
