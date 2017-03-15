package com.pindiboy.weddingvideos.model.bean;

import java.util.Map;

/**
 * Created by Jiangwenjin on 2017/3/15.
 * <p>
 * "publishedAt": "2016-11-19T11:30:00.000Z",
 * "channelId": "UC1KX5wQ8yqJTByEsd2KhrLA",
 * "title": "TOP 10 REASONS WHY MS DHONI WILL NEVER BE HATED",
 * "description": "10 reason why MS Dhoni is the best cricket captain in the world. EDITOR:- Sony Vegas Pro13 AUDIO SOURCE : NCS SUBSCRIBE CHANNEL: ...",
 * "thumbnails": {},
 * "channelTitle": "TOP10 INSANE - Cricket",
 * "liveBroadcastContent": "none"
 */

public class Snippet {
    private String channelId;
    private String channelTitle;
    private String description;
    private String liveBroadcastContent;
    private String publishedAt;
    private Map<String, Thumbnail> thumbnails; // key: default, medium, high
    private String title;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Map<String, Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Map<String, Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
