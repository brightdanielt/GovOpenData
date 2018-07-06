package com.cauliflower.danielt.govopendata.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import static com.cauliflower.danielt.govopendata.ui.MainActivity.EXTRA_LIMIT;
import static com.cauliflower.danielt.govopendata.ui.MainActivity.EXTRA_LOCATION_NAME;

public class DisplayRainfallActivity extends AppCompatActivity {

    private static final String TAG = DisplayRainfallActivity.class.getSimpleName();
    //顯示降雨資料的元件
    private RecyclerView mRecyclerView_rainfall;
    //降雨資料清單
    private RainfallAdapter mRainfallAdapter;
    //提示使用者載入中
    private ProgressBar mProgressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_rainfall);

        makeViewWork();
        new RainfallTask().execute();
    }

    private void makeViewWork() {
        mProgressLoading = findViewById(R.id.pgBar_loadingJson);
        mRecyclerView_rainfall = findViewById(R.id.recyclerView_rainfall);
        mRainfallAdapter = new RainfallAdapter(DisplayRainfallActivity.this, new RainfallAdapter.ClickItem() {
            @Override
            public void scrollToPosition(int position) {
//                mRecyclerView_rainfall.scrollToPosition(position);
            }
        });
        mRecyclerView_rainfall.setAdapter(mRainfallAdapter);
        mRecyclerView_rainfall.setLayoutManager(new LinearLayoutManager(DisplayRainfallActivity.this));
    }

    /**
     * An asyncTask to get rainfall data throw network and set data to adapter of recyclerView.
     */
    class RainfallTask extends AsyncTask<Void, Integer, List<RainfallData>> {
        @Override
        protected void onPreExecute() {
            //Remind user loading data
            mProgressLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<RainfallData> doInBackground(Void... voids) {
            //Get extras which are filter to query from MainActivity.
            String locationName = getIntent().getStringExtra(EXTRA_LOCATION_NAME);
            String limit = getIntent().getStringExtra(EXTRA_LIMIT);
            //Get url
            URL url = NetworkUtils.getRainfallUrl(locationName, limit);
            try {
                if (url != null) {
                    //Get response
                    String response = NetworkUtils.getHttpResponseFromUrl(url);
                    Log.i(TAG, response);
                    //Get Rainfall data list
                    return OpenRainfallJsonUtils.getRainfallDataFromJson(response);
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
                //Reset rainfall data list to recyclerView
                mRainfallAdapter.swapData(data);
            }
            //The task is finished, stop reminding user loading data
            mProgressLoading.setVisibility(View.INVISIBLE);
        }
    }
}
