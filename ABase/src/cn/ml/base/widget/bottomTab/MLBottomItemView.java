package cn.ml.base.widget.bottomTab;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.ml.base.R;

public class MLBottomItemView extends RelativeLayout{

	private Context _content;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public MLBottomItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		_content = context;
		init();
	}

	public MLBottomItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_content = context;
		init();
	}

	public MLBottomItemView(Context context) {
		super(context);
		_content = context;
		init();
	}

	//当前的索引
	private int mIndex;
	private ImageView _iconIv;
	private TextView _nameTv;
/*	private RelativeLayout _countRl;
    private TextView _countTv;*/
	private void init() {
			View view = LayoutInflater.from(_content).inflate(R.layout.bottom_tab, null);
			addView(view);
			_iconIv = (ImageView) view.findViewById(R.id.tab_iv_icon);
			_nameTv = (TextView) view.findViewById(R.id.tab_tv_name);
			/*_countRl = (RelativeLayout) view.findViewById(R.id.tab_lv_count);
			_countTv = (TextView) view.findViewById(R.id.tab_tv_count);*/
	}
	
	public void setData(int position,String text,int textColor,Drawable drawable){
		
		/*if(position==2){
			_countRl.setVisibility(View.VISIBLE);
		}else{
			_countRl.setVisibility(View.INVISIBLE);
		}*/
		
		mIndex = position;
		_nameTv.setText(text);
		_nameTv.setTextColor(textColor);
	//	_iconIv.setBackground(drawable);
		_iconIv.setBackgroundDrawable(drawable);
	}

	public void setData(int textColor,Drawable drawable){
		_nameTv.setTextColor(textColor);
	//	_iconIv.setBackground(drawable);
		_iconIv.setBackgroundDrawable(drawable);
	}
	public int getIndex() {
		return mIndex;
	}
	
	//============红圆圈数量===========================================
/*	public void  setCount(String text){
		if(StringUtil.isEmpty(text)||text.equalsIgnoreCase("0")){
			_countRl.setVisibility(View.INVISIBLE);
		}else{
			_countRl.setVisibility(View.VISIBLE);
			_countTv.setText(text);
		}
	}*/
	
}
