package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.CommentList;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FormatUtils;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.utils.SettingManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */

public class BookHelpDetialAdapter extends BaseQuickAdapter<CommentList.CommentsBean> {
    public BookHelpDetialAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_book_help_comment_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentList.CommentsBean item) {
        CircleImageView circleImageView =  holder.getView(R.id.ivBookCover);
        if (!SettingManager.getInstance().isNoneCover()) {
            ImageLoader.loadCenterCrop(mContext,Constant.IMG_BASE_URL + item.author.avatar,circleImageView,R.mipmap.avatar_default);
        } else {
            holder.setImageResource(R.id.ivBookCover, R.mipmap.avatar_default);
        }

        holder.setText(R.id.tvBookTitle, item.author.nickname)
                .setText(R.id.tvContent, item.content)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item.floor))
                .setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created));

        if (item.replyTo == null) {
            holder.setVisible(R.id.tvReplyNickName, false);
            holder.setVisible(R.id.tvReplyFloor, false);
        } else {
            holder.setText(R.id.tvReplyNickName, String.format(mContext.getString(R.string.comment_reply_nickname), item.replyTo.author.nickname))
                    .setText(R.id.tvReplyFloor, String.format(mContext.getString(R.string.comment_reply_floor), item.replyTo.floor));
            holder.setVisible(R.id.tvReplyNickName, true);
            holder.setVisible(R.id.tvReplyFloor, true);
        }
    }

}
