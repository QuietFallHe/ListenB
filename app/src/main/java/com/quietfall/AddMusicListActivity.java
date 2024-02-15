package com.quietfall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quietfall.database.ListSqliteHelper;
import com.quietfall.entity.MusicList;
import com.quietfall.utils.HttpUtils;
import com.quietfall.utils.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMusicListActivity extends AppCompatActivity {
    private final String TAG = "qf";
    private WebView webview;
    private WebSettings webSettings;
    private String url;
    private String starUrl;
    private static final String ID_REGEX = "(fid=)(\\d*)(&|$)";
    private static final Pattern ID_PATTERN;
    private ListSqliteHelper mHelper;

    static {
        ID_PATTERN = Pattern.compile(ID_REGEX);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music_list);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        url = "https://space.bilibili.com/" + uid + "/favlist";
        webview = findViewById(R.id.webview);

        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setUseWideViewPort(true);//支持viewport
        // webSettings.setLoadWithOverviewMode(true);//自适应屏幕
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);//支持缩放

        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webSettings.setDomStorageEnabled(true);


        webview.loadUrl(url);


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                String thisUrl = view.getUrl();
                if (!thisUrl.equals(starUrl)) {
                    starUrl = thisUrl;
                    if (thisUrl.contains("fid")) {

                        Matcher matcher = ID_PATTERN.matcher(thisUrl);
                        if (matcher.find()) {
                            //id为收藏夹官方id
                            String id = matcher.group(2);
                            ToastUtil.show(AddMusicListActivity.this, "已选择收藏夹" + matcher.group(2));
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    String name = HttpUtils.getListNameById(id);
                                    Log.d(TAG, "AddMusicListActivity获取批量数据失败获取到收藏夹名字: " + name);
                                    long result = mHelper.insert(new MusicList(name, id));
                                    Log.d(TAG, "AddMusicListActivity插入单个收藏夹信息返回结果: " + result);
                                    mHelper.createMusicListTable(id);
                                    List<ContentValues> valuesList = null;
                                    try {
                                        valuesList = HttpUtils.getColumnById(id, HttpUtils.getCountById(id));
                                    } catch (IOException e) {
                                        Log.d(TAG, "AddMusicListActivity获取批量数据失败run: error");
                                    }
                                    mHelper.insertSomeMusic(valuesList, id);
                                    finish();

                                }
                            }.start();
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = ListSqliteHelper.getInstance(this);
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //    mHelper.closeLink();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 避免内存泄露，界面销毁时做如下处理
         */
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webview.clearHistory();

            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview = null;
        }
    }
}