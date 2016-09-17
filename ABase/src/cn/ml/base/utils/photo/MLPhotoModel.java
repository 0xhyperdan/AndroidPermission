package cn.ml.base.utils.photo;

import android.graphics.Bitmap;

public class MLPhotoModel {

    public MLPhotoModel() {
    }

    public MLPhotoModel(Bitmap mBitMap, String path, boolean isNet) {
        super();
        this.mBitMap = mBitMap;
        this.path = path;
        this.isNet = isNet;
    }

    public MLPhotoModel(int id, Bitmap mBitMap, String path) {
        super();
        this.id = id;
        this.mBitMap = mBitMap;
        this.path = path;
    }

    public int id;
    public Bitmap mBitMap;
    public String path;
    public boolean isNet;
}
