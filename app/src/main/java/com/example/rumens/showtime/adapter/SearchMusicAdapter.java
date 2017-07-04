package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.SearchMusic;
import com.example.rumens.showtime.music.searchmusic.SearchMusicPlayActivity;
import com.example.rumens.showtime.utils.ImageLoader;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */

public class SearchMusicAdapter extends BaseQuickAdapter<SearchMusic.ResultBean.SongsBean> {
    public SearchMusicAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_music_search_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final SearchMusic.ResultBean.SongsBean item) {
        ImageView imgAlbum = holder.getView(R.id.img_album);
        ImageLoader.displayRound(mContext,imgAlbum,item.getAlbum().getPicUrl());
        holder.setText(R.id.music_name,item.getName())
                .setText(R.id.tv_album,item.getArtists().get(0).getName());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SearchMusicPlayActivity.lunch(mContext,item.getName(),item.getPage());
            }
        });
    }
}
