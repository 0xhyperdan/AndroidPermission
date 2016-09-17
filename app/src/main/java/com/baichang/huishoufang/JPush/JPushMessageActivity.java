package com.baichang.huishoufang.JPush;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.baichang.huishoufang.R;

import cn.bc.base.BaseAct;

public class JPushMessageActivity extends BaseAct {
    private WebView mWebView;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_message);
        initData(getIntent());
        initView();
    }

    private void initData(Intent intent) {
        if (intent == null) return;
        Bundle args = intent.getExtras();
        if (args != null) {
            url = args.getString("URL");
        }
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.me_message_web);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        if (mWebView != null) {
            mWebView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        url = null;
        super.onDestroy();
    }
}
