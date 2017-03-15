package com.pindiboy.weddingvideos.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;
import com.pindiboy.weddingvideos.FullScreenManager;
import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.common.Constant;

/**
 * Created by Jiangwenjin on 2017/3/15.
 */

public class PlayerActivity extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    private FullScreenManager fullScreenManager;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        videoId = getIntent().getStringExtra(Constant.INTENT_EXTRA_VIDEO_ID);

        fullScreenManager = new FullScreenManager(this);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
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

        findViewById(R.id.next_video_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.loadVideo("LvetJ9U_tVY", 0);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        youTubePlayerView.release();
    }
}
