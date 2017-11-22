package com.shishiTec.HiMaster.UI.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseActivity;

import butterknife.Bind;

public class WebViewActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;
    private String url;
    private ImageView left_back;
    private TextView tv_exit, tv_top_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void initViews() {
        webView = (WebView) findViewById(R.id.webView);
        left_back = (ImageView) findViewById(R.id.left_back);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        webView.loadUrl(url);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("utf-8");//设置字符编码

        //设置本地调用对象及其接口
        webView.addJavascriptInterface(new JavaScriptObject(WebViewActivity.this), "http://kaifa.gomaster.cn/rest/index.php");
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(myWebChromeClient);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:resetFontSize('20px')");
            }
        };
        webView.setWebViewClient(wvc);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goBack();
                tv_exit.setVisibility(View.VISIBLE);
            }
        });
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.this.finish();
            }
        });
    }

    /*
     * Class    ：   用于辅助WebView，处理JavaScript的对话框、网站图标、网站标题、加载进度等
     * Author   :   wangmengyan
     */
    private class MyWebChromeClient extends WebChromeClient {
        //获得网页的加载进度，显示在右上角的TextView控件中
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress < 100) {

            } else {

            }
        }

        //获得网页的标题，作为应用程序的标题进行显示
        public void onReceivedTitle(WebView view, String title) {
            tv_top_title.setText(title);
        }
    }

    public class JavaScriptObject {
        Context mContxt;

        // @JavascriptInterface //sdk17版本以上加上注解
        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }

        public void fun1FromAndroid(String name) {
            Toast.makeText(mContxt, name, Toast.LENGTH_LONG).show();
        }

        public void fun2(String name) {
            Toast.makeText(mContxt, "调用fun2:" + name, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        } else {
            WebViewActivity.this.finish();
        }
        return false;
    }
}
