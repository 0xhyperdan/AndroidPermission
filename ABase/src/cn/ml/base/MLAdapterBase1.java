package cn.ml.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MLAdapterBase1<T> extends BaseAdapter {
//	private  List<T> mList = new LinkedList<T>();
	private  List<T> mList = new ArrayList<T>();
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
		System.out.println(position);
		System.out.println();
		return getExView(position, convertView, parent);
	}
	protected abstract View getExView(int position, View convertView, ViewGroup parent);
}
