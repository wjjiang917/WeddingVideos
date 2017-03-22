package com.pindiboy.weddingvideos.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pindiboy.weddingvideos.FullScreenManager;
import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.youtube.Item;
import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.Snippet;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.presenter.PlayerPresenter;
import com.pindiboy.weddingvideos.presenter.contract.PlayerContract;
import com.pindiboy.weddingvideos.ui.BaseActivity;
import com.pindiboy.weddingvideos.ui.adapter.VideoListAdapter;
import com.pindiboy.weddingvideos.util.DateUtil;
import com.pindiboy.weddingvideos.util.Logger;
import com.pindiboy.weddingvideos.util.NumberUtil;
import com.pindiboy.weddingvideos.util.TipUtil;
import com.youtube.download.DownloadActivity;
import com.youtube.iframeplayer.AbstractYouTubeListener;
import com.youtube.iframeplayer.YouTubePlayerButtonListener;
import com.youtube.iframeplayer.YouTubePlayerFullScreenListener;
import com.youtube.iframeplayer.YouTubePlayerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jiangwenjin on 2017/3/15.
 */
public class PlayerActivity extends BaseActivity<PlayerPresenter> implements PlayerContract.View {
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;
    @BindView(R.id.video_info_title)
    TextView videoTitle;
    @BindView(R.id.video_detail)
    TextView videoDetail;
    @BindView(R.id.video_description)
    TextView videoDesc;
    @BindView(R.id.player_favorite)
    ImageView playerFavorite;
    @BindView(R.id.video_related)
    RecyclerView rvRelated;
    @BindView(R.id.video_related_progress)
    ProgressBar pvRelated;

    private VideoListAdapter mAdapter;
    private boolean loadMore = false;
    private String pageToken = ""; // for pagination
    private FullScreenManager fullScreenManager;
    private String videoId;
    private Snippet video;

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
        mPresenter.getRelatedVideos(videoId, "");

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
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreenManager.exitFullScreen();
            }
        });

        // show quality settings
        youTubePlayerView.addSettingsListener(new YouTubePlayerButtonListener() {
            @Override
            public void onClickSettings() {
                Logger.d("onClickSettings...");
            }
        });

        mAdapter = new VideoListAdapter(null);
        rvRelated.setLayoutManager(new LinearLayoutManager(mContext));
        rvRelated.setAdapter(mAdapter);

        rvRelated.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_VIDEO_ID, mAdapter.getData().get(position).getSnippet().getVideoId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mContext, view, "video_item_view");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
                finish();
            }
        });
        rvRelated.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.video_favorite_btn:
                        Snippet video = mAdapter.getData().get(position).getSnippet();
                        if (video.isFavourite()) {
                            video.setFavourite(false);
                            mPresenter.removeFavorite(video.getVideoId());
                            ((ImageView) view).setImageResource(R.drawable.ic_favorite_border_red_24dp);
                            TipUtil.showToast(mContext, getString(R.string.removed_favorite));
                        } else {
                            video.setFavourite(true);
                            mPresenter.addFavorite(video);
                            ((ImageView) view).setImageResource(R.drawable.ic_favorite_red_24dp);
                            TipUtil.showToast(mContext, getString(R.string.added_favorite));
                        }
                        break;
                }
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore = true;
                mPresenter.getRelatedVideos(videoId, pageToken);
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
        Item<String> item = youTubeBean.getItems().get(0);
        video = item.getSnippet();

        if (item.getSnippet().isFavourite()) {
            playerFavorite.setImageResource(R.drawable.ic_favorite_red_24dp);
        } else {
            playerFavorite.setImageResource(R.drawable.ic_favorite_border_red_24dp);
        }
        playerFavorite.setVisibility(View.VISIBLE);
        videoTitle.setText(item.getSnippet().getTitle());
        videoDesc.setText(item.getSnippet().getDescription());
        videoDetail.setText("Published: " + DateUtil.getNewFormat(item.getSnippet().getPublishedAt(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd MMM, yyyy") + " / Views: " + NumberUtil.format(item.getStatistics().getViewCount()));
    }

    @Override
    public void onRelatedVideosLoaded(YouTubeBean<ItemId> youTubeBean) {
        pvRelated.setVisibility(View.GONE);

        // disable load more
        if (null == youTubeBean.getItems() || youTubeBean.getItems().size() < Constant.CHANNEL_VIDEOS_PAGE_SIZE) {
            mAdapter.loadMoreEnd();
        }

        pageToken = youTubeBean.getNextPageToken();
        if (loadMore) {
            mAdapter.addData(youTubeBean.getItems());
        } else {
            mAdapter.setNewData(youTubeBean.getItems());
        }
    }

    @OnClick(R.id.video_description_btn)
    public void toggleDesc(View view) {
        if (videoDesc.getVisibility() == View.GONE) {
            videoDesc.setVisibility(View.VISIBLE);
            ((ImageView) view).setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        } else {
            videoDesc.setVisibility(View.GONE);
            ((ImageView) view).setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }
    }

    @OnClick(R.id.player_back)
    public void clickBack(View view) {
        finish();
    }

    @OnClick(R.id.player_share)
    public void clickShare(View view) {
    }

    @OnClick(R.id.player_download)
    public void clickDownload(View view) {
        Intent intent = new Intent(mContext, DownloadActivity.class);
        intent.putExtra(DownloadActivity.INTENT_EXTRA_VIDEO_ID, videoId);
        startActivity(intent);
    }

    @OnClick(R.id.player_favorite)
    public void clickFavorite(View view) {
        if (video.isFavourite()) {
            video.setFavourite(false);
            mPresenter.removeFavorite(video.getVideoId());
            ((ImageView) view).setImageResource(R.drawable.ic_favorite_border_red_24dp);
            TipUtil.showToast(mContext, getString(R.string.removed_favorite));
        } else {
            video.setFavourite(true);
            mPresenter.addFavorite(video);
            ((ImageView) view).setImageResource(R.drawable.ic_favorite_red_24dp);
            TipUtil.showToast(mContext, getString(R.string.added_favorite));
        }
    }
}
