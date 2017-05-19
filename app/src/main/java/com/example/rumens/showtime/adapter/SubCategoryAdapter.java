package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.reader.bookdetail.BookDetailActivity;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.utils.SettingManager;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class SubCategoryAdapter extends BaseQuickAdapter<BooksByCats.BooksBean> {
    public SubCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_sub_category;
    }

    @Override
    protected void convert(BaseViewHolder holder, final BooksByCats.BooksBean item) {
        ImageView imageView = holder.getView(R.id.ivSubCateCover);
        if (!SettingManager.getInstance().isNoneCover()) {
            ImageLoader.loadCenterCrop(mContext,Constant.IMG_BASE_URL + item.cover,imageView, R.drawable.cover_default);
        } else {
            imageView.setImageResource(R.drawable.cover_default);
        }

        holder.setText(R.id.tvSubCateTitle, item.title)
                .setText(R.id.tvSubCateAuthor, (item.author == null ? "未知" : item.author) + " | " + (item.majorCate == null ? "未知" : item.majorCate))
                .setText(R.id.tvSubCateShort, item.shortIntro)
                .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.category_book_msg),
                        item.latelyFollower,
                        TextUtils.isEmpty(item.retentionRatio) ? "0" : item.retentionRatio));
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.lunch(mContext,item._id);
            }
        });
    }

}
