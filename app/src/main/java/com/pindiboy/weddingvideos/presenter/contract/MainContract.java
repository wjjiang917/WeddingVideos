package com.pindiboy.weddingvideos.presenter.contract;

import com.pindiboy.weddingvideos.model.bean.IpInfo;
import com.pindiboy.weddingvideos.presenter.BasePresenter;
import com.pindiboy.weddingvideos.ui.BaseView;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface MainContract {
    interface View extends BaseView {
        void onIpInfoLoaded(IpInfo ipInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void getIpInfo();

        void checkPermissions(RxPermissions rxPermissions);
    }
}
