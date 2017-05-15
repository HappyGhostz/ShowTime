package com.example.rumens.showtime.reader.downloadservice;

/**
 * @author Zhaochen Ping
 * @create 2017/5/15
 * @description
 */

public class DownloadProgress {
    public String bookId;

    public String message;

    public boolean isAlreadyDownload = false;

    public DownloadProgress(String bookId, String message, boolean isAlreadyDownload) {
        this.bookId = bookId;
        this.message = message;
        this.isAlreadyDownload = isAlreadyDownload;
    }
}
