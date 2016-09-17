package com.baichang.huishoufang.comment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.CheckBox;
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
 * 注册
 */
public class RegisterActivity extends BaseAct {
    //手机号
    @BindView(R.id.register_et_phone)
    EditText etPhone;
    //验证码
    @BindView(R.id.register_et_code)
    EditText etCode;
    //验证码
    @BindView(R.id.register_tv_code)
    TextView tvCode;
    //注册协议 chekbox
    //@BindView(R.id.register_ck_agreement)
    CheckBox ckAgreement;

    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    //获取验证码
    @OnClick(R.id.register_tv_code)
    void getCode() {
        String phone = etPhone.getText().toString();
        if (ToolsUtil.checkNull(getAty(), phone, R.string.please_input_account)) return;
        if (ToolsUtil.checkPhone(getAty(), phone, R.string.please_input_right_phone)) return;
    }

    //同意注册协议
//    @OnClick({R.id.register_lr_agreement, R.id.register_tv_agreement})
//    void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.register_lr_agreement:
//                ckAgreement.setChecked(!ckAgreement.isChecked());
//                break;
//            case R.id.register_tv_agreement:
//                startAct(RegisterActivity.this, UserAgreementActivity.class);
//                break;
//        }
//    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tvCode.setText(R.string.get_code_rest);
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
