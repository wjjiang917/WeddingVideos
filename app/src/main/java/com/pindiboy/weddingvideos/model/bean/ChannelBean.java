package com.pindiboy.weddingvideos.model.bean;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/14.
 * <p>
 * "kind": "youtube#searchListResponse",
 * "etag": "\"uQc-MPTsstrHkQcRXL3IWLmeNsM/bECqA6d-kVjW5-HYx-X0Vw1N6fk\"",
 * "nextPageToken": "CAUQAA",
 * "regionCode": "PK",
 * "pageInfo": {},
 * "items": []
 */
public class ChannelBean {
    private String kind;
    private String prevPageToken;
    private String nextPageToken;
    private String regionCode;
    private PageInfo pageInfo;
    private List<VideoBean> items;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<VideoBean> getItems() {
        return items;
    }

    public void setItems(List<VideoBean> items) {
        this.items = items;
    }
}
