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
                .compose(RxUtil.rxSchedulerHelper())
                .map(youTubeBean -> {
                    Item<String> item = youTubeBean.getItems().get(0);
                    Snippet snippet = item.getSnippet();
                    snippet.setVideoId(item.getId());
                    snippet.setThumbnail(item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl());
                    snippet.setFavourite(mRealmHelper.queryFavourite(item.getId()));
                    return youTubeBean;
                })
                .subscribe(youTubeBean -> mView.onVideoDetailLoaded(youTubeBean),
                        throwable -> Logger.e("", throwable)));
    }

    @Override
    public void getRelatedVideos(String videoId, String pageToken) {
        addSubscribe(mApiService.fetchRelatedVideos(videoId, pageToken)
                .compose(RxUtil.rxSchedulerHelper())
                .map(youTubeBean -> {
                    for (Item<ItemId> item : youTubeBean.getItems()) {
                        Snippet snippet = item.getSnippet();
                        snippet.setVideoId(item.getId().getVideoId());
                        snippet.setThumbnail(item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl());
                        snippet.setFavourite(mRealmHelper.queryFavourite(item.getId().getVideoId()));
                    }
                    return youTubeBean;
                })
                .subscribe(youTubeBean -> mView.onRelatedVideosLoaded(youTubeBean),
                        throwable -> Logger.e("", throwable)));
    }

    @Override
    public void addFavorite(Snippet video) {
        mRealmHelper.insertFavourite(video);
    }

    @Override
    public void removeFavorite(String videoId) {
        mRealmHelper.deleteFavourite(videoId);
    }
}
