package com.example.rumens.showtime.home;


import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.music.MusicFragment;
import com.example.rumens.showtime.news.main.NewsMainFragment;
import com.example.rumens.showtime.reader.ReaderFragment;
import com.example.rumens.showtime.video.VideoFragment;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/4/19
 * @description
 */

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.status_hight)
    TextView mTvStatusHight;
    private DrawerLayout mDrawLayout;
    private FrameLayout mFlContent;
    private NavigationView mNvLeft;

    private final int[] pressedResId = new int[]{
            R.drawable.sel_new_hometab, R.drawable.sel_videro_hometab, R.drawable.sel_music_hometab, R.drawable.sel_reader_hometab
    };
    private static final String[] mDataList = new String[]{"新闻", "视频", "音乐", "阅读"};
    private RadioGroup mRgTab;
    private long mExitTime = 0;

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {

        mDrawLayout = (DrawerLayout) findViewById(R.id.dl_root);
        mFlContent = (FrameLayout) findViewById(R.id.fl_rightcontent);
        mNvLeft = (NavigationView) findViewById(R.id.nv_left);
        mRgTab = (RadioGroup) findViewById(R.id.rg_Tab);
        int statusBarHeight1 = -1;
       //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        mTvStatusHight.setHeight(statusBarHeight1);

        initFragment();
        initData();
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(R.id.fl_rightcontent, new NewsMainFragment(), "News");
        fragmentTransaction.addToBackStack("News");
        fragmentTransaction.commit();
    }

    private void initData() {

        initDrawLayout(mDrawLayout, mNvLeft);
        mRgTab.check(R.id.rb_new);
        initRadioButton();


    }

    private void initRadioButton() {
        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_new:
                        replaceFragment(R.id.fl_rightcontent, new NewsMainFragment(), mDataList[0]);
                        break;
                    case R.id.rb_video:
                        replaceFragment(R.id.fl_rightcontent, new VideoFragment(), mDataList[1]);
                        break;
                    case R.id.rb_music:
                        replaceFragment(R.id.fl_rightcontent, new MusicFragment(), mDataList[2]);
                        break;
                    case R.id.rb_book:
                        replaceFragment(R.id.fl_rightcontent, new ReaderFragment(), mDataList[3]);
                        break;
                }
            }
        });
    }

    private void initDrawLayout(DrawerLayout mDrawLayout, NavigationView mNvLeft) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            mDrawLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            mDrawLayout.setClipToPadding(false);
        }
        mDrawLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
//                mHandler.sendEmptyMessage(mCuntItem);
            }
        });
        mNvLeft.setNavigationItemSelectedListener(this);
    }


    @Override
    protected int getContenView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawLayout.closeDrawer(Gravity.START);
        if (item.isChecked()) {
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawLayout.openDrawer(Gravity.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // 获取堆栈里有几个
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (mDrawLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawLayout.closeDrawer(GravityCompat.START);
        } else if (stackEntryCount == 1) {
            // 如果剩一个说明在主页，提示按两次退出app
            exit();
        }
        super.onBackPressed();
    }

    /**
     * 退出
     */
    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
