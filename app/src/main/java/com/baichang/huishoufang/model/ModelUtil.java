package com.baichang.huishoufang.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 好吧，让你找到了，这是假的数据源
 * <p/>
 * Created by sunfusheng on 16/4/22.
 */
public class ModelUtil {

    public static final String type_scenery = "风景";
    public static final String type_building = "建筑";
    public static final String type_animal = "动物";
    public static final String type_plant = "植物";

    // 广告数据
    public static List<String> getAdData() {
        List<String> adList = new ArrayList<>();
        adList.add("http://img0.imgtn.bdimg.com/it/u=1270781761,1881354959&fm=21&gp=0.jpg");
        adList.add("http://img0.imgtn.bdimg.com/it/u=2138116966,3662367390&fm=21&gp=0.jpg");
        adList.add("http://img0.imgtn.bdimg.com/it/u=1296117362,655885600&fm=21&gp=0.jpg");
        return adList;
    }

//    // 频道数据
//    public static List<ChannelEntity> getChannelData() {
//        List<ChannelEntity> channelList = new ArrayList<>();
//        channelList.add(new ChannelEntity("中国", "天安门", "http://img2.imgtn.bdimg.com/it/u=2850936076,2080165544&fm=206&gp=0.jpg"));
//        channelList.add(new ChannelEntity("美国", "白宫", "http://img3.imgtn.bdimg.com/it/u=524208507,12616758&fm=206&gp=0.jpg"));
//        channelList.add(new ChannelEntity("英国", "伦敦塔桥", "http://img3.imgtn.bdimg.com/it/u=698582197,4250615262&fm=206&gp=0.jpg"));
//        channelList.add(new ChannelEntity("德国", "城堡", "http://img5.imgtn.bdimg.com/it/u=1467751238,3257336851&fm=11&gp=0.jpg"));
//        channelList.add(new ChannelEntity("西班牙", "巴塞罗那", "http://img5.imgtn.bdimg.com/it/u=3191365283,111438732&fm=21&gp=0.jpg"));
//        channelList.add(new ChannelEntity("意大利", "比萨斜塔", "http://img5.imgtn.bdimg.com/it/u=482494496,1350922497&fm=206&gp=0.jpg"));
//        return channelList;
//    }

//    // 运营数据
//    public static List<OperationEntity> getOperationData() {
//        List<OperationEntity> operationList = new ArrayList<>();
//        operationList.add(new OperationEntity("度假游", "度假的天堂", "http://img2.imgtn.bdimg.com/it/u=4081165325,36916497&fm=21&gp=0.jpg"));
//        operationList.add(new OperationEntity("蜜月游", "浪漫的港湾", "http://img4.imgtn.bdimg.com/it/u=4141168524,78676102&fm=21&gp=0.jpg"));
//        return operationList;
//    }

