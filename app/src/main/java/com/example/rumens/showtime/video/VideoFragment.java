package com.example.rumens.showtime.video;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.video.kankan.VideoMainFragment;
import com.example.rumens.showtime.video.live.LiveListFragment;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/4/18
 * @description
 */

public class VideoFragment extends BaseFragment {

    @BindView(R.id.live_container)
    FrameLayout mLayoutContainer;
    @BindView(R.id.rg_Tab)
    RadioGroup mRgTab;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initToolBar(mToolBar, true, "");
        setHasOptionsMenu(true);

        mRgTab.check(R.id.rb_video);
        initRgTab();
    }

    private void initRgTab() {
        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_live:
                        ireplaceFragment(R.id.live_container, new LiveListFragment(), "live");
                        break;
                    case R.id.rb_video:
                        ireplaceFragment(R.id.live_container, new VideoMainFragment(), "kankan");
                        break;
                }
            }
        });
    }

    private void initFragment(int resID, Fragment Fragment, String live) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(resID, Fragment, live);
        fragmentTransaction.addToBackStack("live");
        fragmentTransaction.commit();
    }
    private void ireplaceFragment(int resID, Fragment Fragment, String live) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.replace(resID, Fragment, live);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    protected void updateViews() {
        initFragment(R.id.live_container, new VideoMainFragment(), "kankan");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_channel,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item_channel){
            Toast.makeText(getActivity(),"被电击到了",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
