package com.baichang.huishoufang.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by iscod.
 * Time:2016/6/22-9:04.
 */
public class UserData implements Serializable {
    @Expose
    public String kid;
    @Expose
    public String village;
    @Expose
    public String committee;
    @Expose
    public String station;
    @Expose
    public String mobile;
    @Expose
    public String token;
    @Expose
    public String villagekid;
    @Expose
    public String committeekid;

    public String password;
}
