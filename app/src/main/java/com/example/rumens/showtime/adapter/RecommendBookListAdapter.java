package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.RecommendBookList;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.utils.SettingManager;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class RecommendBookListAdapter extends BaseQuickAdapter<RecommendBookList.RecommendBook>{

    public RecommendBookListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_book_detail_recommendbooklist;
    }

    @Override
    protected void convert(BaseViewHolder holder, RecommendBookList.RecommendBook item) {
        ImageView imageView =  holder.getView(R.id.ivBookListCover);
        if (!SettingManager.getInstance().isNoneCover()) {
            ImageLoader.loadCenterCrop(mContext, Constant.IMG_BASE_URL + item.cover,imageView,R.mipmap.avatar_default);
        }

        holder.setText(R.id.tvBookListTitle, item.title)
                .setText(R.id.tvBookAuthor, item.author)
                .setText(R.id.tvBookListTitle, item.title)
                .setText(R.id.tvBookListDesc, item.desc)
                .setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .book_detail_recommend_book_list_book_count), item.bookCount))
                .setText(R.id.tvCollectorCount, String.format(mContext.getString(R.string
                        .book_detail_recommend_book_list_collector_count), item.collectorCount));
    }
}
