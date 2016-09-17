package cn.ml.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class MLAdapterBase<T> extends BaseAdapter {
	private  List<T> mList = new ArrayList<T>();
	protected Context mContext;
	protected int mViewXml;
	protected int mPosition;

	public MLAdapterBase(Context context, int viewXml) {
		super();
		mViewXml = viewXml;
		mContext = context;
	}
	public List<T> getList(){
		return mList;
	}
	public void setData(List<T> list) {
		if (list == null) {
			return;
		}
		mList = list;
		notifyDataSetChanged();
	}
	public void addData(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(list);
		notifyDataSetChanged();
	}
	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList.size();
	}
	@Override
	public Object getItem(int position) {
		if(position > mList.size()-1){
			return null;
		}
		return mList.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MLItemView item = null;
		if(convertView ==null){
			item = new MLItemView(mContext);
			convertView = item;
		}else{
			item = (MLItemView) convertView;
		}
		T data = (T) getItem(position);
		item.setData(data,position);
		return item;
		
		//	return getExView(position, convertView, parent);
	}


	//protected abstract View getExView(int position, View convertView, ViewGroup parent);
	

	public class MLItemView extends LinearLayout{
		public MLItemView(Context context) {
			super(context);
			init();
		}
		public  View view;
		private void init(){
			LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		    view = LayoutInflater.from(mContext).inflate(mViewXml, null);
			addView(view,param);
		}
		public void setData(T data, int position) {
			setItemData(view,data,position);
		}
	}
	//protected abstract void initView(View view);
	protected abstract void setItemData(View view, T data,int position);

}
