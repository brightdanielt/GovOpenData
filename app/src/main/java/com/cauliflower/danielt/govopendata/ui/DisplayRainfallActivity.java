package com.cauliflower.danielt.govopendata.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cauliflower.danielt.govopendata.R;
import com.cauliflower.danielt.govopendata.RainfallAdapter;
import com.cauliflower.danielt.govopendata.RainfallObj.RainfallData;
import com.cauliflower.danielt.govopendata.utilities.NetworkUtils;
import com.cauliflower.danielt.govopendata.utilities.OpenRainfallJsonUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DisplayRainfallActivity extends Activity {

    private static final String TAG = DisplayRainfallActivity.class.getSimpleName();
    RecyclerView mRecyclerView_rainfall;
    private RainfallAdapter mRainfallAdapter;
    private ProgressBar mProgrsddLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_rainfall);

        mProgrsddLoading=findViewById(R.id.pgBar_loadingJson);
        mRecyclerView_rainfall = findViewById(R.id.recyclerView_rainfall);
        mRainfallAdapter = new RainfallAdapter();
        mRecyclerView_rainfall.setAdapter(mRainfallAdapter);
        mRecyclerView_rainfall.setLayoutManager(new LinearLayoutManager(DisplayRainfallActivity.this));
        //                recyclerView_rainfall.setHasFixedSize(true);

        new RainfallTask().execute();
    }

    class RainfallTask extends AsyncTask<Void, Integer, List<RainfallData>> {
        @Override
        protected void onPreExecute() {
            mProgrsddLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<RainfallData> doInBackground(Void... voids) {
            String locationName = getIntent().getStringExtra("locationName");
            String limit = getIntent().getStringExtra("limit");
            URL url = NetworkUtils.getRainfallUrl(locationName, limit);
            try {
                if (url != null) {
                    //Get response
                    String response = NetworkUtils.getHttpResponseFromUrl(url);
                    Log.i(TAG, response);
                    //Get Rainfall data list
                    List<RainfallData> rainfallDataList = OpenRainfallJsonUtils.getRainfallDataFromJson(response);
                    return rainfallDataList;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<RainfallData> data) {
            if (data != null) {
                mRainfallAdapter.swapData(data);
            }
            mProgrsddLoading.setVisibility(View.INVISIBLE);
        }
    }
}
