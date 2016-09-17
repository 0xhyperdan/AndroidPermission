package cn.ml.base.widget.bottomTab;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import cn.ml.base.widget.sample.MLUnSlideViewPager;

public class MLBottomTab extends LinearLayout{

	private Context _content;
	
	/** tab的文字. */
	private List<String> tabItemTextList = null;
	
	/** tab的图标. */
	private List<Drawable> tabItemDrawableList = null;
	
	/** 当前选中编号. */
	private int mSelectedTabIndex = 0;
	
	private MLUnSlideViewPager _viewPager;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public MLBottomTab(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		_content = context;
		init();
	}

	public MLBottomTab(Context context, AttributeSet attrs) {
		super(context, attrs);
		_content = context;
		init();
	}

	public MLBottomTab(Context context) {
		super(context);
		_content = context;
		init();
	}
	
	private Paint paint;
	private void init(){
		setWillNotDraw(false);
		paint=new Paint(Paint.DITHER_FLAG);//创建一个画笔
		paint.setStyle(Style.STROKE);//设置非填充
		  paint.setStrokeWidth(1);//笔宽5像素
		  paint.setColor(Color.LTGRAY);//设置为红笔
		  paint.setAntiAlias(true);//锯齿不显示
	}
	
	  @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(0, 0, getWidth(), 0, paint);
	  }

	/**
     * 描述：增加一组内容与tab.
     *
     * @param tabTexts the tab texts
	 * @param viewpager 
     * @param fragments the fragments
     */
	public void addItemViews(List<String> tabTexts,List<Drawable> drawables, MLUnSlideViewPager viewpager){
		_viewPager = viewpager;
		tabItemTextList = tabTexts;
		tabItemDrawableList = drawables;
	//	tabItemTextList.addAll(tabTexts);
	//	tabItemDrawableList.addAll(drawables);
		notifyTabDataSetChanged();
	}

	private void notifyTabDataSetChanged() {
        final int count = tabItemTextList.size();
        for (int i = 0; i < count; i++) {
        		addTab(tabItemTextList.get(i), i,tabItemDrawableList.get(i*2));
        }		
	}

	private void addTab(String text, int i, Drawable drawable) {
		MLBottomItemView tabView = new MLBottomItemView(_content);
		if(i==0){
			//初始化时，默认选中第一个
			tabView.setData(i, text, Color.parseColor("#ED7602"), tabItemDrawableList.get(1));
		}else{
			tabView.setData(i, text, Color.parseColor("#bbbbbb"), drawable);
		}
		tabView.setOnClickListener(mTabClickListener);
		addView(tabView, new LayoutParams(0,LayoutParams.MATCH_PARENT,1));
	}
	
	private OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
        	MLBottomItemView tabView = (MLBottomItemView)view;
            setCurrentItem(tabView.getIndex());
        }

		private void setCurrentItem(int index) {
		    mSelectedTabIndex = index;
		    final int count = tabItemTextList.size();
		  
	        for (int i = 0; i < count; i++) {
	        	  MLBottomItemView tabView = (MLBottomItemView) getChildAt(i);
	        	final boolean isSelected = (i == index);
	            if (isSelected) {
	            	//选中
	            	tabView.setData(Color.parseColor("#ED7602"), tabItemDrawableList.get(index*2+1));
	            }else{
	            	tabView.setData(Color.parseColor("#bbbbbb"), tabItemDrawableList.get(i*2));
	            }
	        }		
	        //选择viewPager 的当前页
	        _viewPager.setCurrentItem(index);
		}
    };
}
