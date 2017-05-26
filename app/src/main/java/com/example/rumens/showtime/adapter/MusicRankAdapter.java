package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.RankingListItem;
import com.example.rumens.showtime.music.listplay.MusicRankingListDetailActivity;
import com.example.rumens.showtime.utils.DefIconFactory;
import com.example.rumens.showtime.utils.ImageLoader;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicRankAdapter extends BaseQuickAdapter<RankingListItem.RangkingDetail> {
    public MusicRankAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_music_rank_item;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final RankingListItem.RangkingDetail item) {
        ImageView imageView = holder.getView(R.id.iv_ranking_photo);
        ImageLoader.loadCenterCrop(mContext,item.getPic_s192(),imageView, DefIconFactory.provideIcon());

        List<RankingListItem.RangkingDetail.SongInfo> content = item.getContent();
        final RankingListItem.RangkingDetail.SongInfo info1 = content.get(0);
        String title1 = info1.getTitle();
        String author1 = info1.getAuthor();
        RankingListItem.RangkingDetail.SongInfo info2 = content.get(1);
        String title2 = info2.getTitle();
        String author2 = info2.getAuthor();
        RankingListItem.RangkingDetail.SongInfo info3 = content.get(2);
        String title3 = info3.getTitle();
        String author3 = info3.getAuthor();

        holder.setText(R.id.tv_rank_first,"1." + title1 + "-" + author1)
                .setText(R.id.tv_rank_name,item.getName())
                .setText(R.id.tv_rank_second,"2." + title2 + "-" + author2)
                .setText(R.id.tv_rank_third,"3." + title3 + "-" + author3);

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MusicRankingListDetailActivity.lunch(mContext,position,item.getName());
            }
        });
    }
}
