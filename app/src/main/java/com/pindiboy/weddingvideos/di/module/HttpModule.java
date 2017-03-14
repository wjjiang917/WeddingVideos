package com.pindiboy.weddingvideos.di.module;

import com.pindiboy.weddingvideos.App;
import com.pindiboy.weddingvideos.BuildConfig;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.di.qualifier.ServiceType;
import com.pindiboy.weddingvideos.model.http.api.YouTubeApi;
import com.pindiboy.weddingvideos.util.FileUtil;
import com.pindiboy.weddingvideos.util.PhoneUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Module
public class HttpModule {
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    @ServiceType("YouTube")
    Retrofit provideYouTubeRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, YouTubeApi.HOST);
    }

    @Singleton
    @Provides
    YouTubeApi provideYouTubeService(@ServiceType("YouTube") Retrofit retrofit) {
        return retrofit.create(YouTubeApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!PhoneUtil.isNetworkConnected(App.getInstance())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (PhoneUtil.isNetworkConnected(App.getInstance())) {
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + 0)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);

        File cacheDir = new File(FileUtil.getDiskCacheDir(App.getInstance()) + File.pathSeparator + Constant.CACHE_NET);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        if (cacheDir.exists()) {
            Cache cache = new Cache(cacheDir, 1024 * 1024 * 50); // 50M
            builder.cache(cache);
        }

        //设置超时
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
