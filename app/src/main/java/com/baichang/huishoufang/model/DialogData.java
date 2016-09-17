package com.baichang.huishoufang.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by iscod.
 * Time:2016/7/1-16:58.
 */
public class DialogData implements Serializable {
    @Expose
    public String name;
    @Expose
    public String kid;
    @Expose
    public String value;

    public String tag;

    public DialogData(String name, String kid) {
        this.name = name;
        this.kid = kid;
    }

    public DialogData() {

    }
}
