package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.utils.BookSettingManager;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FileUtils;
import com.example.rumens.showtime.utils.FormatUtils;
import com.example.rumens.showtime.utils.ImageLoader;

import java.text.NumberFormat;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class BookRecommendListAdapter extends BaseQuickAdapter<Recommend.RecommendBooks> {
    private String mBookListType;

    public BookRecommendListAdapter(Context context, String mBookListType) {
        super(context);
        this.mBookListType = mBookListType;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_recommend_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final Recommend.RecommendBooks item) {
        String latelyUpdate = "";
        if (!TextUtils.isEmpty(FormatUtils.getDescriptionTimeFromDateString(item.updated))) {
            latelyUpdate = FormatUtils.getDescriptionTimeFromDateString(item.updated) + ":";
        }

        holder.setText(R.id.tvRecommendTitle, item.title)
                .setText(R.id.tvLatelyUpdate, latelyUpdate)
                .setText(R.id.tvRecommendShort, item.lastChapter)
                .setVisible(R.id.ivTopLabel, item.isTop)
                .setVisible(R.id.ckBoxSelect, item.showCheckBox)
                .setVisible(R.id.ivUnReadDot, FormatUtils.formatZhuiShuDateString(item.updated)
                        .compareTo(item.recentReadingTime) > 0);

        if (item.path != null && item.path.endsWith(Constant.SUFFIX_PDF)) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_pdf);
        } else if (item.path != null && item.path.endsWith(Constant.SUFFIX_EPUB)) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_epub);
        } else if (item.path != null && item.path.endsWith(Constant.SUFFIX_CHM)) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_chm);
        } else if (item.isFromSD) {
            holder.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_txt);
            long fileLen = FileUtils.getChapterFile(item._id, 1).length();
            if (fileLen > 10) {
                double progress = ((double) BookSettingManager.getInstance().getReadProgress(item._id)[2]) / fileLen;
                NumberFormat fmt = NumberFormat.getPercentInstance();
                fmt.setMaximumFractionDigits(2);
                holder.setText(R.id.tvRecommendShort, "当前阅读进度：" + fmt.format(progress));
            }
        } else if (!BookSettingManager.getInstance().isNoneCover()) {
            ImageView image = holder.getView(R.id.ivRecommendCover);
            ImageLoader.loadCenterCrop(mContext,Constant.IMG_BASE_URL + item.cover,image, R.drawable.cover_default);
        } else {
            holder.setImageResource(R.id.ivRecommendCover, R.drawable.cover_default);
        }

        CheckBox ckBoxSelect = holder.getView(R.id.ckBoxSelect);
        ckBoxSelect.setChecked(item.isSeleted);
        ckBoxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                item.isSeleted = isChecked;
            }
        });
    }
}
