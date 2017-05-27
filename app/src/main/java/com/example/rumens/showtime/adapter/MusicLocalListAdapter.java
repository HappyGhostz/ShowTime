package com.example.rumens.showtime.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseViewHolder;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.utils.ImageLoader;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class MusicLocalListAdapter extends BaseQuickAdapter<SongLocalBean> {
    public MusicLocalListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_local_music_list_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, SongLocalBean item) {
        ImageView imageView = holder.getView(R.id.iv_albumr);
        Bitmap artwork = getArtwork(mContext, item._id, item.albun_id);
//        if(artwork!=null){
//            imageView.setImageBitmap(artwork);
//        }
        if(item.picurl!=null){
            ImageLoader.loadCenterCrop(mContext,item.picurl,imageView,R.mipmap.music_detail_play);
        }else if(item.picurl==null&&artwork!=null){
            imageView.setImageBitmap(artwork);
        }

        holder.setText(R.id.tv_song_title,item.title)
                .setText(R.id.tv_song_artist,item.artist)
                .setText(R.id.tv_local_album,item.album);
    }
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    public static Bitmap getArtwork(Context context, long song_id, long album_id
    ) {
        if (album_id < 0) {
            if (song_id >= 0) {
                Bitmap bm = getArtworkFromFile(context, song_id, -1);
                if (bm != null) {
                    return bm;
                }
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(in, null, sBitmapOptions);
                return bmp;
            } catch (FileNotFoundException ex) {
                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                    }
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {
        Bitmap bm = null;
        if (albumid < 0 && songid < 0) {
            throw new IllegalArgumentException("Must specify an album or a song id");
        }
        try {
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {
                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            }
        } catch (FileNotFoundException ex) {

        }
        return bm;
    }
}
