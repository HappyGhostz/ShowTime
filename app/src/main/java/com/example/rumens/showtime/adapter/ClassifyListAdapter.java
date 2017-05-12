package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.CategoryList;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */

public class ClassifyListAdapter extends BaseQuickAdapter< CategoryList.MaleBean> {
    public ClassifyListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_classify;
    }

    @Override
    protected void convert(BaseViewHolder holder,  CategoryList.MaleBean item) {
        holder.setText(R.id.tvName, item.name)
                .setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .category_book_count), item.bookCount));
    }
}
