package com.pindiboy.weddingvideos.model.bean;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public class Item {
    private String kind;
    private String etag;
    private ItemId id;
    private Snippet snippet;
    private ContentDetail contentDetails;
    private Statistics statistics;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public ItemId getId() {
        return id;
    }

    public void setId(ItemId id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public ContentDetail getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetail contentDetails) {
        this.contentDetails = contentDetails;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
