package com.example.rumens.showtime.api.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class CategoryListLv2 implements Serializable{
    /**
     * major : 玄幻
     * mins : ["东方玄幻","异界大陆","异界争霸","远古神话"]
     */

    public List<MaleBean> male;
    /**
     * major : 古代言情
     * mins : ["穿越时空","古代历史","古典架空","宫闱宅斗","经商种田"]
     */

    public List<MaleBean> female;

    public static class MaleBean implements Serializable{
        public String major;
        public List<String> mins;
    }
}
