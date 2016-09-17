package com.baichang.huishoufang.comment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.baichang.huishoufang.R;
import com.baichang.huishoufang.model.GuideData;
import com.baichang.huishoufang.model.UserData;
import com.syd.oden.gesturelock.view.GesturePreference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bc.base.BaseAct;
import cn.bc.utils.ImageLoader;


/**
 * 启动页面
 */
public class SplashActivity extends BaseAct {
    private ArrayList<String> imageList = new ArrayList<String>();
    private boolean isGesture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
    }

    private void initData() {
        GesturePreference preference = new GesturePreference(this, -1);
        String result = preference.ReadStringPreference();
        Log.d("CID", "Gesture:" + result);
        if (!TextUtils.isEmpty(result)) {
            isGesture = true;
        }
        handler.sendEmptyMessageDelayed(HOME_MAIN_ACTIVITY, 2000);
//        UserData userData = MLAppDiskCache.getUser();
//        if (userData != null) {
//            login(userData.mobile, userData.password);
//        } else {
//            MLAppDiskCache.cleanInstallFirstTime();
//            start();
//        }
    }

//    private void login(String account, final String password) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("mobile", account);
//        map.put("pwd", password);
//        MLHttpRequestMessage message = new MLHttpRequestMessage(
//                MLHttpType.RequestType.LOGIN, map, UserData.class,
//                CmService.getInstance());
//        loadData(this, null, message, new IHttpResultSuccess() {
//            @Override
//            public void success(MLHttpType.RequestType type, Object obj) {
//                UserData userData = (UserData) obj;
//                userData.password = password;
//                MLAppDiskCache.setUser(userData);
//                APP.setToken(userData.token);
//                APP.setMkid(userData.kid);
//                Set<String> set = new HashSet<String>();
//                if (!TextUtils.isEmpty(userData.village)) {
//                    set.add(userData.village);
//                }
//                if (!TextUtils.isEmpty(userData.committee)) {
//                    set.add(userData.committee);
//                }
//                if (!TextUtils.isEmpty(userData.station)) {
//                    set.add(userData.station);
//                }
//                JPushInterface.setAliasAndTags(SplashActivity.this, userData.mobile, set, null);
//                start();
//            }
//        }, new IHttpResultError() {
//            @Override
//            public void error(MLHttpType.RequestType type, Object obj) {
//                MLAppDiskCache.cleanUser();
//                MLAppDiskCache.cleanInstallFirstTime();
//                APP.clean();
//                start();
//            }
//        });
//    }

    private void start() {
        //findGuideImage();
    }

    private static final int GUIDE_ACTIVITY = 1;
    private static final int HOME_MAIN_ACTIVITY = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GUIDE_ACTIVITY) {
                Intent i = new Intent(SplashActivity.this, GuideActivity.class);
                i.putExtra("Image", imageList);
                SplashActivity.this.startActivity(i);
                SplashActivity.this.finish();
            } else if (msg.what == HOME_MAIN_ACTIVITY) {
                //startAct(SplashActivity.this, HomeMainActivity.class);
                if (isGesture) {
                    startAct(SplashActivity.this, GestureLockActivity.class);
                    SplashActivity.this.finish();
                } else {
                    startAct(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.finish();
                }
            }
        }
    };

    /**
     * 获取引导页
     */
//    private void findGuideImage() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("updated", MLAppDiskCache.getInstallFirstTime());
//        MLHttpRequestMessage message = new MLHttpRequestMessage(
//                MLHttpType.RequestType.FIND_START_PAGE, map,
//                GuideData.class, CmService.getInstance());
//        loadData(this, null, message, new IHttpResultSuccess() {
//                    @Override
//                    public void success(MLHttpType.RequestType type, Object obj) {
//                        GuideData data = (GuideData) obj;
//                        if (data == null) {
//                            handler.sendEmptyMessageDelayed(HOME_MAIN_ACTIVITY, 2000);
//                        }
//                        if (!TextUtils.isEmpty(data.updated)) {
//                            MLAppDiskCache.setInstallFirstTime(data.updated);
//                        }
//                        if (data.imgpaths != null && !data.imgpaths.isEmpty()) {
//                            new Thread(new SplashRunnable(data.imgpaths)).start();
//                        } else {
//                            handler.sendEmptyMessageDelayed(HOME_MAIN_ACTIVITY, 2000);
//                        }
//                    }
//                }, new IHttpResultError() {
//                    @Override
//                    public void error(MLHttpType.RequestType type, Object obj) {
//                        handler.sendEmptyMessageDelayed(HOME_MAIN_ACTIVITY, 2000);
//                    }
//                }
//
//        );
//    }

    public class SplashRunnable implements Runnable {
        private List<GuideData.ImagePath> list = new ArrayList<>();

        public SplashRunnable(List<GuideData.ImagePath> imagePathList) {
            list.addAll(imagePathList);
        }

        @Override
        public void run() {
            if (list.isEmpty()) return;
            int size = list.size();
            for (GuideData.ImagePath imgPath : list) {
                File file = ImageLoader.downloadImage2File(
                        getApplicationContext(), imgPath.picpath);
                if (file != null) {
                    imageList.add(file.getAbsolutePath());
                }
                if (imageList.size() == size) {
                    handler.sendEmptyMessageDelayed(GUIDE_ACTIVITY, 1000);
                }
            }
        }
    }
}
