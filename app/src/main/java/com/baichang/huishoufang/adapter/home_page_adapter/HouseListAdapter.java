package com.baichang.huishoufang.adapter.home_page_adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.baichang.huishoufang.R;
import com.baichang.huishoufang.model.HouseData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bc.utils.ImageLoader;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HouseListAdapter extends BaseListAdapter<HouseData> {

    public static final int ONE_SCREEN_COUNT = 8; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定
    public static final int ONE_REQUEST_COUNT = 10; // 一次请求的个数

    public HouseListAdapter(Context context) {
        super(context);
    }

    public HouseListAdapter(Context context, List<HouseData> list) {
        super(context, list);
    }

    // 设置数据
    public void setData(List<HouseData> list) {
        clearAll();
        addALL(list);
        notifyDataSetChanged();
    }

    // 创建不满一屏的空数据
    public List<HouseData> createEmptyList(int size) {
        List<HouseData> emptyList = new ArrayList<>();
        if (size <= 0) return emptyList;
        for (int i = 0; i < size; i++) {
            emptyList.add(new HouseData());
        }
        return emptyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 正常数据
        final ViewHolder holder;
        if (convertView != null && convertView instanceof LinearLayout) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_house_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        HouseData houseData = getItem(position);
        holder.tvTitle.setText(houseData.from + houseData.title + houseData.type);
        holder.tvPrice.setText("均价：" + houseData.rank);
        ImageLoader.loadImage(mContext, houseData.image_url, ImageLoader.ORIGINAL_IMAGE, holder.ivImage);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_house_iv_image)
        ImageView ivImage;
        @BindView(R.id.item_house_tv_title)
        TextView tvTitle;
        @BindView(R.id.item_house_tv_price)
        TextView tvPrice;
        @BindView(R.id.item_house_tv_sate)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
