package com.example.rumens.showtime.music.musicplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.widget.CircleImageView;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/6/3
 * @description
 */

public class RoundFragment extends BaseFragment {
    @BindView(R.id.civ_photo)
    CircleImageView mCivPhoto;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_music_play_round;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String picUrl = bundle.getString("picUrl");
            boolean isLocal = bundle.getBoolean(MusicPlay.LOCAL_IS);
            if(isLocal){
//                ImageLoader.loadResGif(mContext, R.mipmap.localgif,mCivPhoto);
                String local = "http://image.so.com/v?ie=utf-8&src=hao_360so&q=音乐专辑&correct=音乐专辑&cmsid=e7ba6c4f17314a9d674eca4b17d7aab7&cmran=0&cmras=0&gsrc=1#ie=utf-8&src=hao_360so&q=%E9%9F%B3%E4%B9%90%E4%B8%93%E8%BE%91&correct=%E9%9F%B3%E4%B9%90%E4%B8%93%E8%BE%91&gsrc=1&lightboxindex=43&id=39e8b68fdf4bd92b34f742211aeb2072&multiple=0&itemindex=0&dataindex=43&currsn=0";
                ImageLoader.loadUrlGif(mContext,local,mCivPhoto);
            }else {
                ImageLoader.loadCenterCrop(mContext,picUrl,mCivPhoto,R.mipmap.muiscderault);
            }
        }
    }

    @Override
    protected void updateViews() {

    }

    public static Fragment lunch(Context context, boolean isLocal, String picUrl) {
        RoundFragment fragment = new RoundFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MusicPlay.LOCAL_IS, isLocal);
        bundle.putString("picUrl", picUrl);
        fragment.setArguments(bundle);
        return fragment;
    }
}
