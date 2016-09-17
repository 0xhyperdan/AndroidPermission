package cn.ml.base.widget.citypop;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.ml.base.R;
import cn.ml.base.utils.IEvent;

import com.lidroid.xutils.ViewUtils;

public class MLCityPop extends PopupWindow{
/*	@ViewInject(R.id.personal_btn_del)
	private Button _delBtn;*/
	
	private CityPicker _cityPicker;

	private TextView _okBtn;
	
	public MLCityPop(Activity context,final IEvent<String> event) {  
		super(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.city_pop, null);
        ViewUtils.inject(this, view);
        
    	_cityPicker = (CityPicker) view.findViewById(R.id.login_citypicker);
  		_okBtn = (TextView) view.findViewById(R.id.login_city_ok);
  		_okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String cityString = _cityPicker.getCity_string();
				if(cityString!=null&&!cityString.equalsIgnoreCase("")){
					event.onEvent(null, cityString);
				}
				dismiss();
			}
		});
        
        
      //设置LTSelectPopupWindow的View  
        this.setContentView(view);  
        //设置LTSelectPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.MATCH_PARENT);  
        //设置LTSelectPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.MATCH_PARENT);  
        //设置LTSelectPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        //设置LTSelectPopupWindow弹出窗体动画效果  
     //   this.setAnimationStyle(R.style.AnimBottom);  
        //实例化一个ColorDrawable颜色为半透明    
       ColorDrawable dw = new ColorDrawable(0xb0000000);  
        //设置LTSelectPopupWindow弹出窗体的背景  
       this.setBackgroundDrawable(dw);  
        
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        
        view.setOnTouchListener(new OnTouchListener() {  
            public boolean onTouch(View v, MotionEvent event) {  
                  
                int height = view.findViewById(R.id.login_ly_status).getTop();  
                int bottom = view.findViewById(R.id.login_ly_status).getBottom();
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP){  
                    if(y<height||y>bottom){  
                        dismiss();  
                    }  
                }                 
                return true;  
            }  
        });  
        }
	
/*	public void setButtonOnclickListener(OnClickListener clickListener){
		_delBtn.setOnClickListener(clickListener);
		_blackBtn.setOnClickListener(clickListener);
		_reportBtn.setOnClickListener(clickListener);
		_weiboBtn.setOnClickListener(clickListener);
	}*/
}
