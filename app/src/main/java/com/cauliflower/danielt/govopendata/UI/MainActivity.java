package com.cauliflower.danielt.govopendata.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cauliflower.danielt.govopendata.R;
import com.cauliflower.danielt.govopendata.SyncRainfallService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, SyncRainfallService.class);
        startService(intent);

    }
}
