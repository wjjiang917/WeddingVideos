package com.pindiboy.weddingvideos.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.model.bean.youtube.Item;
import com.pindiboy.weddingvideos.model.bean.youtube.ItemId;
import com.pindiboy.weddingvideos.model.bean.youtube.Thumbnail;
import com.pindiboy.weddingvideos.util.ImageUtil;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/15.
 */

public class VideoListAdapter extends BaseQuickAdapter<Item<ItemId>, BaseViewHolder> {
    public VideoListAdapter(List<Item<ItemId>> data) {
        super(R.layout.item_video_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Item<ItemId> item) {
        ImageUtil.load(mContext, item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl(), (ImageView) helper.getView(R.id.video_image));
        helper.setText(R.id.video_title, item.getSnippet().getTitle());
        helper.setText(R.id.video_channel_title, item.getSnippet().getChannelTitle());
        helper.setText(R.id.video_info, item.getSnippet().getPublishedAt());
    }
}
