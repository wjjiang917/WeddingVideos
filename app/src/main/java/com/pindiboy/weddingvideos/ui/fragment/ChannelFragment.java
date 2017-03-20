package com.pindiboy.weddingvideos.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.YouTubeBean;
import com.pindiboy.weddingvideos.presenter.ChannelPresenter;
import com.pindiboy.weddingvideos.presenter.contract.ChannelContract;
import com.pindiboy.weddingvideos.ui.BaseFragment;
import com.pindiboy.weddingvideos.ui.activity.PlayerActivity;
import com.pindiboy.weddingvideos.ui.adapter.VideoListAdapter;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public class ChannelFragment extends BaseFragment<ChannelPresenter> implements ChannelContract.View {
    @BindView(R.id.rv_video_list)
    RecyclerView rvVideoList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private String channelId;
    private VideoListAdapter mAdapter;
    private boolean loadMore = false;
    private String pageToken = ""; // for pagination

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            channelId = getArguments().getString(Constant.BUNDLE_CHANNEL_ID);
        }

        mAdapter = new VideoListAdapter(null);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvVideoList.setAdapter(mAdapter);

        rvVideoList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_VIDEO_ID, mAdapter.getData().get(position).getId().getVideoId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "video_item_view");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore = true;
                mPresenter.getChannelVideos(channelId, pageToken);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMore = false;
                pageToken = "";
                mPresenter.getChannelVideos(channelId, pageToken);
            }
        });

        mPresenter.getChannelVideos(channelId, pageToken);
    }

    @Override
    public void onChannelVideosLoaded(YouTubeBean<ItemId> youTubeBean) {
        mAdapter.loadMoreComplete();
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }

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
}
