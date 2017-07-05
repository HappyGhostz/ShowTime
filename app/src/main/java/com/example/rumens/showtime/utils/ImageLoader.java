package com.example.rumens.showtime.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.entity.GlideRoundTransformUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * @author Zhaochen Ping
 * @create 2017/4/24
 * @description
 */

public class ImageLoader {
    private ImageLoader() {
        throw new RuntimeException("ImageLoader cannot be initialized!");
    }

    public static void loadFit(Context context, String url, ImageView view, int defaultResId){
        if(PreferencesUtils.isShowImageAlways(context)||NetUtil.isWifiConnected(context)){
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(url).dontAnimate().fitCenter().placeholder(defaultResId).into(view);
        }else {
            view.setImageResource(defaultResId);
        }
    }
    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId){
        if(PreferencesUtils.isShowImageAlways(context)||NetUtil.isWifiConnected(context)){
            Glide.with(context).load(url).centerCrop().crossFade().dontAnimate().placeholder(defaultResId).into(view);
        }else {
            view.setImageResource(defaultResId);
        }
    }
    public static void loadFitCenter(Context context, String url, ImageView view, int defaultResId) {
        if (PreferencesUtils.isShowImageAlways(context) || NetUtil.isWifiConnected(context)) {
            Glide.with(context).load(url).fitCenter().dontAnimate().placeholder(defaultResId).into(view);
        } else {
            view.setImageResource(defaultResId);
        }
    }
    public static void loadResCenterCrop(Context context, int resID, ImageView view) {
        Glide.with(context)
                .load(resID)
                .dontAnimate()
                .into(view);
    }
    public static void loadResGif(Context context ,int resId,ImageView view){
        Glide.with(context)
                .load(resId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }
    public static void loadUrlGif(Context context ,String url,ImageView view){
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }
    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_empty_picture).
                centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }
    public static void displayRoundRes(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_empty_picture).
                centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }

    /**
     * 带监听处理
     *
     * @param context
     * @param url
     * @param view
     * @param listener
     */
    public static void loadFitCenter(Context context, String url, ImageView view, RequestListener listener) {
        Glide.with(context).load(url).fitCenter().dontAnimate().listener(listener).into(view);
    }

    public static void loadCenterCrop(Context context, String url, ImageView view, RequestListener listener) {
        Glide.with(context).load(url).centerCrop().dontAnimate().listener(listener).into(view);
    }

    /**
     * 设置图片大小处理
     *
     * @param context
     * @param url
     * @param view
     * @param defaultResId
     * @param width
     * @param height
     */
    public static void loadFitOverride(Context context, String url, ImageView view, int defaultResId,
                                       int width, int height) {
        if (PreferencesUtils.isShowImageAlways(context) || NetUtil.isWifiConnected(context)) {
            Glide.with(context).load(url).fitCenter().dontAnimate().override(width, height)
                    .placeholder(defaultResId).into(view);
        } else {
            view.setImageResource(defaultResId);
        }
    }

    /**
     * 计算图片分辨率
     *
     * @param context
     * @param url
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String calePhotoSize(Context context, String url) throws ExecutionException, InterruptedException {
        File file = Glide.with(context).load(url)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return options.outWidth + "*" + options.outHeight;
    }
     public static Bitmap getBitMap(Context context,String url) {
//         Bitmap bitmap = null;
//         try {
//             bitmap = Glide.with(context).load(url).asBitmap().centerCrop().into(500, 500).get();
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         } catch (ExecutionException e) {
//             e.printStackTrace();
//         }
//         return bitmap;
         try {
             File file = Glide.with(context).load(url)
                     .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
             Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
             return  bitmap;
         } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }
         return  null;
     }
}
