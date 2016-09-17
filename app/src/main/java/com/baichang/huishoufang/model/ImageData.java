package com.baichang.huishoufang.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;


/**
 * Created by iscod.
 * Time:2016/7/2-10:39.
 */
public class ImageData implements Serializable {
    @Expose
    public String picpath;
    @Expose
    public String title;
    @Expose
    public String kid;
    @Expose
    public String remark;

    public boolean isSelect;
}
