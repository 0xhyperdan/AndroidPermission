package com.baichang.huishoufang.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iscod.
 * Time:2016/9/6-9:36.
 */
public class GuideData implements Serializable {
    @Expose
    public List<ImagePath> imgpaths;
    @Expose
    public String updated;

    public class ImagePath implements Serializable {
        @Expose
        public String picpath;
        @Expose
        public String kid;
        @Expose
        public String title;
        @Expose
        public String updated;
    }
}
