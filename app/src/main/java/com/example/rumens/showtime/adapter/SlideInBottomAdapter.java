package com.example.rumens.showtime.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;

import static com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter.EMPTY_VIEW;
import static com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter.FOOTER_VIEW;
import static com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter.HEADER_VIEW;
import static com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter.LOADING_VIEW;

/**
 * @author Zhaochen Ping
 * @create 2017/4/27
 * @description
 */

public class SlideInBottomAdapter extends AnimationAdapter{
    public SlideInBottomAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        super(adapter);
    }

    @Override
    protected Animator[] getAnimators(View view) {
        return new Animator[] {
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int viewType = holder.getItemViewType();
        if(viewType==EMPTY_VIEW||viewType == HEADER_VIEW || viewType == FOOTER_VIEW || viewType == LOADING_VIEW){
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
