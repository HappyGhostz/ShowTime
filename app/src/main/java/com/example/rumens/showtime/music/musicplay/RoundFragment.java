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
                ImageLoader.displayRoundRes(mContext,mCivPhoto,R.mipmap.muiscderault);
            }else {
                ImageLoader.displayRound(mContext,mCivPhoto,picUrl);
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
