package com.cauliflower.danielt.govopendata.ui;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cauliflower.danielt.govopendata.R;
import com.cauliflower.danielt.govopendata.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String GOV_DATA_URL = "https://data.gov.tw/dataset/9177";
    private EditText mEd_locationName;
    private SeekBar mSkBar_limit;
    EditText ed_limit;
    private WebView mWebView_rainfall;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = new Intent(MainActivity.this, SyncRainfallService.class);
        startService(intent);*/
        makeViewWork();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(MainActivity.this).inflate(R.menu.web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_goBack && mWebView_rainfall.canGoBack()) {
            mWebView_rainfall.goBack();
        } else if (itemId == R.id.action_goForward && mWebView_rainfall.canGoForward()) {
            mWebView_rainfall.goForward();
        }
        return false;
    }

    private void makeViewWork() {
        mEd_locationName = findViewById(R.id.ed_locationName);
        mSkBar_limit = findViewById(R.id.skBar_limit);
        ed_limit = findViewById(R.id.ed_limit);
        mWebView_rainfall = findViewById(R.id.webView_Rainfall);
        btn_start = findViewById(R.id.btn_start);

        //Set webView
        WebSettings webSettings = mWebView_rainfall.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);
        mWebView_rainfall.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return false;
            }
        });
        mWebView_rainfall.canGoBack();
        mWebView_rainfall.loadUrl(GOV_DATA_URL);

        //Sync seekBar about limit and EditText about limit
        ed_limit.addTextChangedListener(new TextWatcher() {
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
                    ed_limit.setText(String.valueOf(NetworkUtils.LIMIT_MIN));
                    mSkBar_limit.setProgress(NetworkUtils.LIMIT_MIN);
                } else {
                    mSkBar_limit.setProgress(Integer.valueOf(s.toString()));
                }
            }
        });

        mSkBar_limit.setMax(NetworkUtils.LIMIT_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mSkBar_limit.setMin(NetworkUtils.LIMIT_MIN);
        }

        mSkBar_limit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ed_limit.setText(String.valueOf(seekBar.getProgress()));
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationName = mEd_locationName.getText().toString().trim();
                String limit = ed_limit.getText().toString();
                Intent intent = new Intent(MainActivity.this, DisplayRainfallActivity.class);
                intent.putExtra("locationName", locationName);
                intent.putExtra("limit", limit);
                startActivity(intent);
            }
        });
    }
}
