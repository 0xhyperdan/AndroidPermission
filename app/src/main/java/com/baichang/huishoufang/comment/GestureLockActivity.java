package com.baichang.huishoufang.comment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baichang.huishoufang.R;
import com.baichang.huishoufang.dialog.ResetGestureDialog;
import com.syd.oden.gesturelock.view.GestureLockViewGroup;
import com.syd.oden.gesturelock.view.listener.GestureEventListener;
import com.syd.oden.gesturelock.view.listener.GesturePasswordSettingListener;
import com.syd.oden.gesturelock.view.listener.GestureUnmatchedExceedListener;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bc.base.BaseAct;
import cn.bc.utils.PermissionUtils;
import cn.ml.base.utils.photo.MLPhotoUtil;

public class GestureLockActivity extends BaseAct {
    @BindView(R.id.gesture_lock)
    GestureLockViewGroup mGestureLockViewGroup;
    @BindView(R.id.gesture_tv_state)
    TextView tvState;
    private ObjectAnimator animator;
    private AnimatorSet animatorSet;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);
        ButterKnife.bind(this);
        initAnimation();
        initGestureLock();
    }

    @OnClick(R.id.gesture_btn_forget)
    void forget() {
        PermissionUtils permission = PermissionUtils.getInstance(GestureLockActivity.this);
        //6.0权限拍照测试
        ResetGestureDialog dialog = new ResetGestureDialog();
        dialog.setResetDialogListener(new ResetGestureDialog.ResetDialogListener() {
            @Override
            public void photo() {
                permission.setPermission(PermissionUtils.CAMERA, grant -> {
                    if (grant) {
                        MLPhotoUtil.choose(GestureLockActivity.this, 1);
                    } else {
                        showMessage(GestureLockActivity.this, "没有相机权限");
                    }
                });
            }

            @Override
            public void image() {
                permission.setPermission(PermissionUtils.READ, grant -> {
                    if (grant) {
                        MLPhotoUtil.choose(GestureLockActivity.this, 0);
                    } else {
                        showMessage(GestureLockActivity.this, "没有相册权限");
                    }
                });
            }

            @Override
            public void call() {
                permission.setPermission(PermissionUtils.CALL, grant -> {
                    if (grant) {
                        Intent intent = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:" + "10086"));
                        startActivity(intent);
                    } else {
                        showMessage(GestureLockActivity.this, "没有拨打电话权限");
                    }
                });
            }

        });
        dialog.show(getSupportFragmentManager(), "test");
    }


    private void initAnimation() {
        final float curTranslationX = tvState.getTranslationX();
        animator = ObjectAnimator.ofFloat(tvState, "translationX", curTranslationX, -200f, +200f, curTranslationX);
        animatorSet = new AnimatorSet();
        animatorSet.play(animator);
        animatorSet.setDuration(100);
    }

    private void initGestureLock() {
        gestureEventListener();
        gesturePasswordSettingListener();
        gestureRetryLimitListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            //相册
            MLPhotoUtil.photoZoom(data.getData());
            MLPhotoUtil.cleanActivity();
        } else if (requestCode == 101) {
            //拍照
            MLPhotoUtil.photoZoom(null);
            MLPhotoUtil.cleanActivity();
        } else if (requestCode == 102 && resultCode != 0) {
            Log.d("CID", "Photo:" + MLPhotoUtil.getPhotoPath());
        }
    }

    private void gestureEventListener() {
        mGestureLockViewGroup.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                //Log.d("CID", "onGestureEvent matched: " + matched);
                if (!matched) {
                    tvState.setTextColor(Color.RED);
                    tvState.setText(R.string.gesture_password_false);
                    animatorSet.start();
                } else {
                    tvState.setTextColor(Color.BLACK);
                    tvState.setText(R.string.gesture_password_true);
                    mGestureLockViewGroup.resetView();
                    startAct(GestureLockActivity.this, HomeMainActivity.class);
                    finish();
                }
            }
        });
    }

    private void gesturePasswordSettingListener() {
        mGestureLockViewGroup.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {
                //Log.d("CID", "onFirstInputComplete");
                if (len > 3) {
                    tvState.setTextColor(Color.BLACK);
                    tvState.setText(R.string.draw_again);
                    return true;
                } else {
                    tvState.setTextColor(Color.RED);
                    tvState.setText(R.string.least_count);
                    animatorSet.start();
                    return false;
                }
            }

            @Override
            public void onSuccess() {
                //Log.d("CID", "onSuccess");
                tvState.setTextColor(Color.BLACK);
                Toast.makeText(GestureLockActivity.this, R.string.set_password_success,
                        Toast.LENGTH_SHORT).show();
                tvState.setText(R.string.please_input_gesture);
            }

            @Override
            public void onFail() {
                //Log.d("CID", "onFail");
                tvState.setTextColor(Color.RED);
                tvState.setText(R.string.draw_fail);
                animatorSet.start();
                mGestureLockViewGroup.resetView();
            }
        });
    }

    private void gestureRetryLimitListener() {
        mGestureLockViewGroup.setGestureUnmatchedExceedListener(5, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                tvState.setTextColor(Color.RED);
                //tvState.setText("错误次数过多，请稍后再试!");
                timeCount = new TimeCount(59000, 1000);
                timeCount.start();
            }
        });
    }

    private void setGestureWhenNoSet() {
        if (!mGestureLockViewGroup.isSetPassword()) {
            //Log.d("CID", "未设置密码，开始设置密码");
            tvState.setTextColor(Color.BLACK);
            tvState.setText(R.string.draw_gesture);
        }
    }

    private void resetGesturePattern() {
        mGestureLockViewGroup.removePassword();
        setGestureWhenNoSet();
        mGestureLockViewGroup.resetView();
        Toast.makeText(this, R.string.clean_success, Toast.LENGTH_SHORT).show();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String time = String.valueOf(millisUntilFinished / 1000);
            String str = getResources().getString(R.string.gesture_error_times_out) + time;
            tvState.setText(str);
            mGestureLockViewGroup.resetView();
        }

        @Override
        public void onFinish() {
            tvState.setTextColor(Color.BLACK);
            tvState.setText(R.string.please_input_gesture);
            initGestureLock();
        }

    }

    @Override
    protected void onDestroy() {
        if (timeCount != null) {
            timeCount.onFinish();
        }
        timeCount = null;
        super.onDestroy();
    }
}