    // ListView数据
    public static List<HouseData> getTravelingData() {
        List<HouseData> travelingList = new ArrayList<>();
        travelingList.add(new HouseData(type_scenery, "大理", "中国", 1, "http://img5.imgtn.bdimg.com/it/u=2769726205,1778838650&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_scenery, "", "西班牙", 20, "http://img1.imgtn.bdimg.com/it/u=1832737924,144748431&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_scenery, "", "意大利", 21, "http://img5.imgtn.bdimg.com/it/u=2091366266,1524114981&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_scenery, "拱门", "美国", 5, "http://img4.imgtn.bdimg.com/it/u=3673198446,2175517238&fm=206&gp=0.jpg"));
        travelingList.add(new HouseData(type_plant, "荷花", "中国", 4, "http://img4.imgtn.bdimg.com/it/u=3052089044,3887933556&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_building, "", "西班牙", 18, "http://img2.imgtn.bdimg.com/it/u=140083303,1086773509&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_scenery, "", "西班牙", 19, "http://img5.imgtn.bdimg.com/it/u=1424970962,1243597989&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_animal, "水貂", "美国", 7, "http://img4.imgtn.bdimg.com/it/u=1387833256,3665925904&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_plant, "仙人掌", "美国", 8, "http://img1.imgtn.bdimg.com/it/u=3808801622,1608105009&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_scenery, "威尔士", "英国", 9, "http://img4.imgtn.bdimg.com/it/u=2440866214,1867472386&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_building, "伦敦塔桥", "英国", 10, "http://img3.imgtn.bdimg.com/it/u=3040385967,1031044866&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_animal, "", "英国", 11, "http://img3.imgtn.bdimg.com/it/u=1896821840,3837942977&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_plant, "", "英国", 12, "http://img3.imgtn.bdimg.com/it/u=2745651862,279304559&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_scenery, "", "德国", 13, "http://img3.imgtn.bdimg.com/it/u=4137420324,1489843447&fm=206&gp=0.jpg"));
        travelingList.add(new HouseData(type_building, "自由女神像", "美国", 6, "http://img3.imgtn.bdimg.com/it/u=2566161363,1140447270&fm=206&gp=0.jpg"));
        travelingList.add(new HouseData(type_building, "拉萨", "中国", 2, "http://img1.imgtn.bdimg.com/it/u=372954611,2699392190&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_animal, "熊猫", "中国", 3, "http://img0.imgtn.bdimg.com/it/u=1022702848,645282860&fm=206&gp=0.jpg"));
        travelingList.add(new HouseData(type_building, "", "德国", 14, "http://img1.imgtn.bdimg.com/it/u=3436675019,2609348874&fm=206&gp=0.jpg"));
        travelingList.add(new HouseData(type_animal, "狗熊", "德国", 15, "http://img4.imgtn.bdimg.com/it/u=4280994062,276434784&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_plant, "", "德国", 16, "http://img0.imgtn.bdimg.com/it/u=1644854868,3172549858&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_scenery, "", "西班牙", 17, "http://img4.imgtn.bdimg.com/it/u=620137884,621556624&fm=21&gp=0.jpg"));
        travelingList.add(new HouseData(type_building, "", "意大利", 22, "http://img0.imgtn.bdimg.com/it/u=3631118072,1530723002&fm=206&gp=0.jpg"));
        return travelingList;
    }

    // 区域数据
    public static List<FilterEntity> getCategoryData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("高新区", "1"));
        list.add(new FilterEntity("历下区", "2"));
        list.add(new FilterEntity("历城区", "3"));
        list.add(new FilterEntity("市中区", "4"));
        list.add(new FilterEntity("槐荫区", "5"));
        return list;
    }

    // 价格数据
    public static List<FilterEntity> getSortData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("6000-9000", "1"));
        list.add(new FilterEntity("9000-10000", "2"));
        list.add(new FilterEntity("20000以上", "3"));
        return list;
    }

    // 类型数据
    public static List<FilterEntity> getFilterData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("一居", "1"));
        list.add(new FilterEntity("二居", "2"));
        list.add(new FilterEntity("三居", "3"));
        list.add(new FilterEntity("四居", "4"));
        list.add(new FilterEntity("五居", "5"));
        list.add(new FilterEntity("五居以上", "6"));
        return list;
    }

    // ListView分类数据
    public static List<HouseData> getCategoryTravelingData(FilterEntity entity) {
        List<HouseData> list = getTravelingData();
        List<HouseData> travelingList = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).type.equals(entity.getValue()) &&
                    list.get(i).from.equals(entity.getKey())) {
                travelingList.add(list.get(i));
            }
        }
        return travelingList;
    }

    // ListView排序数据
//    public static List<HouseData> getSortTravelingData(FilterEntity entity) {
//        List<HouseData> list = getTravelingData();
//        Comparator<HouseData> ascComparator = new HouseDataComparator();
//        if (entity.getKey().equals("排序从高到低")) {
//            Collections.sort(list);
//        } else {
//            Collections.sort(list, ascComparator);
//        }
//        return list;
//    }

    // ListView筛选数据
    public static List<HouseData> getFilterTravelingData(FilterEntity entity) {
        List<HouseData> list = getTravelingData();
        List<HouseData> travelingList = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).from.equals(entity.getKey())) {
                travelingList.add(list.get(i));
            }
        }
        return travelingList;
    }

    // 暂无数据
    public static List<HouseData> getNoDataEntity(int height) {
        List<HouseData> list = new ArrayList<>();
        HouseData entity = new HouseData();
        //entity.setNoData(true);
        //entity.setHeight(height);
        list.add(entity);
        return list;
    }

}
