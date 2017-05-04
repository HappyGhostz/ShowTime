package com.example.rumens.showtime.video.live;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.AlertDialogAdapter;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRecyclerViewItemClickListener;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.utils.DefIconFactory;
import com.example.rumens.showtime.utils.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */

public class LiveListFragment extends BaseFragment {
    @BindView(R.id.viewpager)
    ViewPager mLiveViewpager;
    @BindView(R.id.tab_new_layout)
    TabLayout mTabNewLayout;
    @BindView(R.id.tv_live_type)
    TextView mTvLiveType;


    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter mPagerAdapter;
    private final List<String> typeIdList = new ArrayList<>();    //直播平台id
    private final List<String> typeNameList = new ArrayList<>();  //直播平台名字
    private final Integer[] logoArrays = new Integer[]{
            R.mipmap.logo_all,
            R.mipmap.logo_douyu,
            R.mipmap.logo_panda,
            R.mipmap.logo_zhanqi,
            R.mipmap.logo_yy,
            R.mipmap.logo_longzhu,
            R.mipmap.logo_quanmin,
            R.mipmap.logo_cc,
            R.mipmap.logo_huomao
    };
    private int currentPos;
    private int pos=0;
    private boolean isDouyu = false;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_live_list;
    }

    @Override
    protected void initInjector() {
    }

    @Override
    protected void initViews() {
        mPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        //直播平台ID
        Collections.addAll(typeIdList, mContext.getResources().getStringArray(R.array.live_type_id));
        //直播平台名称
        Collections.addAll(typeNameList, mContext.getResources().getStringArray(R.array.live_type_name));
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add(getString(R.string.live_all));
        titles.add(getString(R.string.live_lol));
        titles.add(getString(R.string.live_ow));
        titles.add(getString(R.string.live_dota2));
        titles.add(getString(R.string.live_hs));
        titles.add(getString(R.string.live_csgo));
        fragments = new ArrayList<>();
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_all),typeIdList.get(pos)));
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_lol),typeIdList.get(pos)));
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_ow),typeIdList.get(pos)));
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_dota2),typeIdList.get(pos)));
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_hs),typeIdList.get(pos)));
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_csgo),typeIdList.get(pos)));
    }

    @Override
    protected void updateViews() {
        initData();
        mPagerAdapter.setItems(fragments, titles);
        mLiveViewpager.setAdapter(mPagerAdapter);
        mTabNewLayout.setupWithViewPager(mLiveViewpager);
    }

    @OnClick(R.id.tv_live_type)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View dialogView = View.inflate(mContext,R.layout.layout_dialog,null);
        final ImageView typeLogo= (ImageView) dialogView.findViewById(R.id.live_type_picker_logo);
        RecyclerView typeName = (RecyclerView) dialogView.findViewById(R.id.layout_live_type_picker_recyclerview);
        AlertDialogAdapter mAdapter = new AlertDialogAdapter(mContext,typeNameList);
        RecyclerViewHelper.initRecyclerViewG(mContext,typeName,mAdapter,3);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageLoader.loadResCenterCrop(mContext,logoArrays[position],typeLogo);
                typeLogo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                currentPos = position;
            }
        });
        builder.setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pos = currentPos;
                        mTvLiveType.setText(typeNameList.get(pos));
                        updateViews();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.color_primary));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.color_secondary_text));
    }
}
