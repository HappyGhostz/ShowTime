package com.example.rumens.showtime.api.bean;

import java.io.Serializable;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class BookMark implements Serializable{
    public int chapter;

    public String title;

    public int startPos;

    public int endPos;

    public String desc = "";
}
