package com.pindiboy.weddingvideos.model.http.api;

import com.pindiboy.weddingvideos.model.bean.IpInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public interface IpApi {
    String HOST = "http://ip-api.com/";

    @GET("json")
    Observable<IpInfo> getIpInfo();
}
