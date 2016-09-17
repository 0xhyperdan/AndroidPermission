package cn.ml.base.widget.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.ml.base.R;
import cn.ml.base.utils.IEvent;

@SuppressLint("NewApi")
public class MLStar extends LinearLayout{

	private Context mContext;
	private   int count_f;

	public MLStar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init();
	}

	public MLStar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		   TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.star);   
	        count_f = array.getInt(R.styleable.star_count, 0);
		init();
	}

	public MLStar(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	private void init(){
		setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		for(int i=0;i<count_f;i++){
			final int count = getChildCount()+1;
			ImageView star_f = new ImageView(mContext);
			star_f.setImageResource(R.drawable.star_f);
			star_f.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if(mEvent!=null){
						setStarCount(count);
						mEvent.onEvent(null, count);
					}
				}
			});
			addView(star_f, param);
		}
		for(int i=0;i<(5-count_f);i++){
			final int count = getChildCount()+1;
			ImageView star_n = new ImageView(mContext);
			star_n.setImageResource(R.drawable.star_n);
			star_n.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if(mEvent!=null){
						setStarCount(count);
						mEvent.onEvent(null, count);
					}
				}
			});
			addView(star_n, param);
		}
	}
	
	public void setStarCount(int count){
		count_f = count;
		removeAllViews();
		init();
	}

	private IEvent mEvent;
	public void setOnClickEvent(final IEvent event){


		mEvent = event;

	/*	for(int i=0;i<getChildCount();i++){
			//ImageView iv = (ImageView) ;
			final int finalI = i;
			getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					setStarCount(finalI+1);
					event.onEvent(null, finalI+1);
				}
			});
		}
*/
	}
		
}
