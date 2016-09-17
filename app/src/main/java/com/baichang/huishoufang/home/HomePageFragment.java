package com.baichang.huishoufang.home;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.baichang.huishoufang.HomePageViews.FilterView;
import com.baichang.huishoufang.HomePageViews.HeaderAdViewView;
import com.baichang.huishoufang.HomePageViews.SmoothListView.SmoothListView;
import com.baichang.huishoufang.R;
import com.baichang.huishoufang.adapter.home_page_adapter.HeaderFilterViewView;
import com.baichang.huishoufang.adapter.home_page_adapter.HouseListAdapter;
import com.baichang.huishoufang.model.FilterData;
import com.baichang.huishoufang.model.FilterEntity;
import com.baichang.huishoufang.model.HouseData;
import com.baichang.huishoufang.model.ModelUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bc.base.BaseFrag;
import cn.bc.utils.ColorUtil;
import cn.bc.utils.DensityUtil;
import cn.ml.base.utils.ToolsUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFrag implements SmoothListView.ISmoothListViewListener {

    @BindView(R.id.listView)
    SmoothListView smoothListView;
    @BindView(R.id.fv_top_filter)
    FilterView fvTopFilter;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;

    private View view;
    private Context mContext;
    private Activity mActivity;
    private int mScreenHeight; // 屏幕高度

    private List<String> adList = new ArrayList<>(); // 广告数据
    private List<HouseData> houseDataList = new ArrayList<>(); // ListView数据

    private HeaderAdViewView listViewAdHeaderView; // 广告视图
    private HeaderFilterViewView headerFilterViewView; // 分类筛选视图
    private FilterData filterData; // 筛选数据
    private HouseListAdapter mAdapter; // 主页数据

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 50; // 标题栏的高度
    private int filterPosition = -1; // 点击FilterView的位置：区域(0)、价格(1)、类型(2)

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 4; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        mContext = getActivity();
        mActivity = getActivity();
        mScreenHeight = DensityUtil.getWindowHeight(getActivity());

        // 筛选数据
        filterData = new FilterData();
        filterData.areas = (ModelUtil.getCategoryData());
        filterData.prices = (ModelUtil.getSortData());
        filterData.types = (ModelUtil.getFilterData());

        // 广告数据
        adList = ModelUtil.getAdData();

        // ListView数据
        houseDataList = ModelUtil.getTravelingData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            initView(inflater);
        } else {
            if (container != null) {
                container.removeView(view);
            }
        }
        return view;
    }

    private void initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_home_page, null);
        ButterKnife.bind(this, view);
        fvTopFilter.setVisibility(View.GONE);

        // 设置筛选数据
        fvTopFilter.setFilterData(mActivity, filterData);

        // 设置广告数据
        listViewAdHeaderView = new HeaderAdViewView(getActivity());
        listViewAdHeaderView.fillView(adList, smoothListView);

        // 设置筛选数据
        headerFilterViewView = new HeaderFilterViewView(getActivity());
        headerFilterViewView.fillView(new Object(), smoothListView);

        // 设置ListView数据
        mAdapter = new HouseListAdapter(mContext, houseDataList);
        smoothListView.setAdapter(mAdapter);

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;

        initListener();
    }


    private void initListener() {
        // (假的ListView头部展示的)筛选视图点击
        headerFilterViewView.setOnFilterClickListener(new HeaderFilterViewView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mContext, titleViewHeight));
            }
        });

        // (真正的)筛选视图点击
        fvTopFilter.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (isStickyTop) {
                    filterPosition = position;
                    fvTopFilter.showFilterLayout(position);
                    if (titleViewHeight - 3 > filterViewTopSpace || filterViewTopSpace > titleViewHeight + 3) {
                        smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mContext, titleViewHeight));
                    }
                }
            }
        });

        // 区域Item点击
        fvTopFilter.setOnItemAreaClickListener(new FilterView.OnItemAreaClickListener() {
            @Override
            public void onItemAreaClick(FilterEntity entity) {
                //fillAdapter(ModelUtil.getSortTravelingData(entity));
                showMessage(getActivity(), entity.getKey());
            }
        });

        // 价格Item点击
        fvTopFilter.setOnItemPriceClickListener(new FilterView.OnItemPriceClickListener() {
            @Override
            public void onItemPriceClick(FilterEntity entity) {
                //fillAdapter(ModelUtil.getSortTravelingData(entity));
                showMessage(getActivity(), entity.getKey());
            }
        });

        // 类型Item点击
        fvTopFilter.setOnItemTypeClickListener(new FilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(FilterEntity entity) {
                //fillAdapter(ModelUtil.getFilterTravelingData(entity));
                showMessage(getActivity(), entity.getKey());
            }
        });

        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);
        smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0) return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = smoothListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(mContext, itemHeaderAdView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
                }

                // 处理筛选是否吸附在顶部
                if (filterViewTopSpace > titleViewHeight) {
                    isStickyTop = false; // 没有吸附在顶部
                    fvTopFilter.setVisibility(View.GONE);
                } else {
                    isStickyTop = true; // 吸附在顶部
                    fvTopFilter.setVisibility(View.VISIBLE);
                }

                if (firstVisibleItem > filterViewPosition) {
                    isStickyTop = true;
                    fvTopFilter.setVisibility(View.VISIBLE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    fvTopFilter.showFilterLayout(filterPosition);
                }

                fvTopFilter.setStickyTop(isStickyTop);

                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });
    }

    // 填充数据
    private void fillAdapter(List<HouseData> list) {
        if (list == null || list.size() == 0) {
            smoothListView.setLoadMoreEnable(false);
            int height = mScreenHeight - DensityUtil.dip2px(mContext, 95); // 95 = 标题栏高度 ＋ FilterView的高度
            //mAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
            smoothListView.setLoadMoreEnable(list.size() > HouseListAdapter.ONE_REQUEST_COUNT);
            mAdapter.setData(list);
        }
    }

    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (adViewTopSpace > 0) {
            fraction = 1f - adViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f || isStickyTop) {
            isStickyTop = true;
            //viewTitleBg.setAlpha(0f);
            //viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        } else {
            //viewTitleBg.setAlpha(1f - fraction);
            //viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.orange));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listViewAdHeaderView != null) {
            listViewAdHeaderView.stopADRotate();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listViewAdHeaderView != null) {
            listViewAdHeaderView.stopADRotate();
        }
    }

    @Override
    public void onRefresh() {
        smoothListView.stopRefresh();
        smoothListView.setRefreshTime(ToolsUtil.getRefreshTime());
    }

    @Override
    public void onLoadMore() {
        smoothListView.stopLoadMore();
    }
}
