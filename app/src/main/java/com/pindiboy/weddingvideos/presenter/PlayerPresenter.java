package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.common.RxUtil;
import com.pindiboy.weddingvideos.model.bean.youtube.Item;
import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.Snippet;
import com.pindiboy.weddingvideos.model.bean.youtube.Thumbnail;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.model.db.RealmHelper;
import com.pindiboy.weddingvideos.model.http.ApiService;
import com.pindiboy.weddingvideos.presenter.contract.PlayerContract;
import com.pindiboy.weddingvideos.util.Logger;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class PlayerPresenter extends RxPresenter<PlayerContract.View> implements PlayerContract.Presenter {
    @Inject
    public PlayerPresenter(ApiService apiService, RealmHelper realmHelper) {
        mApiService = apiService;
        mRealmHelper = realmHelper;
    }

    @Override
    public void getVideoDetail(String videoId) {
        addSubscribe(mApiService.fetchVideoDetail(videoId)
                .compose(RxUtil.<YouTubeBean<String>>rxSchedulerHelper())
                .map(new Func1<YouTubeBean<String>, YouTubeBean<String>>() {
                    @Override
                    public YouTubeBean<String> call(YouTubeBean<String> youTubeBean) {
                        Item<String> item = youTubeBean.getItems().get(0);
                        Snippet snippet = item.getSnippet();
                        snippet.setVideoId(item.getId());
                        snippet.setThumbnail(item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl());
                        snippet.setFavourite(mRealmHelper.queryFavourite(item.getId()));
                        return youTubeBean;
                    }
                })
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
                .map(new Func1<YouTubeBean<ItemId>, YouTubeBean<ItemId>>() {
                    @Override
                    public YouTubeBean<ItemId> call(YouTubeBean<ItemId> youTubeBean) {
                        for (Item<ItemId> item : youTubeBean.getItems()) {
                            Snippet snippet = item.getSnippet();
                            snippet.setVideoId(item.getId().getVideoId());
                            snippet.setThumbnail(item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl());
                            snippet.setFavourite(mRealmHelper.queryFavourite(item.getId().getVideoId()));
                        }
                        return youTubeBean;
                    }
                })
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
