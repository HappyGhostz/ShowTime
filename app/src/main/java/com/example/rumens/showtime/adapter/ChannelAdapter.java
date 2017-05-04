package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.local.NewsTypeInfo;
import com.example.rumens.showtime.news.channe.ChannelActivity;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

public class ChannelAdapter extends BaseQuickAdapter<NewsTypeInfo> {
    public ChannelAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_manage;
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsTypeInfo item) {
        holder.setText(R.id.tv_channel_name,item.getName());
    }
}
