package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/27
 * @description
 */

public class AlertDialogAdapter extends BaseQuickAdapter<String>{
    public AlertDialogAdapter(Context context, List<String> typeNameList) {
        super(context,typeNameList);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_live_type_picker_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.tv_platform, item);//昵称
        holder.setTextColor(R.id.tv_platform, ContextCompat.getColor(mContext, R.color.color_secondary_text));
    }

}
