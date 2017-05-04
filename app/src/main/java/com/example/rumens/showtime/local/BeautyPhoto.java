package com.example.rumens.showtime.local;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */
@Entity
public class BeautyPhoto implements Parcelable{
    @Id
    private String imgsrc;
    private String pixel;
    private String docid;
    private String title;
    // 喜欢
    private boolean isLove;
    // 点赞
    private boolean isPraise;
    // 下载
    private boolean isDownload;

    protected BeautyPhoto(Parcel in) {
        imgsrc = in.readString();
        pixel = in.readString();
        docid = in.readString();
        title = in.readString();
        isLove = in.readByte() != 0;
        isPraise = in.readByte() != 0;
        isDownload = in.readByte() != 0;
    }

    @Generated(hash = 2061371882)
    public BeautyPhoto(String imgsrc, String pixel, String docid, String title,
            boolean isLove, boolean isPraise, boolean isDownload) {
        this.imgsrc = imgsrc;
        this.pixel = pixel;
        this.docid = docid;
        this.title = title;
        this.isLove = isLove;
        this.isPraise = isPraise;
        this.isDownload = isDownload;
    }

    @Generated(hash = 3085635)
    public BeautyPhoto() {
    }

    public static final Creator<BeautyPhoto> CREATOR = new Creator<BeautyPhoto>() {
        @Override
        public BeautyPhoto createFromParcel(Parcel in) {
            return new BeautyPhoto(in);
        }

        @Override
        public BeautyPhoto[] newArray(int size) {
            return new BeautyPhoto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgsrc);
        dest.writeString(pixel);
        dest.writeString(docid);
        dest.writeString(title);
        dest.writeByte((byte) (isLove ? 1 : 0));
        dest.writeByte((byte) (isPraise ? 1 : 0));
        dest.writeByte((byte) (isDownload ? 1 : 0));
    }

    public boolean getIsDownload() {
        return this.isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    public boolean getIsPraise() {
        return this.isPraise;
    }

    public void setIsPraise(boolean isPraise) {
        this.isPraise = isPraise;
    }

    public boolean getIsLove() {
        return this.isLove;
    }

    public void setIsLove(boolean isLove) {
        this.isLove = isLove;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocid() {
        return this.docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getPixel() {
        return this.pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public String getImgsrc() {
        return this.imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
