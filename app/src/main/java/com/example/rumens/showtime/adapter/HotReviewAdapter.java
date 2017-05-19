package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.HotReview;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.widget.XLHRatingBar;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class HotReviewAdapter extends BaseQuickAdapter<HotReview.Reviews> {

    public HotReviewAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_book_detail_hotreview;
    }

    @Override
    protected void convert(BaseViewHolder holder, HotReview.Reviews item) {
        ImageView imageView = holder.getView(R.id.ivBookCover);
        ImageLoader.loadCenterCrop(mContext, Constant.IMG_BASE_URL + item.author.avatar,imageView, R.mipmap.avatar_default);
        holder.setText(R.id.tvBookTitle, item.author.nickname)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string
                        .book_detail_user_lv), item.author.lv))
                .setText(R.id.tvTitle, item.title)
                .setText(R.id.tvContent, String.valueOf(item.content))
                .setText(R.id.tvHelpfulYes, String.valueOf(item.helpful.yes));
        XLHRatingBar ratingBar = holder.getView(R.id.rating);
        ratingBar.setCountSelected(item.rating);
    }
}
