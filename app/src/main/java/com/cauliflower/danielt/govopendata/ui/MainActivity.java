package com.cauliflower.danielt.govopendata.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.cauliflower.danielt.govopendata.R;
import com.cauliflower.danielt.govopendata.utilities.NetworkUtils;
/**
 * User can browse website and set filter to query rainfall data.
 * */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String GOV_DATA_URL = "https://data.gov.tw/dataset/9177";
    //Two extra send to DisplayRainfallActivity
    public static final String EXTRA_LOCATION_NAME = "locationName";
    public static final String EXTRA_LIMIT = "limit";
    //To decide locationName to query
    private EditText mEdLocationName;
    //To decide limit to query
    private SeekBar mSkBarLimit;
    //Show progress of mSkBarLimit
    private EditText edLimit;
    private WebView mWebViewRainfall;
    private ProgressBar pgBarLoading;
    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeViewWork();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(MainActivity.this).inflate(R.menu.web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //webView頁面切換
        int itemId = item.getItemId();
        if (itemId == R.id.action_goBack && mWebViewRainfall.canGoBack()) {
            mWebViewRainfall.goBack();
        } else if (itemId == R.id.action_goForward && mWebViewRainfall.canGoForward()) {
            mWebViewRainfall.goForward();
        }
        return false;
    }

    private void makeViewWork() {
        mEdLocationName = findViewById(R.id.ed_locationName);
        mSkBarLimit = findViewById(R.id.skBar_limit);
        edLimit = findViewById(R.id.ed_limit);
        mWebViewRainfall = findViewById(R.id.webView_Rainfall);
        pgBarLoading = findViewById(R.id.pgBar_loading);
        mBtnStart = findViewById(R.id.btn_start);

        //Set webView
        WebSettings webSettings = mWebViewRainfall.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);

        //在同一個 webView 開啟新頁面
        mWebViewRainfall.setWebViewClient(new WebViewClient() {
            //新頁面開啟時顯示提示載入中
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pgBarLoading.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            //新頁面完成時停止提示載入中
            @Override
            public void onPageFinished(WebView view, String url) {
                pgBarLoading.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //使用同一個 WebView 載入 WebResourceRequest,如此一來，點選其他連結時，
                // 將不會要求使用者選擇其他工具開啟連結
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
        mWebViewRainfall.loadUrl(GOV_DATA_URL);

        //Sync seekBar about limit and EditText about limit
        edLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged");
                //Update progress of seekBar
                if (s.toString().trim().equals("")) {
                    edLimit.setText(String.valueOf(NetworkUtils.LIMIT_MIN));
                    mSkBarLimit.setProgress(NetworkUtils.LIMIT_MIN);
                } else {
                    mSkBarLimit.setProgress(Integer.valueOf(s.toString()));
                }
            }
        });

        mSkBarLimit.setMax(NetworkUtils.LIMIT_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mSkBarLimit.setMin(NetworkUtils.LIMIT_MIN);
        }

        //Sync seekBar about limit and EditText about limit
        mSkBarLimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                edLimit.setText(String.valueOf(seekBar.getProgress()));
                //Set min of mSkBarLimit for app target API 25 and below
                if (seekBar.getProgress() == 0) {
                    seekBar.setProgress(NetworkUtils.LIMIT_MIN);
                }
            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationName = mEdLocationName.getText().toString().trim();
                String limit = edLimit.getText().toString();
                Intent intent = new Intent(MainActivity.this, DisplayRainfallActivity.class);
                intent.putExtra(EXTRA_LOCATION_NAME, locationName);
                intent.putExtra(EXTRA_LIMIT, limit);
                startActivity(intent);
            }
        });
    }
}
