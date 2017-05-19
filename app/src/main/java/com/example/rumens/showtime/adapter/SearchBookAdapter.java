package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.SearchDetail;
import com.example.rumens.showtime.reader.bookdetail.BookDetailActivity;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.ImageLoader;

/**
 * @author Zhaochen Ping
 * @create 2017/5/18
 * @description
 */

public class SearchBookAdapter extends BaseQuickAdapter<SearchDetail.SearchBooks> {
    public SearchBookAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_search_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final SearchDetail.SearchBooks item) {
        ImageView imageView = holder.getView(R.id.ivBookCover);
        ImageLoader.loadCenterCrop(mContext, Constant.IMG_BASE_URL + item.cover,imageView,R.drawable.cover_default);
        holder.setText(R.id.tvBookListTitle, item.title)
                .setText(R.id.tvLatelyFollower, String.format(mContext.getString(R.string.search_result_lately_follower), item.latelyFollower))
                .setText(R.id.tvRetentionRatio, (TextUtils.isEmpty(item.retentionRatio) ? String.format(mContext.getString(R.string.search_result_retention_ratio), "0")
                        : String.format(mContext.getString(R.string.search_result_retention_ratio), item.retentionRatio)))
                .setText(R.id.tvBookListAuthor, String.format(mContext.getString(R.string.search_result_author), item.author));
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.lunchForNewTask(mContext,item._id);
            }
        });
    }
}
