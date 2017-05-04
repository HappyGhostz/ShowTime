package com.example.rumens.showtime.news.channe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnItemMoveListener;
import com.example.rumens.showtime.adapter.listener.OnRecyclerViewItemClickListener;
import com.example.rumens.showtime.adapter.listener.OnRemoveDataListener;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.inject.component.DaggerChannelComponent;
import com.example.rumens.showtime.inject.modules.ChannelModule;
import com.example.rumens.showtime.local.NewsTypeInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;



/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

public class ChannelActivity extends BaseActivity<IChannelPresenter> implements IChannelView {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_checked_list)
    RecyclerView mRvCheckedList;
    @BindView(R.id.rv_unchecked_list)
    RecyclerView mRvUncheckedList;
    @Inject
    BaseQuickAdapter mCheckedAdapter;
    @Inject
    BaseQuickAdapter mUncheckedAdapter;

    public static void launch(Context mContext) {
        Intent intent = new Intent(mContext, ChannelActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        initToolBar(mToolbar,true,"栏目管理");
        RecyclerViewHelper.initRecyclerViewG(this,mRvCheckedList,mCheckedAdapter,4);
        RecyclerViewHelper.initRecyclerViewG(this,mRvUncheckedList,mUncheckedAdapter,4);
        RecyclerViewHelper.startDragAndSwipe(mRvCheckedList,mCheckedAdapter,0);
        mRvCheckedList.setItemAnimator(new ScaleInAnimator());
        mRvUncheckedList.setItemAnimator(new FlipInBottomXAnimator());
        mCheckedAdapter.setDragDrawable(ContextCompat.getDrawable(this,R.drawable.shape_channel_drag));
        // 设置移除监听器
        mCheckedAdapter.setRemoveDataListener(new OnRemoveDataListener() {
            @Override
            public void onRemove(int position) {
                mUncheckedAdapter.addLastItem(mCheckedAdapter.getItem(position));
                mPresenter.delete(mCheckedAdapter.getItem(position));
            }
        });
        // 设置移动监听器
        mCheckedAdapter.setItemMoveListener(new OnItemMoveListener() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                mPresenter.update(mCheckedAdapter.getData());
                mPresenter.swap(fromPosition, toPosition);
            }
        });
        // 设置点击删除
        mUncheckedAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 删除前获取数据，不然获取不到对应数据
                Object data = mUncheckedAdapter.getItem(position);
                mUncheckedAdapter.removeItem(position);
                mCheckedAdapter.addLastItem(data);
                mPresenter.insert(data);
            }
        });
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_channel;
    }

    @Override
    protected void initInjector() {
        DaggerChannelComponent.builder()
                .appComponent(getAppComponent())
                .channelModule(new ChannelModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void loadData(List<NewsTypeInfo> checkList, List<NewsTypeInfo> uncheckList) {
        mCheckedAdapter.updateItems(checkList);
        mUncheckedAdapter.updateItems(uncheckList);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }


}
