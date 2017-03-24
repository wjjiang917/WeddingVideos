package com.pindiboy.weddingvideos.presenter.contract;

import com.pindiboy.weddingvideos.model.bean.ChannelConfig;
import com.pindiboy.weddingvideos.presenter.BasePresenter;
import com.pindiboy.weddingvideos.ui.BaseView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public interface SplashContract {
    interface View extends BaseView {
        void onChannelsLoaded(List<ChannelConfig> channels);

        void onChannelsFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void getChannels();

        void checkPermissions(RxPermissions rxPermissions);
    }
}
