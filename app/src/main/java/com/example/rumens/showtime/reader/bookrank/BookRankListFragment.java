package com.example.rumens.showtime.reader.bookrank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.BookRankListAdapter;
import com.example.rumens.showtime.api.bean.RankingListBean;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerBookRankListComponent;
import com.example.rumens.showtime.inject.modules.BookRankListModule;
import com.example.rumens.showtime.widget.CustomExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class BookRankListFragment extends BaseFragment<IBasePresenter> implements IBookRankView {
    private static final String BOOK_RANK = "bookrank";
    @BindView(R.id.elvMale)
    CustomExpandableListView mElvMale;
    @BindView(R.id.elvFeMale)
    CustomExpandableListView mElvFeMale;
    private String mBookType;
    private List<RankingListBean.MaleBean> maleGroups = new ArrayList<>();
    private List<List<RankingListBean.MaleBean>> maleChilds = new ArrayList<>();
    private BookRankListAdapter maleAdapter;

    private List<RankingListBean.MaleBean> femaleGroups = new ArrayList<>();
    private List<List<RankingListBean.MaleBean>> femaleChilds = new ArrayList<>();
    private BookRankListAdapter femaleAdapter;

    public static Fragment lunch(String mBookType) {
        BookRankListFragment fragment = new BookRankListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_RANK, mBookType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void LoadRankList(RankingListBean rankingList) {
        maleGroups.clear();
        femaleGroups.clear();
        updateMale(rankingList);
        updateFemale(rankingList);
    }

    private void updateMale(RankingListBean rankingList) {
        List<RankingListBean.MaleBean> list = rankingList.male;
        List<RankingListBean.MaleBean> collapse = new ArrayList<>();
        for (RankingListBean.MaleBean bean : list) {
            if (bean.collapse) { // 折叠
                collapse.add(bean);
            } else {
                maleGroups.add(bean);
                maleChilds.add(new ArrayList<RankingListBean.MaleBean>());
            }
        }
        if (collapse.size() > 0) {
            maleGroups.add(new RankingListBean.MaleBean("别人家的排行榜"));
            maleChilds.add(collapse);
        }
        maleAdapter.notifyDataSetChanged();
    }

    private void updateFemale(RankingListBean rankingList) {
        List<RankingListBean.MaleBean> female = rankingList.female;
        List<RankingListBean.MaleBean>collapse = new ArrayList<>();
        for (RankingListBean.MaleBean bean :
                female) {
            if (bean.collapse) {
                collapse.add(bean);
            }else {
                femaleGroups.add(bean);
                femaleChilds.add(new ArrayList<RankingListBean.MaleBean>());
            }
        }
        if(collapse.size()>0){
            femaleGroups.add(new RankingListBean.MaleBean("别人家的排行榜"));
            femaleChilds.add(collapse);
        }
        femaleAdapter.notifyDataSetChanged();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_book_rank;
    }

    @Override
    protected void initInjector() {
        mBookType = getArguments().getString(BOOK_RANK);
        DaggerBookRankListComponent.builder()
                .appComponent(getAppComponent())
                .bookRankListModule(new BookRankListModule(this, mBookType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        maleAdapter = new BookRankListAdapter(mContext, maleGroups, maleChilds);
        mElvMale.setAdapter(maleAdapter);
        femaleAdapter = new BookRankListAdapter(mContext, femaleGroups, femaleChilds);
        mElvFeMale.setAdapter(femaleAdapter);
        maleAdapter.setItemClickListener(new ClickListener());
        femaleAdapter.setItemClickListener(new ClickListener());
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    class ClickListener implements OnRvItemClickListener<RankingListBean.MaleBean> {

        @Override
        public void onItemClick(View view, int position, RankingListBean.MaleBean data) {
            if (data.monthRank == null) {
                SubOtherHomeRankActivity.startActivity(mContext, data._id, data.title);
            } else {
                SubRankActivity.startActivity(mContext, data._id, data.monthRank, data.totalRank, data.title);
            }
        }
    }

}
