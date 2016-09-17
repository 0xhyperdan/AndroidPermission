package com.baichang.huishoufang.comment;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baichang.huishoufang.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bc.base.BaseAct;
import cn.bc.retrofit.APIConstants;
import cn.ml.base.widget.sample.MLScrollWebView;

public class UserAgreementActivity extends BaseAct {
    @BindView(R.id.wv_notice_detail)
    WebView mlScrollWebView;

    private String url = APIConstants.API_WEB_VIEW + "agreement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);
        mlScrollWebView.loadUrl(url);
        //设置WebViewClient
        mlScrollWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        if (mlScrollWebView != null) {
            mlScrollWebView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mlScrollWebView != null) {
            mlScrollWebView.destroy();
        }
        super.onDestroy();
    }
}
