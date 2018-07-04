package com.cauliflower.danielt.govopendata.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cauliflower.danielt.govopendata.R;
import com.cauliflower.danielt.govopendata.RainfallAdapter;
import com.cauliflower.danielt.govopendata.RainfallObj.RainfallData;

import java.util.List;

public class DisplayRainfallActivity extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_rainfall);
        mContext = DisplayRainfallActivity.this;


        List<RainfallData> rainfallDataList = null;
        try {
            rainfallDataList = (List<RainfallData>) getIntent().getSerializableExtra("Rainfall_List");
            if (rainfallDataList != null) {
                RecyclerView recyclerView_rainfall = findViewById(R.id.recyclerView_rainfall);
                RainfallAdapter rainfallAdapter = new RainfallAdapter(rainfallDataList);
                recyclerView_rainfall.setAdapter(rainfallAdapter);
                recyclerView_rainfall.setLayoutManager(new LinearLayoutManager(mContext));
//                recyclerView_rainfall.setHasFixedSize(true);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }

}
