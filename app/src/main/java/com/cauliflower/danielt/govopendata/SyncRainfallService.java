package com.cauliflower.danielt.govopendata;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class SyncRainfallService extends IntentService {

    private static final String TAG = SyncRainfallService.class.getSimpleName();

    public SyncRainfallService() {
        super("SyncRainfallService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        URL url = NetworkUtils.getRainfallUrl();
        try {
            String response = NetworkUtils.getHttpResponseFromUrl(url);
            Log.i(TAG, response);
            OpenRainfallJsonUtils.getRainfallContentValuesFromJson(response);
//            String json = NetworkUtils.getJson(SyncRainfallService.this);
//            Log.i(TAG, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
