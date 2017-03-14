package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
