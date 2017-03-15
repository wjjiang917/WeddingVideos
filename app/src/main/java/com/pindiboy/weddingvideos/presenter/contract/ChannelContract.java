package com.pindiboy.weddingvideos.presenter.contract;

import com.pindiboy.weddingvideos.model.bean.ChannelBean;
import com.pindiboy.weddingvideos.presenter.BasePresenter;
import com.pindiboy.weddingvideos.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface ChannelContract {
    interface View extends BaseView {
        void onChannelVideosLoaded(ChannelBean channelBean);
    }

    interface Presenter extends BasePresenter<View> {
        void getChannelVideos(String channelId);
    }
}
