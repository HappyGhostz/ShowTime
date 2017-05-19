package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.CommentList;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */

public class BestCommentListAdapter extends BaseQuickAdapter<CommentList.CommentsBean>{


    public BestCommentListAdapter(Context context) {
        super(context);
    }

    public BestCommentListAdapter(Context context, List<CommentList.CommentsBean> mBestCommentList) {
        super(context, mBestCommentList);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_book_help_detial_best;
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentList.CommentsBean item) {
        CircleImageView imagView = holder.getView(R.id.ivBookCover);
        ImageLoader.loadCenterCrop(mContext, Constant.IMG_BASE_URL + item.author.avatar,imagView,R.mipmap.avatar_default);
        holder .setText(R.id.tvBookTitle, item.author.nickname)
                .setText(R.id.tvContent, item.content)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item.floor))
                .setText(R.id.tvLikeCount, String.format(mContext.getString(R.string.comment_like_count), item.likeCount));
    }
}
