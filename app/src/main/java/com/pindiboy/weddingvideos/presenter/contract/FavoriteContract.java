package com.pindiboy.weddingvideos.presenter.contract;

import com.pindiboy.weddingvideos.model.bean.youtube.Snippet;
import com.pindiboy.weddingvideos.presenter.BasePresenter;
import com.pindiboy.weddingvideos.ui.BaseView;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface FavoriteContract {
    interface View extends BaseView {
        void onFavoriteLoaded(List<Snippet> videos);
    }

    interface Presenter extends BasePresenter<View> {
        void getFavorite();

        void addFavorite(Snippet video);

        void removeFavorite(String videoId);
    }
}
