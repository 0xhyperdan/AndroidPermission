package com.baichang.huishoufang.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by iscod.
 * Time:2016/8/17-9:21.
 */
public class VersionData implements Serializable {
    @Expose
    public String versions;
    @Expose
    public String detail;
    @Expose
    public String updateurl;
    @Expose
    public String enforce;
}
