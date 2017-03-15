package com.pindiboy.weddingvideos.model.bean;

/**
 * Created by Jiangwenjin on 2017/3/15.
 * <p>
 * "kind": "youtube#video",
 * "videoId": "Ee2ot0QkEKg"
 */

public class VideoId {
    private String kind;
    private String videoId;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
