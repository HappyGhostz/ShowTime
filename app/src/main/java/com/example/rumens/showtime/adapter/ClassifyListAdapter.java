package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */

public class ClassifyListAdapter extends BaseQuickAdapter {
    public ClassifyListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {

    }
}
