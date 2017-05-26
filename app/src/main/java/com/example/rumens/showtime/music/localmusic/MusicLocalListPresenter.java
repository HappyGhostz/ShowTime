package com.example.rumens.showtime.music.localmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.SystemClock;
import android.text.format.Formatter;

import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.ToastUtils;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.provider.MediaStore;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class MusicLocalListPresenter implements IBasePresenter {
    private IBaseLocalMusicView mView;
    private Context context;
    private final List<SongLocalBean> songLocalBeanList;


    public MusicLocalListPresenter(IBaseLocalMusicView mView, Context context) {
        this.mView = mView;
        this.context = context;
        songLocalBeanList = new ArrayList<>();
    }

    @Override
    public void getData() {
        mView.showLoading();

        Cursor cursor = null;
        songLocalBeanList.clear();

        SystemClock.sleep(3000);
        mView.hideLoading();
        try{
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(MediaStore.Audio.Media.CONTENT_URI, new String[] {
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.SIZE},
                    null, null, MediaStore.Audio.Media.DATE_ADDED + " DESC");
            if(cursor == null) {
                return ;
            }
            int count= cursor.getCount();
            if(count <= 0) {
                ToastUtils.showToast("无音乐文件");
                return ;
            }
                while (cursor.moveToNext()) {
                    //歌曲编号
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    //歌曲标题
                    String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                    String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                    Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    if (size > 1024 * 800) {//大于800K
                        String formatFileSize = Formatter.formatFileSize(context, size);
                        SongLocalBean musicMedia = new SongLocalBean(tilte,url,formatFileSize,duration,artist,album);
                        songLocalBeanList.add(musicMedia);
                    }
                }
                mView.loadLocalMusicListInfo(songLocalBeanList);
        }catch (Exception e){
            e.printStackTrace();
            mView.showNetError(new EmptyErrLayout.OnRetryListener() {
                @Override
                public void onRetry() {
                    getData();
                }
            });
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

    }

    @Override
    public void getMoreData() {

    }
}
