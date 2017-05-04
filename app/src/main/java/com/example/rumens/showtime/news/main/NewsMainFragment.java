package com.example.rumens.showtime.news.main;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IRxBusPresenter;
import com.example.rumens.showtime.inject.component.DaggerNewsListComponent;
import com.example.rumens.showtime.inject.modules.NewsListModule;
import com.example.rumens.showtime.local.NewsTypeInfo;
import com.example.rumens.showtime.news.channe.ChannelActivity;
import com.example.rumens.showtime.news.onenews.NewsListFragment;
import com.example.rumens.showtime.news.onenews.TestFragment;
import com.example.rumens.showtime.rxBus.event.ChannelEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * @author Zhaochen Ping
 * @create 2017/4/18
 * @description
 */

public class NewsMainFragment extends BaseFragment<IRxBusPresenter> implements INewsView {
    @BindView(R.id.tab_new_layout)
    TabLayout mTabNewLayout;
    @BindView(R.id.new_vp)
    ViewPager mNewVp;

    @Inject
    ViewPagerAdapter mPagerAdapter;

    @BindView(R.id.tool_bar)
    Toolbar mNewToolbar;


    @Override
    protected void initInjector() {
        DaggerNewsListComponent.builder()
                .appComponent(getAppComponent())
                .newsListModule(new NewsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(mNewToolbar, true, "新闻");
        setHasOptionsMenu(true);
        mNewVp.setAdapter(mPagerAdapter);
        mTabNewLayout.setupWithViewPager(mNewVp);
        mPresenter.registerRxBus(ChannelEvent.class, new Action1<ChannelEvent>() {
            @Override
            public void call(ChannelEvent channelEvent) {
                handleChannelEvent(channelEvent);
            }
        });
    }

    private void handleChannelEvent(ChannelEvent channelEvent) {
        switch (channelEvent.eventType) {
            case ChannelEvent.ADD_EVENT:
                mPagerAdapter.addItem(NewsListFragment.newInstance(channelEvent.newsInfo.getTypeId()), channelEvent.newsInfo.getName());
                break;
            case ChannelEvent.DEL_EVENT:
                // 如果是删除操作直接切换第一项，不然容易出现加载到不存在的Fragment
                mNewVp.setCurrentItem(0);
                mPagerAdapter.delItem(channelEvent.newsInfo.getName());
                break;
            case ChannelEvent.SWAP_EVENT:
                mPagerAdapter.swapItems(channelEvent.fromPos, channelEvent.toPos);
                break;
        }
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_layout_news;
    }


    @Override
    public void loadData(List<NewsTypeInfo> checkList) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (NewsTypeInfo bean : checkList) {
            titles.add(bean.getName());
//            fragments.add(new TestFragment());
            fragments.add(NewsListFragment.newInstance(bean.getTypeId()));
        }
        mPagerAdapter.setItems(fragments, titles);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterRxBus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_channel,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item_channel){
            ChannelActivity.launch(mContext);
            return true;
        }
        return false;
    }
}
