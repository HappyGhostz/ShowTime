package com.example.rumens.showtime.adapter;

import android.content.Context;
import android.text.TextUtils;

import android.widget.ImageView;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.utils.DefIconFactory;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.video.videoliveplay.DouyuPhonePlayActivity;
import com.example.rumens.showtime.video.videoliveplay.DouyuWebViewPlayActivity;
import com.example.rumens.showtime.video.videoliveplay.VideoPlayActivity;
import com.example.rumens.showtime.widget.RippleView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */

public class LiveAdapter extends BaseQuickAdapter {
    private final String mPlatformType;

    public LiveAdapter(Context context, String mPlatformType) {
        super(context);
        this.mPlatformType = mPlatformType;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_live;
    }

    @Override
    protected void convert(BaseViewHolder holder, final Object item) {
        if(TextUtils.equals("douyu",mPlatformType)){
            if(item instanceof DouyuLiveListItemBean.DataBean){
                holder.setText(R.id.tv_roomname, ((DouyuLiveListItemBean.DataBean) item).getRoom_name())//房间名称
                        .setText(R.id.tv_nickname, ((DouyuLiveListItemBean.DataBean) item).getNickname())//主播昵称
                        .setText(R.id.tv_online, String.valueOf(((DouyuLiveListItemBean.DataBean) item).getOnline()));//在线人数
                ImageView roomSrc =  holder.getView(R.id.iv_roomsrc);
                CircleImageView acatarSrc =  holder.getView(R.id.iv_avatar);
                ImageLoader.loadCenterCrop(mContext, ((DouyuLiveListItemBean.DataBean) item).getRoom_src(), roomSrc, DefIconFactory.provideIcon());
                ImageLoader.loadCenterCrop(mContext, ((DouyuLiveListItemBean.DataBean) item).getAvatar_small(), acatarSrc, DefIconFactory.provideIcon());
                RippleView rippleView = holder.getView(R.id.item_live_ripple);
                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        if(((DouyuLiveListItemBean.DataBean) item).getCate_id()== 201){
                            DouyuPhonePlayActivity.lunchLiveDouyu(mContext,((DouyuLiveListItemBean.DataBean) item),mPlatformType);
                        }else if (((DouyuLiveListItemBean.DataBean) item).getCate_id()==207){
                            DouyuWebViewPlayActivity.lunchLiveDouyu(mContext,((DouyuLiveListItemBean.DataBean) item),mPlatformType);
                        }else{
                            VideoPlayActivity.lunchLiveDouyu(mContext,((DouyuLiveListItemBean.DataBean) item),mPlatformType);
                        }
                    }
                });
            }

        }else{
            if(item instanceof LiveListItemBean){
                holder.setText(R.id.tv_roomname,  ((LiveListItemBean) item).getLive_title())//房间名称
                        .setText(R.id.tv_nickname, ((LiveListItemBean) item).getLive_nickname())//主播昵称
                        .setText(R.id.tv_online, String.valueOf(((((LiveListItemBean) item).getLive_online()))));//在线人数
                ImageView roomSrc =  holder.getView(R.id.iv_roomsrc);
                CircleImageView acatarSrc =  holder.getView(R.id.iv_avatar);
                ImageLoader.loadCenterCrop(mContext, ((LiveListItemBean) item).getLive_img(), roomSrc, DefIconFactory.provideIcon());
                ImageLoader.loadCenterCrop(mContext, ((LiveListItemBean) item).getLive_userimg(), acatarSrc, DefIconFactory.provideIcon());
                RippleView rippleView = holder.getView(R.id.item_live_ripple);
                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        VideoPlayActivity.lunchLive(mContext,((LiveListItemBean) item));
                    }
                });
            }
        }
    }


//    @Override
//    protected void convert(final BaseViewHolder viewHolder, final LiveListItemBean bean) {
//
//        viewHolder.setText(R.id.tv_roomname, bean.getLive_title())//房间名称
//                .setText(R.id.tv_nickname, bean.getLive_nickname())//主播昵称
//                .setText(R.id.tv_online, String.valueOf(bean.getLive_online()));//在线人数
//        ImageView roomSrc =  viewHolder.getView(R.id.iv_roomsrc);
//        ImageView acatarSrc =  viewHolder.getView(R.id.iv_avatar);
//        ImageLoader.loadCenterCrop(mContext, bean.getLive_img(), roomSrc, DefIconFactory.provideIcon());
//        ImageLoader.loadCenterCrop(mContext, bean.getLive_userimg(), acatarSrc, DefIconFactory.provideIcon());
//        // 波纹效果
//        RippleView rippleLayout = viewHolder.getView(R.id.item_live_ripple);
//        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
//            @Override
//            public void onComplete(RippleView rippleView) {
//                VideoPlayActivity.lunchLive(mContext,bean);
//            }
//        });
//    }

}
