package com.example.rumens.showtime.adapter;

import android.content.Context;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.RankingListDetail;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicRankPlayListAdapter extends BaseQuickAdapter<RankingListDetail.SongListBean> {
    public MusicRankPlayListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_rank_play_list_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, RankingListDetail.SongListBean item) {
        holder.setText(R.id.tv_trackNumber,holder.getAdapterPosition()+"")
                .setText(R.id.tv_song_title,item.getTitle())
                .setText(R.id.tv_song_artist,item.getAuthor());

    }
}
