package com.example.rumens.showtime.music.localmusic;

import android.support.v4.app.Fragment;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.base.BaseFragment;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicLocalFragment extends BaseFragment{
    @Override
    protected int attachLayoutRes() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {

    }

    public static Fragment lunch() {
        MusicLocalFragment fragment = new MusicLocalFragment();
        return fragment;
    }
}
