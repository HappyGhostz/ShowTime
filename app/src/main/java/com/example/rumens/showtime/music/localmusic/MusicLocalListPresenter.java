package com.example.rumens.showtime.music.localmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.Formatter;

import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.ToastUtils;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


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
        songLocalBeanList.clear();
        Observable.create(new Observable.OnSubscribe<List<SongLocalBean>>() {
            @Override
            public void call(Subscriber<? super List<SongLocalBean>> subscriber) {
                songLocalBeanList.clear();
                Cursor cursor = null;
                try{
                    ContentResolver contentResolver = context.getContentResolver();
                    cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] {
                                    MediaStore.Audio.Media._ID,
                                    MediaStore.Audio.Media.TITLE,
                                    MediaStore.Audio.Media.DISPLAY_NAME,
                                    MediaStore.Audio.Media.DURATION,
                                    MediaStore.Audio.Media.ARTIST,
                                    MediaStore.Audio.Media.DATA,
                                    MediaStore.Audio.Media.ALBUM,
                                    MediaStore.Audio.Media.ALBUM_ID,
                                    MediaStore.Audio.Media.SIZE},
                            null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER );
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
                        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                        //歌曲标题
                        String tilte = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                        //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                        String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                        String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                        //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                        int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                        //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                        Long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                        int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                        String albumArt = getAlbumArt(albumId);
                        if (size > 1024 * 800) {//大于800K
                            String formatFileSize = Formatter.formatFileSize(context, size);
                            SongLocalBean musicMedia = new SongLocalBean(id,tilte,url,formatFileSize,duration,artist, album,albumId,albumArt);
                            songLocalBeanList.add(musicMedia);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(cursor!=null){
                        cursor.close();
                    }
                }
                subscriber.onNext(songLocalBeanList);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                mView.showLoading();
            }
        })
        .subscribe(new Subscriber<List<SongLocalBean>>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showNetError(new EmptyErrLayout.OnRetryListener() {
                    @Override
                    public void onRetry() {
                        getData();
                    }
                });
            }

            @Override
            public void onNext(List<SongLocalBean> localBeanList) {
                mView.loadLocalMusicListInfo(localBeanList);
            }
        });



    }

    @Override
    public void getMoreData() {

    }



    private String getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }



}
