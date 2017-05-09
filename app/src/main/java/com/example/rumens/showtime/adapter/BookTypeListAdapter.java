package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class BookTypeListAdapter extends BaseQuickAdapter {
    private String mBookListType;

    public BookTypeListAdapter(Context context, String mBookListType) {
        super(context);
        this.mBookListType = mBookListType;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.test_fragment;
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {

    }
}
