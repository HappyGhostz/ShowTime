package com.example.rumens.showtime.api.bean;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class DownloadMessage {
    public String bookId;

    public String message;

    public boolean isComplete = false;

    public DownloadMessage(String bookId, String message, boolean isComplete) {
        this.bookId = bookId;
        this.message = message;
        this.isComplete = isComplete;
    }
}
