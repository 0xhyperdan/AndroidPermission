package cn.ml.base.widget.sample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ParseException;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
/**
 * 月份选择器
 * @author Marcello
 *
 */
public class MLPickerMonthDialog extends DatePickerDialog {
	public MLPickerMonthDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		super(context,DatePickerDialog.THEME_HOLO_LIGHT,callBack, year, monthOfYear, dayOfMonth);
		this.setTitle(year + "年" + (monthOfYear + 1) + "月");
		
		((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		this.setTitle(year + "年" + (month + 1) + "月");
	}


}

