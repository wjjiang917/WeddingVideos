package com.pindiboy.weddingvideos.presenter.contract;

import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.Snippet;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.presenter.BasePresenter;
import com.pindiboy.weddingvideos.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface ChannelContract {
    interface View extends BaseView {
        void onChannelVideosLoaded(YouTubeBean<ItemId> youTubeBean);
    }

    interface Presenter extends BasePresenter<View> {
        void getChannelVideos(String channelId, String pageToken);

        void addFavorite(Snippet video);

        void removeFavorite(String videoId);
    }
}
