package cn.ml.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MLAdapterBase2<T> extends BaseAdapter {
	private  List<T> mList = new ArrayList<T>();
	protected Context mContext;
	protected int mViewXml;
	protected int mPosition;

	public MLAdapterBase2(Context context, int viewXml) {
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

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext)
					.inflate(mViewXml, parent, false);
		}

		T data = (T) getItem(position);
		setItemData(convertView,data,position);
		return convertView;
	}
	protected abstract void setItemData(View view, T data,int position);
}
