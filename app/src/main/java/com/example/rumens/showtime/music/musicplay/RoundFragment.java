package com.example.rumens.showtime.music.musicplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Zhaochen Ping
 * @create 2017/6/3
 * @description
 */

public class RoundFragment extends BaseFragment {
    @BindView(R.id.civ_photo)
    CircleImageView mCivPhoto;
    @BindView(R.id.rotate_image)
    ImageView mRotateImage;
    private Animation mRotateAnimation;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_music_play_round;
    }

    @Override
    protected void initInjector() {
//        EventBus.getDefault().register(getContext());
    }

    @Override
    protected void initViews() {

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String picUrl = bundle.getString("picUrl");
            boolean isLocal = bundle.getBoolean(MusicPlay.LOCAL_IS);
            if (isLocal) {
                ImageLoader.displayRoundRes(mContext, mCivPhoto, R.mipmap.muiscderault);
            } else {
                ImageLoader.displayRound(mContext, mCivPhoto, picUrl);
            }
            mRotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            LinearInterpolator lin = new LinearInterpolator();
            mRotateAnimation.setInterpolator(lin);
            mRotateImage.startAnimation(mRotateAnimation);
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

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void startAndStopAnimation(String message){
//        if(message.equals("play")){
//            if(mRotateAnimation!=null){
//                mRotateImage.startAnimation(mRotateAnimation);
//            }
//        }else if(message.equals("pause")){
//            mRotateImage.clearAnimation();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }
}
