package com.pindiboy.weddingvideos.model.http.api;

import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.YouTubeBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public interface YouTubeApi {
    String HOST = "https://www.googleapis.com/youtube/v3/";

    /**
     * get videos by channelId
     */
    @GET("search?part=snippet&order=rating&maxResults=" + Constant.CHANNEL_VIDEOS_PAGE_SIZE + "&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean> getChannelVideos(@Query("channelId") String channelId, @Query("pageToken") String pageToken);

    /**
     * video detail
     */
    @GET("videos?part=snippet,contentDetails,statistics&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean> getVideoDetail(@Query("id") String videoId);
}
