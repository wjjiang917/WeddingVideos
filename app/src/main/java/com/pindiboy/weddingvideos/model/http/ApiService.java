package com.pindiboy.weddingvideos.model.http;

import com.pindiboy.weddingvideos.model.bean.ChannelBean;
import com.pindiboy.weddingvideos.model.http.api.YouTubeApi;

import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public class ApiService {
    private YouTubeApi youTubeApi;

    public ApiService(YouTubeApi youTubeApi) {
        this.youTubeApi = youTubeApi;
    }

    public Observable<ChannelBean> fetchChannelVideos(String channelId) {
        return youTubeApi.getChannelVideos(channelId);
    }
}
