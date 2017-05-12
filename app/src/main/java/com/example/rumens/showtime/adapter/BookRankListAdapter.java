package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.RankingListBean;
import com.example.rumens.showtime.reader.bookrank.OnRvItemClickListener;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.ImageLoader;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class BookRankListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<RankingListBean.MaleBean> maleGroups;
    private final List<List<RankingListBean.MaleBean>> maleChilds;
    private final LayoutInflater inflater;
    private OnRvItemClickListener<RankingListBean.MaleBean> listener;

    public BookRankListAdapter(Context context, List<RankingListBean.MaleBean> maleGroups, List<List<RankingListBean.MaleBean>> maleChilds) {

        this.context = context;
        this.maleGroups = maleGroups;
        this.maleChilds = maleChilds;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return maleGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return maleChilds.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return maleGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return maleChilds.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final View group = inflater.inflate(R.layout.adapter_book_rank, null);
        ImageView groupCover = (ImageView) group.findViewById(R.id.ivRankCover);
        if(!TextUtils.isEmpty(maleGroups.get(groupPosition).cover)){
            ImageLoader.loadFitCenter(context, Constant.IMG_BASE_URL + maleGroups.get(groupPosition).cover,groupCover,R.mipmap.avatar_default);
            group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(group, groupPosition, maleGroups.get(groupPosition));
                }
            });
        }else {
            groupCover.setImageResource(R.mipmap.ic_rank_collapse);
        }
        TextView tvTitle = (TextView) group.findViewById(R.id.tvRankGroupName);
        tvTitle.setText(maleGroups.get(groupPosition).title);

        ImageView ivArrow = (ImageView) group.findViewById(R.id.ivRankArrow);
        if (maleChilds.get(groupPosition).size() > 0) {
            if (isExpanded) {
                ivArrow.setImageResource(R.mipmap.rank_arrow_up);
            } else {
                ivArrow.setImageResource(R.mipmap.rank_arrow_down);
            }
        } else {
            ivArrow.setVisibility(View.GONE);
        }
        return group;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final View child = inflater.inflate(R.layout.adapter_book_rank_child, null);

        TextView tvName = (TextView) child.findViewById(R.id.tvRankChildName);
        tvName.setText(maleChilds.get(groupPosition).get(childPosition).title);

        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(child, childPosition, maleChilds.get(groupPosition).get(childPosition));
            }
        });
        return child;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setItemClickListener(OnRvItemClickListener<RankingListBean.MaleBean> listener) {
        this.listener = listener;
    }
}
