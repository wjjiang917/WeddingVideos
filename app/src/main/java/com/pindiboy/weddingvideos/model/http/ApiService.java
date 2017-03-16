package com.pindiboy.weddingvideos.model.http;

import com.pindiboy.weddingvideos.model.bean.YouTubeBean;
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

    public Observable<YouTubeBean> fetchChannelVideos(String channelId, String pageToken) {
        return youTubeApi.getChannelVideos(channelId, pageToken);
    }

    public Observable<YouTubeBean> fetchVideoDetail(String videoId) {
        return youTubeApi.getVideoDetail(videoId);
    }
}
