package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.listpop.abslistview.EasyLVAdapter;
import com.example.rumens.showtime.adapter.listpop.abslistview.EasyLVHolder;
import com.example.rumens.showtime.entity.ReadTheme;
import com.example.rumens.showtime.utils.ThemeManager;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class ReadThemeAdapter extends EasyLVAdapter<ReadTheme> {
    private int selected = 0;

    public ReadThemeAdapter(Context context, List<ReadTheme> list, int selected) {
        super(context, list, R.layout.adapter_item_read_theme);
        this.selected = selected;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, ReadTheme readTheme) {
        if (readTheme != null) {
            ThemeManager.setReaderTheme(readTheme.theme, holder.getView(R.id.ivThemeBg));
            if (selected == position) {
                holder.setVisible(R.id.ivSelected, true);
            } else {
                holder.setVisible(R.id.ivSelected, false);
            }
        }
    }

    public void select(int position) {
        selected = position;
        notifyDataSetChanged();
    }
}
