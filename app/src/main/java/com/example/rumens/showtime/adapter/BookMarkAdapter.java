package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.listpop.abslistview.EasyLVAdapter;
import com.example.rumens.showtime.adapter.listpop.abslistview.EasyLVHolder;
import com.example.rumens.showtime.api.bean.BookMark;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class BookMarkAdapter extends EasyLVAdapter<BookMark> {
    public BookMarkAdapter(Context context, List<BookMark> list) {
        super(context, list, R.layout.adapter_item_read_mark);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, BookMark item) {
        TextView tv = holder.getView(R.id.tvMarkItem);

        SpannableString spanText = new SpannableString((position + 1) + ". " + item.title + ": ");
        spanText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.light_coffee)),
                0, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        tv.setText(spanText);

        if (item.desc != null) {
            tv.append(item.desc
                    .replaceAll("　", "")
                    .replaceAll(" ", "")
                    .replaceAll("\n", ""));
        }

    }
}
