package com.pindiboy.weddingvideos.ui.activity;

import android.content.Intent;

import com.google.gson.Gson;
import com.pindiboy.shadiraat.R;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.ChannelConfig;
import com.pindiboy.weddingvideos.presenter.SplashPresenter;
import com.pindiboy.weddingvideos.presenter.contract.SplashContract;
import com.pindiboy.weddingvideos.ui.BaseActivity;
import com.pindiboy.weddingvideos.util.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/24.
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        // check storage permission
        mPresenter.checkPermissions(new RxPermissions(this));

        mPresenter.getChannels();
    }

    @Override
    public void onChannelsLoaded(List<ChannelConfig> channels) {
        goMain(new Gson().toJson(channels));
    }

    @Override
    public void onChannelsFailed() {
        // read default channels from json file
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = getAssets().open("channels.json");
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            goMain(builder.toString());
        } catch (IOException e) {
            Logger.e("", e);
        } finally {
            try {
                if (null != br) br.close();
                if (null != isr) isr.close();
                if (null != is) is.close();
            } catch (IOException ignore) {
            }
        }
    }

    private void goMain(String channels) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_CHANNELS, channels);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
