package com.baichang.huishoufang.comment;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;


import com.baichang.huishoufang.R;
import com.baichang.huishoufang.client.ClientFragment;
import com.baichang.huishoufang.home.HomePageFragment;
import com.baichang.huishoufang.information.InformationFragment;
import com.baichang.huishoufang.me.MeFragment;
import com.baichang.huishoufang.model.VersionData;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import cn.bc.base.BaseAct;
import cn.bc.utils.MLCrashHandler;
import cn.ml.base.utils.MLUpdateManager;
import cn.ml.base.utils.ToolsUtil;
import rx.functions.Action1;

public class HomeMainActivity extends BaseAct {
    //定义FragmentTabHost对象
    public FragmentTabHost mTabHost;
    private Class mViewFrags[] = {
            HomePageFragment.class,
            ClientFragment.class,
            InformationFragment.class,
            MeFragment.class,
    };
    //Tab图片
    private int mImageViewArray[] = {
            R.drawable.home_tab_1,
            R.drawable.home_tab_2,
            R.drawable.home_tab_3,
            R.drawable.home_tab_4,
    };
    //Tab文字
    private String mDataTv[] = {"首页", "客户", "资讯", "我的"};
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        //initData(getIntentData());
        initCrash();
        initView();
    }

    private void initCrash() {
        //崩溃收集
        MLCrashHandler crashHandler = MLCrashHandler.getInstance();
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(grant -> {
                    if (grant) {
                        crashHandler.init(getApplicationContext());
                    } else {
                        showMessage(HomeMainActivity.this, "抱歉，没有文件写入权限，无法生成错误日志。");
                    }
                });
    }

    private void initData(Object intentData) {
        try {
            String version = ToolsUtil.getVersionCode(this);
            //checkVersion(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化组件
     */
    private void initView() {
        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //R.id.realtabcontent决定了标签是在上面还是下面显示,FrameLayout在FragmentTabHost上面，标签在上方显示，否则在下方显示。
        mTabHost.setup(this, getSupportFragmentManager(), R.id.home_fl_content);
        //得到fragment的个数
        int count = mViewFrags.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mDataTv[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mViewFrags[i], null);
        }
        //去掉了底部导航栏的间隔竖线
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_home_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);
        //TextView textView = (TextView) view.findViewById(R.id.textview);
        //textView.setText(mDataTv[index]);
        return view;
    }

    /**
     * 检查版本
     */
//    private void checkVersion(String version) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("version", version);
//        map.put("dev", "0");
//        MLHttpRequestMessage message = new MLHttpRequestMessage(
//                MLHttpType.RequestType.UP_GRADE_VERSION, map,
//                VersionData.class, CmService.getInstance());
//        loadData(this, "", message, new IHttpResultSuccess() {
//            @Override
//            public void success(MLHttpType.RequestType type, Object obj) {
//                VersionData data = (VersionData) obj;
//                MLUpdateManager manager;
//                if (TextUtils.equals(data.enforce, "1")) {
//                    manager = new MLUpdateManager(HomeMainActivity.this,
//                            data.updateurl, data.detail, true);
//                } else {
//                    manager = new MLUpdateManager(HomeMainActivity.this,
//                            data.updateurl, data.detail);
//                }
//                manager.checkUpdateInfo();
//            }
//        }, new IHttpResultError() {
//            @Override
//            public void error(MLHttpType.RequestType type, Object obj) {
//
//            }
//        });
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

            return true;
        }
        return false;
        //return super.onKeyDown(keyCode, event);
    }


}
