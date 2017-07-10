package com.zbxn.main.activity.memberCenter;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pub.base.BaseActivity;
import com.zbxn.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewDetailsActivity extends BaseActivity {

    @BindView(R.id.web_details)
    WebView webDetails;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_details);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        setTitle(title);
        webDetails.loadUrl(url);
        WebSettings settings = webDetails.getSettings();
        settings.setJavaScriptEnabled(true);
        webDetails.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webDetails.canGoBack()) {
            webDetails.goBack();
            return true;
        }
        if (!webDetails.canGoBack()) {
            finish();
        }
        return false;
    }

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);

        }
    }
}
