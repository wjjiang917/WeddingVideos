package com.pindiboy.weddingvideos.presenter;

import com.pindiboy.weddingvideos.model.bean.youtube.Snippet;
import com.pindiboy.weddingvideos.model.db.RealmHelper;
import com.pindiboy.weddingvideos.presenter.contract.FavoriteContract;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class FavoritePresenter extends RxPresenter<FavoriteContract.View> implements FavoriteContract.Presenter {
    @Inject
    public FavoritePresenter(RealmHelper realmHelper) {
        mRealmHelper = realmHelper;
    }

    @Override
    public void getFavorite() {
        mView.onFavoriteLoaded(mRealmHelper.queryFavourite());
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
