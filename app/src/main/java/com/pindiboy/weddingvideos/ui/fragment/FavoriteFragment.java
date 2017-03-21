package com.pindiboy.weddingvideos.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.youtube.Snippet;
import com.pindiboy.weddingvideos.presenter.FavoritePresenter;
import com.pindiboy.weddingvideos.presenter.contract.FavoriteContract;
import com.pindiboy.weddingvideos.ui.BaseFragment;
import com.pindiboy.weddingvideos.ui.activity.PlayerActivity;
import com.pindiboy.weddingvideos.ui.adapter.FavoriteAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public class FavoriteFragment extends BaseFragment<FavoritePresenter> implements FavoriteContract.View {
    @BindView(R.id.rv_video_list)
    RecyclerView rvVideoList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private FavoriteAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        mAdapter = new FavoriteAdapter(null);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvVideoList.setAdapter(mAdapter);

        rvVideoList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_VIDEO_ID, mAdapter.getData().get(position).getVideoId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "video_item_view");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });
        rvVideoList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.video_favorite_btn:
                        Snippet video = mAdapter.getData().get(position);
                        if (video.isFavourite()) {
                            video.setFavourite(false);
                            mPresenter.removeFavorite(video.getVideoId());
                            ((ImageView) view).setImageResource(R.drawable.ic_favorite_border_red_24dp);
                        } else {
                            video.setFavourite(true);
                            mPresenter.addFavorite(video);
                            ((ImageView) view).setImageResource(R.drawable.ic_favorite_red_24dp);
                        }
                        break;
                }
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getFavorite();
            }
        });

        // swipe and drag
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvVideoList);

        // open drag
        mAdapter.enableDragItem(itemTouchHelper, R.id.video_list_item, true);
        mAdapter.setOnItemDragListener(onItemDragListener);

        // open slide to delete
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);

        mPresenter.getFavorite();
    }

    @Override
    public void onFavoriteLoaded(List<Snippet> videos) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }

        mAdapter.setNewData(videos);
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        }
    };

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };
}
