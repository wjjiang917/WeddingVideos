package com.pindiboy.weddingvideos.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.ChannelBean;
import com.pindiboy.weddingvideos.model.bean.VideoBean;
import com.pindiboy.weddingvideos.presenter.ChannelPresenter;
import com.pindiboy.weddingvideos.presenter.contract.ChannelContract;
import com.pindiboy.weddingvideos.ui.BaseFragment;
import com.pindiboy.weddingvideos.ui.adapter.VideoListAdapter;

import java.util.List;

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
    private List<VideoBean> mVideos;
    private VideoListAdapter mAdapter;
    private boolean loadMore = false;

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

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getChannelVideos(channelId);
            }
        });

        mAdapter = new VideoListAdapter(mVideos);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvVideoList.setAdapter(mAdapter);

        mPresenter.getChannelVideos(channelId);
    }

    @Override
    public void onChannelVideosLoaded(ChannelBean channelBean) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }

        mVideos = channelBean.getItems();
        if (loadMore) {
            mAdapter.addData(mVideos);
        } else {
            mAdapter.setNewData(mVideos);
        }
    }
}
