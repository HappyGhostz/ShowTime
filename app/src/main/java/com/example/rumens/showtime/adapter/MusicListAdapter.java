package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.WrapperSongListInfo;
import com.example.rumens.showtime.utils.DefIconFactory;
import com.example.rumens.showtime.utils.ImageLoader;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicListAdapter extends BaseQuickAdapter<WrapperSongListInfo.SongListInfo> {
    public MusicListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_music_list_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, WrapperSongListInfo.SongListInfo item) {
        int count = Integer.parseInt(item.getListenum());
        if(count>10000){
            count = count / 10000;
            holder.setText(R.id.tv_songlist_count,count+"万");
        }else {
            holder.setText(R.id.tv_songlist_count,item.getListenum());
        }
        holder.setText(R.id.tv_songlist_name,item.getTitle());
        ImageView imageView=holder.getView(R.id.iv_songlist_photo);
        ImageLoader.loadCenterCrop(mContext,item.getPic_300(),imageView, DefIconFactory.provideIcon());
    }


}
