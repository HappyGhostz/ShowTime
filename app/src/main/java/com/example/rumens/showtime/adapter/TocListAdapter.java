package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.listpop.abslistview.EasyLVAdapter;
import com.example.rumens.showtime.adapter.listpop.abslistview.EasyLVHolder;
import com.example.rumens.showtime.api.bean.BookMixATocBean;
import com.example.rumens.showtime.utils.FileUtils;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class TocListAdapter extends EasyLVAdapter<BookMixATocBean.mixToc.Chapters> {
    private final String mBookId;
    private int currentChapter;
    private boolean isEpub = false;

    public TocListAdapter(Context context, List<BookMixATocBean.mixToc.Chapters> mChapterList, String mBookId, int currentChapter) {
        super(context, mChapterList, R.layout.adapter_book_read_toc_list);
        this.mBookId = mBookId;
        this.currentChapter = currentChapter;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, BookMixATocBean.mixToc.Chapters chapters) {
        TextView tvTocItem = holder.getView(R.id.tvTocItem);
        tvTocItem.setText(chapters.title);
        Drawable drawable;
        if (currentChapter == position + 1) {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_red));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_activated);
        } else if (isEpub || FileUtils.getChapterFile(mBookId, position + 1).length() > 10) {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_download);
        } else {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_normal);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvTocItem.setCompoundDrawables(drawable, null, null, null);
    }

    public void setCurrentChapter(int chapter) {
        currentChapter = chapter;
        notifyDataSetChanged();
    }

    public void setEpub(boolean isEpub) {
        this.isEpub = isEpub;
    }

}
