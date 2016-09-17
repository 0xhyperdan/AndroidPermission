package com.baichang.huishoufang.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HouseData implements Serializable {

    public String type; // 风景、动物、植物、建筑
    public String title; // 标题
    public String from; // 来源
    public int rank; // 排名
    public String image_url; // 图片地址

    // 暂无数据属性
    public boolean isNoData = false;
    public int height;

    public HouseData() {
    }

    public HouseData(String type, String title, String from, int rank, String image_url) {
        this.type = type;
        this.title = title;
        this.from = from;
        this.rank = rank;
        this.image_url = image_url;
    }

}
