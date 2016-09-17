package com.baichang.huishoufang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterData implements Serializable {

    public List<FilterEntity> areas = new ArrayList<>();
    public List<FilterEntity> prices = new ArrayList<>();
    public List<FilterEntity> types = new ArrayList<>();

}
