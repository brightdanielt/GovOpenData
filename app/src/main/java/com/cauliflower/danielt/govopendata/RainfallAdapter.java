package com.cauliflower.danielt.govopendata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cauliflower.danielt.govopendata.RainfallObj.RainfallData;

import java.util.ArrayList;
import java.util.List;

public class RainfallAdapter extends RecyclerView.Adapter<RainfallAdapter.ViewHolder> {

    private List<RainfallData> mRainfallList = new ArrayList<>();

    public RainfallAdapter() {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationName;
        TextView city;
        ImageView rainfallStatus;

        private ViewHolder(View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.item_locationName);
            city = itemView.findViewById(R.id.item_city);
            rainfallStatus = itemView.findViewById(R.id.item_img_status);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * 1. 大雨:24 小時累積雨量達 50 毫米以上，且其中至少有 1 小時雨量達 15 毫米以上之降雨現象。
     * 2. 豪雨:指 24 小時累積雨量達 130 毫米以上之降雨現象，
     * <p>若 24 小時累積雨量達 200 毫米以上者稱之為大豪雨，
     * <p>若 24 小時累積雨量達 350 毫米以上者稱之為超大豪雨。
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.locationName.setText(mRainfallList.get(position).getLocationName());
        holder.city.setText(mRainfallList.get(position).getParameter().getCity());
        RainfallData.WeatherElement element = mRainfallList.get(position).getWeatherElement();
        double hour_24 = element.getHour_24();
        double rain = element.getRain();
        if (hour_24 <= 50) {
            int pic = position % 4;
            switch (pic) {
                case 0: {
                    holder.rainfallStatus.setImageResource(R.drawable.go_out_boy1);
                    break;
                }
                case 1: {
                    holder.rainfallStatus.setImageResource(R.drawable.go_out_boy2);
                    break;
                }
                case 2: {
                    holder.rainfallStatus.setImageResource(R.drawable.go_out_boy3);
                    break;
                }
                case 3: {
                    holder.rainfallStatus.setImageResource(R.drawable.go_out_boy4);
                    break;
                }
            }
        } else if (hour_24 > 50 && rain <= 15 && hour_24 <= 130) {
            //之後改成半滴
            holder.rainfallStatus.setImageResource(R.drawable.go_out_boy2);
        } else if (hour_24 > 50 && rain > 15 && hour_24 <= 130) {
            holder.rainfallStatus.setImageResource(R.drawable.water_drop_1);
        } else if (hour_24 > 130 && hour_24 <= 200) {
            holder.rainfallStatus.setImageResource(R.drawable.water_drop_2);
        } else if (hour_24 > 200 && hour_24 <= 350) {
            holder.rainfallStatus.setImageResource(R.drawable.water_drop_3);
        } else if (hour_24 > 350) {
            holder.rainfallStatus.setImageResource(R.drawable.water_drop_4);
        }
    }

    @Override
    public int getItemCount() {
        return mRainfallList.size();
    }

    /**
     * This method is called in DisplayRainfallActivity when a load is finished,
     * as well as the Loader responsible for loading rainfall data is reset.
     *
     * When this method is called,we assume we have a completely new set of data,
     * so we can call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param data The list of rainfall data to use as RainfallAdapter's data source
     */
    public void swapData(List<RainfallData> data) {
        mRainfallList = data;
        notifyDataSetChanged();
    }
}
