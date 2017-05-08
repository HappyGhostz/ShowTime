package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;

/**
 * @author Zhaochen Ping
 * @create 2017/5/8
 * @description
 */

public class VideoListAdapter extends BaseQuickAdapter {
    private String mVideoType;

    public VideoListAdapter(Context context, String mVideoType) {
        super(context);
        this.mVideoType = mVideoType;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.test_adapter;
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"哼-o-,被电了",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
