package com.pindiboy.weddingvideos.model.db;

import android.content.Context;

import com.pindiboy.weddingvideos.model.bean.youtube.Snippet;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Jiangwenjin on 2017/3/21.
 */

public class RealmHelper {
    private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;

    public RealmHelper(Context mContext) {
        Realm.init(mContext);
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }

    public void close() {
        if (null != mRealm) {
            mRealm.close();
        }
    }

    public void insertFavourite(Snippet video) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(video);
        mRealm.commitTransaction();
    }

    public void deleteFavourite(String videoId) {
        Snippet video = mRealm.where(Snippet.class).equalTo("videoId", videoId).findFirst();
        mRealm.beginTransaction();
        if (video != null) {
            video.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    public boolean queryFavourite(String videoId) {
        RealmResults<Snippet> results = mRealm.where(Snippet.class).findAll();
        for (Snippet item : results) {
            if (item.getVideoId().equals(videoId)) {
                return true;
            }
        }
        return false;
    }

    public List<Snippet> queryFavourite() {
        RealmResults<Snippet> results = mRealm.where(Snippet.class).findAll();
        return mRealm.copyFromRealm(results);
    }
}
