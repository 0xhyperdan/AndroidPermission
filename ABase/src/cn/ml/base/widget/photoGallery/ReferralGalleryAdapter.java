package cn.ml.base.widget.photoGallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import java.util.ArrayList;
import java.util.List;

import cn.ml.base.MLApplication;

public class ReferralGalleryAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<MyImageView> imageViews = new ArrayList<MyImageView>();

    private ImageCacheManager imageCache;

    private List<String> mItems;
    private int _position;
    //private MLLoadingDialog _dialog;

    public void setData(List<String> data, int position) {
        _position = position;
        this.mItems = data;
        notifyDataSetChanged();
    }

    protected BitmapUtils bitmapUtils;
    protected BitmapDisplayConfig bigPicDisplayConfig;

    public ReferralGalleryAdapter(Context context) {
        this.context = context;
        imageCache = ImageCacheManager.getInstance(context);
     /*   _dialog = new MLLoadingDialog(context);
        _dialog.setCanceledOnTouchOutside(false);*/

    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyImageView view = new MyImageView(context);
        view.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        String item = mItems.get(position);
        view.setTag(item);

        if (!this.imageViews.contains(view)) {
            imageViews.add(view);
        }

        return view;
    }


}
