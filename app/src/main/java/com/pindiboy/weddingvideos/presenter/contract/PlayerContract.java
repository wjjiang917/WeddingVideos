package com.pindiboy.weddingvideos.presenter.contract;

import com.pindiboy.weddingvideos.presenter.BasePresenter;
import com.pindiboy.weddingvideos.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface PlayerContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
    }
}
