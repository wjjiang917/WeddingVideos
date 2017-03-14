package com.pindiboy.weddingvideos.model.http.api;

import com.pindiboy.weddingvideos.model.bean.ChannelBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public interface YouTubeApi {
    String HOST = "https://www.googleapis.com/youtube/v3/";

    /**
     * get videos by channelId
     */
    @GET("search")
    Observable<ChannelBean> getChannelVideos();
}
