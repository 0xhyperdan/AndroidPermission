package cn.ml.base.widget.sample;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.ml.base.utils.MLBitmapUtil;
import cn.ml.base.utils.MLViewUtil;

/**
 * 
 * 选择对话框
 * 
 * @author Soniy7x
 *
 */
public class MLActionSheetDialog extends PopupWindow{
	
	public static final int TYPE_IOS = 0;
	public static final int TYPE_ANDROID = 1;
	
	private Context context;
	private String title;
	private Typeface typeface;
	private LinearLayout rootLayout;
	private LinearLayout contentLayout;
	private LinearLayout parentLayout;
	private ScrollView sheetLayout;
	private List<SheetItem> sheetItemList;
	private OnActionSheetClickListener onActionSheetClickListener;
	
		private int DP = 0;
	private int WIDTH = 0;
	private int HEIGHT = 0;
	private int type = TYPE_IOS;
	private int backgroundColor = Color.parseColor("#FFFFFF");
	private int itemBackgroundColor = Color.parseColor("#FFFFFF");
	private int titleColor = Color.parseColor("#8F8F8F");
	private int cancelColor = Color.parseColor("#3DB399");

	/**
	 * 构造方法
	 * @param context
	 */
	@SuppressWarnings("deprecation")
	public MLActionSheetDialog(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		this.context = context;

		this.DP = (int) MLViewUtil.dip2px(context, 1);
		this.WIDTH = display.getWidth();
		this.HEIGHT = display.getHeight();
	}
	
	/**
	 * 构建内容视图 - IOS
	 * @return 内容视图
	 */
	@SuppressWarnings("deprecation")
	private View createContentViewForIOS(){
		//根布局
		rootLayout = new LinearLayout(context);
		rootLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rootLayout.setOrientation(LinearLayout.VERTICAL);
		rootLayout.setBackgroundColor(Color.parseColor("#77000000"));
		rootLayout.setGravity(Gravity.BOTTOM);
		
		parentLayout = new LinearLayout(context);
		parentLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		parentLayout.setOrientation(LinearLayout.VERTICAL);
		parentLayout.setBackgroundColor(Color.parseColor("#00000000"));

		LinearLayout childLayout = new LinearLayout(context);
		LayoutParams childParams = getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		childParams.setMargins(8 * DP, 0, 8 * DP, 0);
		childLayout.setLayoutParams(childParams);
		childLayout.setOrientation(LinearLayout.VERTICAL);
		childLayout.setBackgroundDrawable(MLBitmapUtil.createBackground(Color.WHITE, 204, 10));
		
		//标题
		TextView titleTextView = new TextView(context);
		titleTextView.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		titleTextView.setPadding(0, 10 * DP, 0, 10 * DP);
		titleTextView.setMinHeight(45 * DP);
		titleTextView.setTextSize(14);
		titleTextView.setGravity(Gravity.CENTER);
		titleTextView.setTextColor(Color.parseColor("#8F8F8F"));
		if (title == null) {
			titleTextView.setVisibility(View.GONE);
		}else{
			titleTextView.setVisibility(View.VISIBLE);
			titleTextView.setText(title);
		}
		
		//内容外层布局
		sheetLayout = new ScrollView(context);
		sheetLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		sheetLayout.setFadingEdgeLength(0);
		
		//内容内层布局
		contentLayout = new LinearLayout(context);
		contentLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		sheetLayout.addView(contentLayout);
		
		//取消按钮
		final TextView cancelTextView = new TextView(context);
		LayoutParams params = getParams(LayoutParams.MATCH_PARENT, 45 * DP);
		params.setMargins(8 * DP, 8 * DP, 8 * DP, 8 * DP);
		cancelTextView.setLayoutParams(params);
		cancelTextView.setTextColor(Color.parseColor("#3DB399"));
		cancelTextView.setTextSize(16);
		cancelTextView.setGravity(Gravity.CENTER);
		cancelTextView.setText("取消");
		cancelTextView.setBackgroundDrawable(MLBitmapUtil.createBackground(Color.WHITE, 204, 10));
		cancelTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		
		//控件创建结束
		childLayout.addView(titleTextView);
		childLayout.addView(sheetLayout);
		parentLayout.addView(childLayout);
		parentLayout.addView(cancelTextView);
		rootLayout.addView(parentLayout);
		if (typeface != null) {
			titleTextView.setTypeface(typeface);
			cancelTextView.setTypeface(typeface);
		}
		return rootLayout;
	}
	
