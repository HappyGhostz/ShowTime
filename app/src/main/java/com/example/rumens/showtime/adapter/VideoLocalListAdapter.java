package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.utils.StringUtils;

import io.vov.vitamio.provider.MediaStore;


/**
 * Created by e445 on 2017/5/8.
 */

public class VideoLocalListAdapter extends CursorAdapter {
    public VideoLocalListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //当convertview == null的时候 走这个方法 这里把xml文件转换成View对象创建条目布局
        View view = View.inflate(context, R.layout.item_video, null);
        ViewHolder holder = new ViewHolder();
        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        holder.tv_time = (TextView) view.findViewById(R.id.tv_duration);
        holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //给条目设置数据  00:00:00
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tv_title.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
        String result = StringUtils.formatTime(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
        holder.tv_time.setText(result);
        holder.tv_size.setText(Formatter.formatFileSize(context,cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))));
    }
    private static class ViewHolder{
        TextView tv_title;
        TextView tv_time;
        TextView tv_size;

    }
}
