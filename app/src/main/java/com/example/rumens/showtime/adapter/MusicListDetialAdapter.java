package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.SongListDetail;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class MusicListDetialAdapter extends BaseQuickAdapter<SongListDetail.SongDetail> {
    public MusicListDetialAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_music_list_play_item;
    }
//    @Override
//    protected int attachLayoutRes() {
//        return R.layout.test_adapter;
//    }

    @Override
    protected void convert(BaseViewHolder holder, SongListDetail.SongDetail item) {
        holder.setText(R.id.tv_trackNumber,holder.getAdapterPosition()+"")
                .setText(R.id.tv_song_title,item.getTitle())
                .setText(R.id.tv_song_artist,item.getAuthor());
    }
}
