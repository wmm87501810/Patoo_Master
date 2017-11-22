package com.shishiTec.HiMaster.UI.Activity.master;

import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.WebViewActivity;
import com.shishiTec.HiMaster.base.BaseActivity;

public class MasterHomePageActivity extends BaseActivity {
    private WebView master_home_pager_web;

    @Override
    protected void initViews() {
        master_home_pager_web = (WebView)findViewById(R.id.master_home_pager_web);
        WebSettings webSettings = master_home_pager_web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        //webSettings.setUseWideViewPort(true);
        master_home_pager_web.setWebViewClient(mWebViewClientBase);
        master_home_pager_web.setWebChromeClient(mWebChromeClientBase);
        master_home_pager_web.loadUrl("http://kaifa.gomaster.cn/attms/daren/daren_zhuye.html");
        master_home_pager_web.onResume();
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void onPause() {
        super.onPause();
        master_home_pager_web.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        master_home_pager_web.destroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_master_home_page;
    }

    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                                           boolean isReload) {
            // TODO Auto-generated method stub
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    private WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase();

    private class WebChromeClientBase extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            activity.setProgress(newProgress * 1000);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // TODO Auto-generated method stub
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                                           boolean precomposed) {
            // TODO Auto-generated method stub
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            // TODO Auto-generated method stub
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

    }
}
