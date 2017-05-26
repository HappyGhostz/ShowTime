package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.utils.DefIconFactory;
import com.example.rumens.showtime.utils.ImageLoader;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class MusicLocalListAdapter extends BaseQuickAdapter<SongLocalBean> {
    public MusicLocalListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_local_music_list_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, SongLocalBean item) {
        ImageView imageView = holder.getView(R.id.iv_albumr);
        ImageLoader.loadCenterCrop(mContext,item.albun_id,imageView, DefIconFactory.provideIcon());
        holder.setText(R.id.tv_song_title,item.title)
                .setText(R.id.tv_song_artist,item.artist);
    }
}
