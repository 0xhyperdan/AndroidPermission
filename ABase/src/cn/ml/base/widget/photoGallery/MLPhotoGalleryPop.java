package cn.ml.base.widget.photoGallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.lidroid.xutils.ViewUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.ml.base.R;
import cn.ml.base.utils.MLToastUtils;

public class MLPhotoGalleryPop extends PopupWindow{


	private PicGallery gallery;

	private ImageView btnSave;


	private ReferralGalleryAdapter mAdapter;
	public MLPhotoGalleryPop(final Context context,List<String> images,int position) {
		super(context);
		final View view = LayoutInflater.from(context).inflate(R.layout.widget_gallery_pop, null);
        ViewUtils.inject(this, view);
        
    	gallery = (PicGallery) view.findViewById(R.id.pic_gallery);
		btnSave = (ImageView) view.findViewById(R.id.btn_save);

		gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
		gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
		gallery.setDetector(new GestureDetector(context,
				new MySimpleGesture()));
		mAdapter = new ReferralGalleryAdapter(context);
		gallery.setAdapter(mAdapter);
		
		mAdapter.setData(images,position);
		gallery.setSelection(position);
       /* Drawable drawable = Drawable.createFromPath(path);
        _photoIv.setImageDrawable(drawable);*/
        
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
        
     /*   view.setOnTouchListener(new OnTouchListener() {  
            public boolean onTouch(View v, MotionEvent event) {  
            	 dismiss();  
                int height = view.findViewById(R.id.choose_album).getTop();  
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP){  
                    if(y>height){  
                        dismiss();  
                    }  
                }                 
                return true;  
            }  
        });  */

//		btnSave.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String url = (String) gallery.getSelectedItem();
//				Bitmap bitmap = MLApplication.IMAGE_CACHE.get(url).getData();
//				saveImageToGallery(context,bitmap);
//			}
//		});

        }
	
	private class MySimpleGesture extends SimpleOnGestureListener {
		// 按两下的第二下Touch down时触发
		public boolean onDoubleTap(MotionEvent e) {

			View view = gallery.getSelectedView();
			if (view instanceof MyImageView) {
				MyImageView imageView = (MyImageView) view;
				if (imageView.getScale() > imageView.getMiniZoom()) {
					imageView.zoomTo(imageView.getMiniZoom());
				} else {
					imageView.zoomTo(imageView.getMaxZoom());
				}

			} else {

			}
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// Logger.LOG("onSingleTapConfirmed",
			// "onSingleTapConfirmed excute");
			// mTweetShow = !mTweetShow;
			// tweetLayout.setVisibility(mTweetShow ? View.VISIBLE
			// : View.INVISIBLE);
			return true;
		}
	}
	public static void saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			MLToastUtils.showMessage(context, "保存成功");
		//	MLToastUtils.showMessage(context,"保存成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
	/*	try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
			MLToastUtils.showMessage(context,"保存成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/

		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
		//context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));
	}
	
}
