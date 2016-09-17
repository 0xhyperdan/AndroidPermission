package cn.ml.base.utils.photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcello on 2015/5/16.
 */
public class MLPicAllData implements Serializable{

    public int position;
    public List<MLPicData> pics;

    public MLPicAllData() {
        pics = new ArrayList<>();
    }
}
