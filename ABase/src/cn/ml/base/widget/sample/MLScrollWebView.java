package cn.ml.base.widget.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Marcello on 2015/7/4.
 */
public class MLScrollWebView extends WebView {
   /* private Context context;
    private boolean isScroll = true;// webview 是否滚动

    PointF downP = new PointF();
    *//** 触摸时当前的点 **//*
    PointF curP = new PointF();

    public ScroWebView(Context context) {
        super(context);
    }

    public ScroWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScroWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当拦截触摸事件到达此位置的时候，返回true，
        //说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
        //和android的触屏事件由上至下一层一层传播有关
        return isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //webview被点击到，即可滑动
                isScroll = true ;
                curP.x = event.getX();
                curP.y = event.getY();
                //通知父控件现在进行的是本控件的操作，不要对我的操作进行干扰
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float lastY = event.getY(event.getPointerCount() - 1);
                if (isBottom())//如果到达底部，先设置为不能滚动
                    isScroll = false;
                // 如果到达底部，但开始向上滚动，那么webview可以滚动
                if (isBottom() && (curP.y - lastY < 0))
                    isScroll = true;
                if(isTop())//滑到顶部不能再滑
                    isScroll = false ;
                if(isTop() && (curP.y - lastY >0))//滑动到顶部，向下滑，可以滑到
                    isScroll = true ;
                getParent().requestDisallowInterceptTouchEvent(isScroll);
                break;
            case MotionEvent.ACTION_UP:
                isScroll = false ;
                break;
        }

        return super.onTouchEvent(event);
    }

    *//**
     * 判断是否到WebView达底部
     *//*
    private boolean isBottom() {
        // WebView的总高度
        float contentHeight = getContentHeight() * getScale();
        // WebView的现高度
        float currentHeight = getHeight() + getScrollY();
        // 之间的差距小于2便认为滑动到底部
        return contentHeight - currentHeight < 1;
    }

    private boolean isTop(){
        //当ScrollY为0是到达顶部
        return getScrollY() == 0 ;
    }*/


    private GestureDetector mGestureDetector;
    OnTouchListener mGestureListener;

    public MLScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }

}
