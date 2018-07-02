package com.cauliflower.danielt.govopendata;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cauliflower.danielt.govopendata.RainfallObj.RainfallData;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SyncRainfallService extends IntentService {

    private static final String TAG = SyncRainfallService.class.getSimpleName();

    public SyncRainfallService() {
        super("SyncRainfallService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Get URL
        URL url = NetworkUtils.getRainfallUrl();
        try {
            if (url != null) {
                //Get response
                String response = NetworkUtils.getHttpResponseFromUrl(url);
                Log.i(TAG, response);
                //Get custom data
                List<RainfallData> rainfallDataList = OpenRainfallJsonUtils.getRainfallDataFromJson(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
