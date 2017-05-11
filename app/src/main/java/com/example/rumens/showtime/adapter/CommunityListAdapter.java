package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.BookHelpList;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FormatUtils;
import com.example.rumens.showtime.utils.ImageLoader;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */

public class CommunityListAdapter extends BaseQuickAdapter<BookHelpList.HelpsBean> {
    public CommunityListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_community_help;
    }

    @Override
    protected void convert(BaseViewHolder holder, BookHelpList.HelpsBean item) {
        String latelyUpdate = "";
        if (!TextUtils.isEmpty(FormatUtils.getDescriptionTimeFromDateString(item.updated))) {
            latelyUpdate = FormatUtils.getDescriptionTimeFromDateString(item.updated) + ":";
        }
        holder.setText(R.id.tvBookTitle,item.author.nickname)
                .setText(R.id.tvBookType,"lv."+item.author.lv+"")
                .setText(R.id.tvTitle,item.title)
                .setText(R.id.tvHelpfulYes,item.commentCount+"");
        if(TextUtils.equals(item.state,"distillate")){
            TextView tvDistillate = holder.getView(R.id.tvDistillate);
            tvDistillate.setVisibility(View.VISIBLE);
        }else if(TextUtils.equals(item.state,"hot")){
            TextView tvHot = holder.getView(R.id.tvHot);
            tvHot.setVisibility(View.VISIBLE);
        }else if(TextUtils.equals(item.state,"normal")){
            TextView tvTime = holder.getView(R.id.tvTime);
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(latelyUpdate);
        }
        ImageView imageView = holder.getView(R.id.ivBookCover);
        ImageLoader.loadCenterCrop(mContext, Constant.IMG_BASE_URL + item.author.avatar,imageView,R.mipmap.avatar_default);
    }
}
