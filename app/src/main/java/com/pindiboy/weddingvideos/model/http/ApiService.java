package com.pindiboy.weddingvideos.model.http;

import com.pindiboy.weddingvideos.model.bean.IpInfo;
import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.model.http.api.IpApi;
import com.pindiboy.weddingvideos.model.http.api.YouTubeApi;

import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public class ApiService {
    private YouTubeApi youTubeApi;
    private IpApi ipApi;

    public ApiService(YouTubeApi youTubeApi, IpApi ipApi) {
        this.youTubeApi = youTubeApi;
        this.ipApi = ipApi;
    }

    public Observable<YouTubeBean<ItemId>> fetchChannelVideos(String channelId, String pageToken) {
        return youTubeApi.getChannelVideos(channelId, pageToken);
    }

    public Observable<YouTubeBean<ItemId>> fetchRelatedVideos(String videoId, String pageToken) {
        return youTubeApi.getRelatedVideos(videoId, pageToken);
    }

    public Observable<YouTubeBean<ItemId>> search(String q, String pageToken) {
        return youTubeApi.search(q, pageToken);
    }

    public Observable<YouTubeBean<String>> fetchVideoDetail(String videoId) {
        return youTubeApi.getVideoDetail(videoId);
    }

    public Observable<IpInfo> fetchIpInfo() {
        return ipApi.getIpInfo();
    }
}
