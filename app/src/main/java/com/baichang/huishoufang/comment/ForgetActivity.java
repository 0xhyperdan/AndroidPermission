package com.baichang.huishoufang.comment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;


import com.baichang.huishoufang.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bc.base.BaseAct;
import cn.ml.base.utils.ToolsUtil;

/**
 * 忘记密码
 */
public class ForgetActivity extends BaseAct {
    //手机号
    @BindView(R.id.forget_et_phone)
    EditText etPhone;
    //验证码
    @BindView(R.id.forget_et_code)
    EditText etCode;
    //验证码
    @BindView(R.id.forget_tv_code)
    TextView tvCode;

    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
    }

    //获取验证码
    @OnClick(R.id.forget_tv_code)
    void getForgetCode() {
        String phone = etPhone.getText().toString();
        if (ToolsUtil.checkNull(getAty(), phone, R.string.please_input_account)) return;
        if (ToolsUtil.checkPhone(getAty(), phone, R.string.please_input_right_phone)) return;
        //requestFormCode(phone);
    }


    /**
     * 获取验证码
     */
//    private void requestFormCode(final String phone) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("mobile", phone);
//        map.put("isreg", "0");
//        MLHttpRequestMessage message = new MLHttpRequestMessage(
//                MLHttpType.RequestType.GET_SMS_CODE, map, Boolean.class,
//                CmService.getInstance());
//        loadData(this, null, message, new IHttpResultSuccess() {
//            @Override
//            public void success(MLHttpType.RequestType type, Object obj) {
//                showMessage(ForgetActivity.this,
//                        ToolsUtil.getResourceString(R.string.get_code_success));
//                timeCount = new TimeCount(59000, 1000);// 构造CountDownTimer对象
//                tvCode.setClickable(false);
//                timeCount.start();
//            }
//        });
//    }

    //下一步
    @OnClick(R.id.forget_btn_confirm)
    void next() {
        String code = etCode.getText().toString();
        String phone = etPhone.getText().toString();
        if (ToolsUtil.checkNull(getAty(), phone, R.string.please_input_account)) return;
        if (ToolsUtil.checkNull(getAty(), code, R.string.please_input_code)) return;
        //checkCode(code, phone);
    }

    /**
     * 判断验证码
     */
//    private void checkCode(String code, final String mobile) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("code", code);
//        map.put("mobile", mobile);
//        MLHttpRequestMessage message = new MLHttpRequestMessage(
//                MLHttpType.RequestType.CHECK_SMS_CODE, map, Boolean.class,
//                CmService.getInstance());
//        loadData(this, null, message, new IHttpResultSuccess() {
//            @Override
//            public void success(MLHttpType.RequestType type, Object obj) {
//                startAct(ForgetActivity.this, LoginActivity.class);
//                finish();
//            }
//        });
//    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tvCode.setText(ToolsUtil.getResourceString(R.string.get_code_rest));
            tvCode.setClickable(true);
            tvCode.setBackgroundColor(getResources().getColor(R.color.app_btn));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程显示
            tvCode.setBackgroundColor(getResources().getColor(R.color.get_code_bg));
            long time = millisUntilFinished / 1000;
            String strTime = String.valueOf(time) + "S";
            tvCode.setText(strTime);
        }
    }

    @Override
    protected void onDestroy() {
        timeCount = null;
        super.onDestroy();
    }
}
