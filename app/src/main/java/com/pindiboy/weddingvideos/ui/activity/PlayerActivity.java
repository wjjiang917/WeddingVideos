package com.pindiboy.weddingvideos.ui.activity;

import android.content.pm.ActivityInfo;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.pindiboy.weddingvideos.FullScreenManager;
import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.presenter.PlayerPresenter;
import com.pindiboy.weddingvideos.presenter.contract.PlayerContract;
import com.pindiboy.weddingvideos.ui.BaseActivity;
import com.pindiboy.weddingvideos.util.Logger;
import com.youtube.iframeplayer.AbstractYouTubeListener;
import com.youtube.iframeplayer.YouTubePlayerButtonListener;
import com.youtube.iframeplayer.YouTubePlayerFullScreenListener;
import com.youtube.iframeplayer.YouTubePlayerView;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/15.
 */

public class PlayerActivity extends BaseActivity<PlayerPresenter> implements PlayerContract.View {
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;

    private FullScreenManager fullScreenManager;
    private String videoId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_player;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        videoId = getIntent().getStringExtra(Constant.INTENT_EXTRA_VIDEO_ID);
        fullScreenManager = new FullScreenManager(this);

        // get video detail
        mPresenter.getVideoDetail(videoId);

        youTubePlayerView.initialize(new AbstractYouTubeListener() {
            @Override
            public void onReady() {
                youTubePlayerView.loadVideo(videoId, 0);
            }
        }, true);

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullScreenManager.enterFullScreen();

                youTubePlayerView.setCustomActionRight(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_pause_36dp), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        youTubePlayerView.pauseVideo();
                    }
                });
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreenManager.exitFullScreen();

                youTubePlayerView.setCustomActionRight(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_pause_36dp), null);
            }
        });

        // show quality settings
        youTubePlayerView.addSettingsListener(new YouTubePlayerButtonListener() {
            @Override
            public void onClickSettings() {
                Logger.d("onClickSettings...");
            }
        });
    }

    @Override
    public void onDestroy() {
        if (null != youTubePlayerView) {
            youTubePlayerView.release();
        }
        super.onDestroy();
    }

    @Override
    public void onVideoDetailLoaded(YouTubeBean<String> youTubeBean) {

    }
}
