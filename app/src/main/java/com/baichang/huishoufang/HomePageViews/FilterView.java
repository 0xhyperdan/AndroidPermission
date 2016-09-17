package com.baichang.huishoufang.HomePageViews;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baichang.huishoufang.R;
import com.baichang.huishoufang.adapter.home_page_adapter.FilterAdapter;
import com.baichang.huishoufang.model.FilterData;
import com.baichang.huishoufang.model.FilterEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class FilterView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.filter_tv_area)
    TextView tvArea;
    @BindView(R.id.filter_iv_area_arrow)
    ImageView ivAreaArrow;
    @BindView(R.id.filter_tv_price)
    TextView tvPrice;
    @BindView(R.id.filter_iv_price_arrow)
    ImageView ivPriceArrow;
    @BindView(R.id.filter_tv_type)
    TextView tvType;
    @BindView(R.id.filter_iv_type_arrow)
    ImageView ivTypeArrow;
    @BindView(R.id.filter_ll_area)
    LinearLayout llCategory;
    @BindView(R.id.filter_ll_price)
    LinearLayout llPrice;
    @BindView(R.id.filter_ll_type)
    LinearLayout llType;
    @BindView(R.id.filter_content_lv_list)
    ListView lvContentList;
    @BindView(R.id.filter_ll_content_list)
    LinearLayout llContentListView;
    @BindView(R.id.view_mask_bg)
    View viewMaskBg;

    private Context mContext;
    private Activity mActivity;
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isShowing = false;
    private int filterPosition = -1;
    private int panelHeight;
    private FilterData filterData;

    private FilterEntity selectedAreaEntity; // 被选择的排序项
    private FilterEntity selectedPriceEntity; // 被选择的排序项
    private FilterEntity selectedTypeEntity; // 被选择的筛选项

    private FilterAdapter areaAdapter;
    private FilterAdapter typeAdapter;
    private FilterAdapter priceAdapter;

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_layout, this);
        ButterKnife.bind(this, view);

        initData();
        initView();
        initListener();
    }

    private void initData() {

    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        llCategory.setOnClickListener(this);
        llPrice.setOnClickListener(this);
        llType.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);
        llContentListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_ll_area:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.filter_ll_price:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.filter_ll_type:
                filterPosition = 2;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.view_mask_bg:
                hide();
                break;
        }

    }

    // 复位筛选的显示状态
    public void resetFilterStatus() {
        tvArea.setTextColor(mContext.getResources().getColor(R.color.tv_black2));
        ivAreaArrow.setImageResource(R.mipmap.arrow_sareen_down);

        tvPrice.setTextColor(mContext.getResources().getColor(R.color.tv_black2));
        ivPriceArrow.setImageResource(R.mipmap.arrow_sareen_down);

        tvType.setTextColor(mContext.getResources().getColor(R.color.tv_black2));
        ivTypeArrow.setImageResource(R.mipmap.arrow_sareen_down);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetFilterStatus();
        hide();
    }

    // 显示筛选布局
    public void showFilterLayout(int position) {
        resetFilterStatus();
        switch (position) {
            case 0:
                setAreaAdapter();
                break;
            case 1:
                setPriceAdapter();
                break;
            case 2:
                setTypeAdapter();
                break;
        }

        if (isShowing) return;
        show();
    }

    // 设置区域数据
    private void setAreaAdapter() {
        tvArea.setTextColor(mActivity.getResources().getColor(R.color.orange));
        ivAreaArrow.setImageResource(R.mipmap.arrow_sareen_up);
        //lvLeft.setVisibility(VISIBLE);
        lvContentList.setVisibility(VISIBLE);
        areaAdapter = new FilterAdapter(mContext, filterData.areas);
        lvContentList.setAdapter(areaAdapter);
        lvContentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAreaEntity = filterData.areas.get(position);
                areaAdapter.setSelectedEntity(selectedAreaEntity);
                hide();
                if (onItemAreaClickListener != null) {
                    tvArea.setText(selectedAreaEntity.getKey());
                    onItemAreaClickListener.onItemAreaClick(selectedAreaEntity);
                }
            }
        });
    }

    // 设置价格数据
    private void setPriceAdapter() {
        tvPrice.setTextColor(mActivity.getResources().getColor(R.color.orange));
        ivPriceArrow.setImageResource(R.mipmap.arrow_sareen_up);
        lvContentList.setVisibility(VISIBLE);
        priceAdapter = new FilterAdapter(mContext, filterData.prices);
        lvContentList.setAdapter(priceAdapter);
        lvContentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPriceEntity = filterData.prices.get(position);
                priceAdapter.setSelectedEntity(selectedPriceEntity);
                hide();
                if (onItemPriceClickListener != null) {
                    tvPrice.setText(selectedPriceEntity.getKey());
                    onItemPriceClickListener.onItemPriceClick(selectedPriceEntity);
                }
            }
        });
    }

    // 设置类型筛选数据
    private void setTypeAdapter() {
        tvType.setTextColor(mActivity.getResources().getColor(R.color.orange));
        ivTypeArrow.setImageResource(R.mipmap.arrow_sareen_up);
        lvContentList.setVisibility(VISIBLE);
        typeAdapter = new FilterAdapter(mContext, filterData.types);
        lvContentList.setAdapter(typeAdapter);
        lvContentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeEntity = filterData.types.get(position);
                typeAdapter.setSelectedEntity(selectedTypeEntity);
                hide();
                if (onItemTypeClickListener != null) {
                    tvType.setText(selectedTypeEntity.getKey());
                    onItemTypeClickListener.onItemTypeClick(selectedTypeEntity);
                }
            }
        });
    }

    // 动画显示
    private void show() {
        isShowing = true;
        viewMaskBg.setVisibility(VISIBLE);
        llContentListView.setVisibility(VISIBLE);
        llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = llContentListView.getHeight();
                ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });
    }

    // 隐藏动画
    public void hide() {
        isShowing = false;
        resetFilterStatus();
        viewMaskBg.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    // 是否吸附在顶部
    public void setStickyTop(boolean stickyTop) {
        isStickyTop = stickyTop;
    }

    // 设置筛选数据
    public void setFilterData(Activity activity, FilterData filterData) {
        this.mActivity = activity;
        this.filterData = filterData;
    }

    // 是否显示
    public boolean isShowing() {
        return isShowing;
    }

    // 筛选视图点击
    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

    // 分类Item点击
    private OnItemTypeClickListener onItemTypeClickListener;

    public void setOnItemTypeClickListener(OnItemTypeClickListener onItemTypeClickListener) {
        this.onItemTypeClickListener = onItemTypeClickListener;
    }

    public interface OnItemTypeClickListener {
        void onItemTypeClick(FilterEntity entity);
    }

    // 区域Item点击
    private OnItemAreaClickListener onItemAreaClickListener;

    public void setOnItemAreaClickListener(OnItemAreaClickListener onItemSortClickListener) {
        this.onItemAreaClickListener = onItemSortClickListener;
    }

    public interface OnItemAreaClickListener {
        void onItemAreaClick(FilterEntity entity);
    }

    // 价格Item点击
    private OnItemPriceClickListener onItemPriceClickListener;

    public void setOnItemPriceClickListener(OnItemPriceClickListener onItemPriceClickListener) {
        this.onItemPriceClickListener = onItemPriceClickListener;
    }

    public interface OnItemPriceClickListener {
        void onItemPriceClick(FilterEntity entity);
    }

}
