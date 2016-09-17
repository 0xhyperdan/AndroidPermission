package com.baichang.huishoufang.comment;

/**
 * Created by iscod on 2016/3/11.
 */

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.baichang.huishoufang.R;

import java.util.ArrayList;

import cn.bc.base.BaseAct;

public class GuideActivity extends BaseAct {
    private ViewPager viewPager;

    /**
     * 装分页显示的view的数组
     */
    private ArrayList<View> pageViews;

    /**
     * 将小圆点的图片用数组表示
     */
    private ImageView[] imageViews;

    //包裹小圆点的LinearLayout
    private ViewGroup viewPoints;
    private LinearLayout.LayoutParams pointLayoutParams;
    private int state;
    private final int LAST_PAGE = 10010;
    private final int NORMAL = 0;
    private int mLastX;
    private ArrayList<String> bitmapImage = new ArrayList<String>();
    private int bitmapLast;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle args = intent.getExtras();
            if (args != null) {
                bitmapImage = args.getStringArrayList("Image");
            }
        }
        //将要分页显示的View装入数组中
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        int bitmapArraySize = bitmapImage.size();
        bitmapLast = bitmapArraySize - 1;
        for (int i = 0; i < bitmapArraySize; i++) {
            View view = inflater.inflate(R.layout.activity_guide_view_pager, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.guide_iv_image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(bitmapImage.get(i)));
            pageViews.add(view);
        }
        //创建imageviews数组，大小是要显示的图片的数量
        imageViews = new ImageView[pageViews.size()];
        //实例化小圆点的linearLayout和viewpager
        viewPoints = (ViewGroup) findViewById(R.id.view_group);
        viewPager = (ViewPager) findViewById(R.id.guide_pages);
        //添加小圆点的图片
        pointLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        pointLayoutParams.setMargins(5, 20, 5, 20);
        int size = pageViews.size();
        for (int i = 0; i < size; i++) {
            ImageView pointImageView = new ImageView(GuideActivity.this);
            //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == 0) {
                pointImageView.setBackgroundResource(R.drawable.play_display);
            } else {
                pointImageView.setBackgroundResource(R.drawable.play_hide);
            }
            //设置小圆点pointImageView的参数.
            pointImageView.setLayoutParams(pointLayoutParams);//创建一个宽高均为20 的布局
            //将小圆点layout添加到数组中
            imageViews[i] = pointImageView;
            //将pointImageView添加到小圆点视图组
            viewPoints.addView(imageViews[i]);
        }
        //设置viewpager的适配器和监听事件
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.addOnPageChangeListener(new GuidePageChangeListener());
        viewPager.setOnTouchListener(new ViewPagerTouch());
    }

    private Button.OnClickListener Button_OnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            //设置已经引导
            //setGuided();
            //跳转
            Intent mIntent = new Intent();
            mIntent.setClass(GuideActivity.this, HomeMainActivity.class);
            GuideActivity.this.startActivity(mIntent);
            GuideActivity.this.finish();
        }
    };

    private void setGuided() {
    }

    class GuidePageAdapter extends PagerAdapter {
        //销毁position位置的界面
        @Override
        public void destroyItem(View v, int position, Object arg2) {
            ((ViewPager) v).removeView(pageViews.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        //获取当前窗体界面数
        @Override
        public int getCount() {
            return pageViews.size();
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager) v).addView(pageViews.get(position));
//            Button btn = (Button) v.findViewById(R.id.btn_close_guide);
//            // 测试页卡1内的按钮事件
//            if (position == 2) {
//                btn.setVisibility(View.VISIBLE);
//                btn.setOnClickListener(Button_OnClickListener);
//            } else {
//                btn.setVisibility(View.INVISIBLE);
//            }
            return pageViews.get(position);
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View v, Object arg1) {
            return v == arg1;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }


    class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            //Log.d("CID", "onPageScrollStateChanged:" + arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            //Log.d("CID", "onPageScrolled:" + "==arg0==:" + arg0 + "==arg1==" + arg1 + "==arg2==" + arg2);
        }

        @Override
        public void onPageSelected(int position) {
            if (position == bitmapLast) {
                state = LAST_PAGE;
            } else {
                state = NORMAL;
            }
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(R.drawable.play_display);
                //不是当前选中的page，其小圆点设置为未选中的状态
                if (position != i) {
                    imageViews[i].setBackgroundResource(R.drawable.play_hide);
                }
            }
        }
    }

    class ViewPagerTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                //手指放到屏幕时候的x坐标
                mLastX = (int) event.getX();
            }
            if (action == MotionEvent.ACTION_MOVE) {
                //当前x坐标
                int x = (int) event.getX();
                //当前页面是最后一页并且右滑动距离>200
                if (state == LAST_PAGE && (x - mLastX) < -200) {
                    //设置已经引导
                    setGuided();
                    //跳转
                    startAct(GuideActivity.this, HomeMainActivity.class);
                    GuideActivity.this.finish();
                    return true;
                }
            }
            return false;
        }
    }

}