	/**
	 * 构建内容视图 - ANDROID
	 * @return 内容视图
	 */
	private View createContentViewForANDROID(){
		//根布局
		rootLayout = new LinearLayout(context);
		rootLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rootLayout.setOrientation(LinearLayout.VERTICAL);
		rootLayout.setBackgroundColor(Color.parseColor("#55000000"));
		rootLayout.setGravity(Gravity.BOTTOM);
		
		parentLayout = new LinearLayout(context);
		parentLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		parentLayout.setOrientation(LinearLayout.VERTICAL);
		parentLayout.setBackgroundColor(backgroundColor);
		
		LinearLayout childLayout = new LinearLayout(context);
		LayoutParams childParams = getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		childLayout.setLayoutParams(childParams);
		childLayout.setOrientation(LinearLayout.VERTICAL);
		
		//标题
		TextView titleTextView = new TextView(context);
		titleTextView.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		titleTextView.setMinHeight(48 * DP);
		titleTextView.setTextSize(12);
		titleTextView.setGravity(Gravity.CENTER);
		titleTextView.setTextColor(titleColor);
		if (title == null) {
			titleTextView.setVisibility(View.GONE);
		}else{
			titleTextView.setVisibility(View.VISIBLE);
			titleTextView.setText(title);
		}
		
		//内容外层布局
		sheetLayout = new ScrollView(context);
		sheetLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		sheetLayout.setFadingEdgeLength(0);
		
		//内容内层布局
		contentLayout = new LinearLayout(context);
		contentLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		sheetLayout.addView(contentLayout);
		
		//取消按钮
		final TextView cancelTextView = new TextView(context);
		LayoutParams params = getParams(LayoutParams.MATCH_PARENT, 52 * DP);
		params.setMargins(0, 8 * DP, 0, 0);
		cancelTextView.setLayoutParams(params);
		cancelTextView.setTextColor(cancelColor);
		cancelTextView.setTextSize(16);
		cancelTextView.setGravity(Gravity.CENTER);
		cancelTextView.setText("取消");
		cancelTextView.setBackgroundColor(itemBackgroundColor);
		cancelTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		
		//控件创建结束
		childLayout.addView(titleTextView);
		childLayout.addView(sheetLayout);
		parentLayout.addView(childLayout);
		parentLayout.addView(cancelTextView);
		rootLayout.addView(parentLayout);
		if (typeface != null) {
			titleTextView.setTypeface(typeface);
			cancelTextView.setTypeface(typeface);
		}
		return rootLayout;
	}
	
	/**
	 * 构建视图
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private MLActionSheetDialog builder() {
		setBackgroundDrawable(new BitmapDrawable());
		if (type == 0) {
			setContentView(createContentViewForIOS());
		}else {
			setContentView(createContentViewForANDROID());
		}
		setWidth(WIDTH);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		setFocusable(true);
		return this;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 根据宽高获得参数
	 * @param width 宽
	 * @param height 高
	 * @return
	 */
	private LayoutParams getParams(int width, int height){
        return new LayoutParams(width, height);
    }
	
	/**
	 * 设置标题内容
	 * @param title 内容
	 * @return
	 */
	public MLActionSheetDialog setTitle(String title) {
		this.title = title;
		return this;
	}
	
	/**
	 * 添加选项
	 * @param strItem 选项标题
	 * @param color 选项标题颜色
	 * @param listener 选项点击监听
	 * @return
	 */
	public MLActionSheetDialog addSheetItem(String strItem, int color, OnSheetItemClickListener listener) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		sheetItemList.add(new SheetItem(strItem, color, listener));
		return this;
	}
	
	/**
	 * 添加选项
	 * @param strItem 选项标题
	 * @param color 选项标题颜色
	 * @return
	 */
	public MLActionSheetDialog addSheetItem(String strItem, int color) {
		return addSheetItem(strItem, color, null);
	}

	/**
	 * 设置选项
	 */
	private void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}

		int size = sheetItemList.size();
		if (size >= 7) {
			LayoutParams params = (LayoutParams) sheetLayout.getLayoutParams();
			params.height = HEIGHT / 2;
			sheetLayout.setLayoutParams(params);
		}

		for (int i = 1; i <= size; i++) {
			final int index = i;
			SheetItem sheetItem = sheetItemList.get(i - 1);
			String itemName = sheetItem.name;
			int color = sheetItem.color;
			final OnSheetItemClickListener listener = sheetItem.itemClickListener;

			TextView textView = new TextView(context);
			textView.setText(itemName);
			textView.setTextSize(16);
			textView.setGravity(Gravity.CENTER);
			if (typeface != null) {
				textView.setTypeface(typeface);
			}

			textView.setTextColor(color);
			if (type == TYPE_ANDROID) {
				textView.setBackgroundColor(itemBackgroundColor);				
				textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 52 * DP));
			}else {
				textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 45 * DP));
			}

			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (listener != null) {
						listener.onClick();
					}
					if (onActionSheetClickListener != null) {
						onActionSheetClickListener.onClick(index);
					}
					dismiss();
				}
			});

			View view = new View(context);
			LayoutParams params = getParams(LayoutParams.MATCH_PARENT, (int)(0.5 * DP));
			params.setMargins(2 * DP, 0, 2 * DP, 0);
			view.setLayoutParams(params);
			view.setBackgroundColor(Color.parseColor("#33444444"));
			
			contentLayout.addView(view);
			contentLayout.addView(textView);
		}
	}

	/**
	 * 展示控件
	 */
	public void show() {
		builder();
		setSheetItems();
		AlphaAnimation animation1 = new AlphaAnimation(0.3f, 1.0f);
		animation1.setDuration(200);
		TranslateAnimation animation = new TranslateAnimation(0, 0, HEIGHT, 0);
		animation.setDuration(320);
		rootLayout.startAnimation(animation1);
		parentLayout.startAnimation(animation);
		showAtLocation(((ViewGroup)(((Activity) context).findViewById(android.R.id.content))).getChildAt(0), Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 设置自定义字体
	 * @param typeface
	 */
	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
	}
	
	/**
	 * 设置全局监听
	 * @param onActionSheetClickListener 监听器
	 */
	public void setOnActionSheetClickListener(OnActionSheetClickListener onActionSheetClickListener) {
		this.onActionSheetClickListener = onActionSheetClickListener;
	}

	public interface OnSheetItemClickListener {
		void onClick();
	}
	
	public interface OnActionSheetClickListener {
		void onClick(int which);
	}

	public class SheetItem {
		int color;
		String name;
		OnSheetItemClickListener itemClickListener;

		public SheetItem(String name, int color, OnSheetItemClickListener itemClickListener) {
			this.name = name;
			this.color = color;
			this.itemClickListener = itemClickListener;
		}
	}
}
