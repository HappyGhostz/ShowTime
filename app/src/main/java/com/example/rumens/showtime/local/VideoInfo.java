package com.example.rumens.showtime.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rumens.showtime.constant.ConstantUitles;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */
@Entity
public class VideoInfo implements Parcelable {
    @Id
    private String vid;
    private String mp4Hd_url;
    private String cover;
    private String title;
    //    private String replyBoard;
//    private int playCount;
    private String sectiontitle;
    //    private String replyid;
    private String mp4_url;
    private int length;
    private String m3u8Hd_url;
    //    private String latest;
    private String ptime;
    private String m3u8_url;

    /**
     * 下载地址，可能有多个视频源，统一用一个字段
     */
    private String videoUrl;
    /**
     * 文件大小，字节
     */
    private long totalSize;
    /**
     * 已加载大小
     */
    private long loadedSize;
    /**
     * 下载状态
     */
    private int downloadStatus = ConstantUitles.NORMAL;
    /**
     * 下载速度
     */
    private long downloadSpeed;
    /**
     * 是否收藏
     */
    private boolean isCollect;

    protected VideoInfo(Parcel in) {
        vid = in.readString();
        mp4Hd_url = in.readString();
        cover = in.readString();
        title = in.readString();
        sectiontitle = in.readString();
        mp4_url = in.readString();
        length = in.readInt();
        m3u8Hd_url = in.readString();
        ptime = in.readString();
        m3u8_url = in.readString();
        videoUrl = in.readString();
        totalSize = in.readLong();
        loadedSize = in.readLong();
        downloadStatus = in.readInt();
        downloadSpeed = in.readLong();
        isCollect = in.readByte() != 0;
    }

    @Generated(hash = 1048022349)
    public VideoInfo(String vid, String mp4Hd_url, String cover, String title,
            String sectiontitle, String mp4_url, int length, String m3u8Hd_url,
            String ptime, String m3u8_url, String videoUrl, long totalSize,
            long loadedSize, int downloadStatus, long downloadSpeed,
            boolean isCollect) {
        this.vid = vid;
        this.mp4Hd_url = mp4Hd_url;
        this.cover = cover;
        this.title = title;
        this.sectiontitle = sectiontitle;
        this.mp4_url = mp4_url;
        this.length = length;
        this.m3u8Hd_url = m3u8Hd_url;
        this.ptime = ptime;
        this.m3u8_url = m3u8_url;
        this.videoUrl = videoUrl;
        this.totalSize = totalSize;
        this.loadedSize = loadedSize;
        this.downloadStatus = downloadStatus;
        this.downloadSpeed = downloadSpeed;
        this.isCollect = isCollect;
    }

    @Generated(hash = 296402066)
    public VideoInfo() {
    }

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        @Override
        public VideoInfo createFromParcel(Parcel in) {
            return new VideoInfo(in);
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vid);
        dest.writeString(this.mp4Hd_url);
        dest.writeString(this.cover);
        dest.writeString(this.title);
        dest.writeString(this.sectiontitle);
        dest.writeString(this.mp4_url);
        dest.writeInt(this.length);
        dest.writeString(this.m3u8Hd_url);
        dest.writeString(this.ptime);
        dest.writeString(this.m3u8_url);
        dest.writeString(this.videoUrl);
        dest.writeLong(this.totalSize);
        dest.writeLong(this.loadedSize);
        dest.writeInt(this.downloadStatus);
        dest.writeLong(this.downloadSpeed);
        dest.writeByte(this.isCollect ? (byte) 1 : (byte) 0);
    }
//    @Override
//    public boolean equals(Object o) {
//        if (!(o instanceof VideoInfo)) {
//            return false;
//        }
//        VideoInfo other = (VideoInfo) o;
//        if (vid.equals(other.getVid())) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return vid.hashCode();
//    }

    public boolean getIsCollect() {
        return this.isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public long getDownloadSpeed() {
        return this.downloadSpeed;
    }

    public void setDownloadSpeed(long downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public int getDownloadStatus() {
        return this.downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public long getLoadedSize() {
        return this.loadedSize;
    }

    public void setLoadedSize(long loadedSize) {
        this.loadedSize = loadedSize;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getM3u8_url() {
        return this.m3u8_url;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public String getPtime() {
        return this.ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getM3u8Hd_url() {
        return this.m3u8Hd_url;
    }

    public void setM3u8Hd_url(String m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMp4_url() {
        return this.mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public String getSectiontitle() {
        return this.sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMp4Hd_url() {
        return this.mp4Hd_url;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
