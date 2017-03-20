package com.pindiboy.weddingvideos.presenter.contract;

import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.presenter.BasePresenter;
import com.pindiboy.weddingvideos.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface PlayerContract {
    interface View extends BaseView {
        void onVideoDetailLoaded(YouTubeBean<String> youTubeBean);

        void onRelatedVideosLoaded(YouTubeBean<ItemId> youTubeBean);
    }

    interface Presenter extends BasePresenter<View> {
        void getVideoDetail(String videoId);

        void getRelatedVideos(String videoId, String pageToken);
    }
}
