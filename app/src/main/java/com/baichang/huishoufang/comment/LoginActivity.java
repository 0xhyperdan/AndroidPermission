package com.baichang.huishoufang.comment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.baichang.huishoufang.R;
import com.baichang.huishoufang.model.UserData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bc.base.APP;
import cn.bc.base.BaseAct;
import cn.bc.utils.MLAppDiskCache;
import cn.jpush.android.api.JPushInterface;


/**
 * 登录
 */
public class LoginActivity extends BaseAct {
    //账号
    @BindView(R.id.login_et_phone)
    EditText etPhone;
    //密码
    @BindView(R.id.login_et_password)
    EditText etPsw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    //注册
    @OnClick(R.id.login_tv_register)
    void register() {
        startAct(LoginActivity.this, RegisterActivity.class);
    }

    //忘记密码
    @OnClick(R.id.login_tv_forget)
    void forget() {
        startAct(LoginActivity.this, ForgetActivity.class);
    }

    //登录
    @OnClick(R.id.login_btn_login)
    void login() {
//        String phone = etPhone.getText().toString();
//        String password = etPsw.getText().toString();
//        if (ToolsUtil.checkNull(getAty(), phone, R.string.please_input_account)) return;
//        if (ToolsUtil.checkNull(getAty(), password, R.string.please_input_password)) return;
//        if (ToolsUtil.checkPhone(getAty(), phone, R.string.please_input_right_phone)) return;
        //requestLogin(phone, password);
        startAct(this, HomeMainActivity.class);
        finish();
    }

    @OnClick(R.id.login_iv_gesture)
    void getsutre() {
        startAct(this, GestureLockActivity.class);
        finish();
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     */
//    private void requestLogin(String account, final String password) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("mobile", account);
//        map.put("pwd", password);
//        MLHttpRequestMessage message = new MLHttpRequestMessage(
//                MLHttpType.RequestType.LOGIN, map, UserData.class,
//                CmService.getInstance());
//        loadData(this, "", message, new IHttpResultSuccess() {
//            @Override
//            public void success(MLHttpType.RequestType type, Object obj) {
//                UserData userData = (UserData) obj;
//                userData.password = password;
//                MLAppDiskCache.setUser(userData);
//                APP.setToken(userData.token);
//                APP.setMkid(userData.kid);
//                showMessage(LoginActivity.this, "登录成功~^_^");
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
//                JPushInterface.setAliasAndTags(getAty(), userData.mobile, set, null);
//                startAct(LoginActivity.this, HomeMainActivity.class);
//                finish();
//            }
//        });
//    }
}
