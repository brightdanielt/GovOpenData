package com.cauliflower.danielt.govopendata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cauliflower.danielt.govopendata.RainfallObj.RainfallData;

import java.util.ArrayList;
import java.util.List;

public class RainfallAdapter extends RecyclerView.Adapter<RainfallAdapter.ViewHolder> {

    private static final String TAG = RainfallAdapter.class.getSimpleName();
    private List<RainfallData> mRainfallList = new ArrayList<>();
    private Context mContext;
    private ClickItem mClickItem;

    public interface ClickItem {
        void scrollToPosition(int position);
    }

    public RainfallAdapter(Context context, ClickItem clickItem) {
        mContext = context;
        mClickItem = clickItem;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView rainfallStatus;
        TextView coordinate;
        TextView obsTime;
        TextView elev;
        /*TextView now,min_10,hour_1,hour_3,hour_6,hour_12,hour_24;*/
        TextView accumulate1, accumulate2;
        TextView town_city, locationName;
        TextView attribute;

        LinearLayout linearDetail;

        /*mRainfallList.get(position).getStationId();
        mRainfallList.get(position).getParameter().getCitySN();
        mRainfallList.get(position).getParameter().getTownSN();*/
        private ViewHolder(View itemView) {
            super(itemView);
            rainfallStatus = itemView.findViewById(R.id.item_img_status);
            coordinate = itemView.findViewById(R.id.item_coordinate);
            obsTime = itemView.findViewById(R.id.item_obsTime);
            elev = itemView.findViewById(R.id.item_elev);
            accumulate1 = itemView.findViewById(R.id.item_accumulate1);
            accumulate2 = itemView.findViewById(R.id.item_accumulate2);
            /*now = itemView.findViewById(R.id.item_now);
            min_10 = itemView.findViewById(R.id.item_min_10);
            hour_1 = itemView.findViewById(R.id.item_hour_1);
            hour_3 = itemView.findViewById(R.id.item_hour_3);
            hour_6 = itemView.findViewById(R.id.item_hour_6);
            hour_12 = itemView.findViewById(R.id.item_hour_6);
            hour_24 = itemView.findViewById(R.id.item_hour_24);*/
            town_city = itemView.findViewById(R.id.item_town_city);
            locationName = itemView.findViewById(R.id.item_locationName);
            linearDetail = itemView.findViewById(R.id.item_detail);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick");
                /*mRainfallList.get(position).getStationId();
                mRainfallList.get(position).getParameter().getCitySN();
                mRainfallList.get(position).getParameter().getTownSN();
                mRainfallList.get(position).getParameter().getAttribute();*/
                if (holder.linearDetail.getVisibility() == View.VISIBLE) {
                    holder.linearDetail.setVisibility(View.GONE);
                    Log.i(TAG, "GONE");
                    holder.town_city.setText(mRainfallList.get(position).getParameter().getCity());
                } else if (holder.linearDetail.getVisibility() == View.GONE) {
                    Log.i(TAG, "VISIBLE");
                    //Scroll to position clicked
                    mClickItem.scrollToPosition(position);
                    String coordinate = mContext.getString(R.string.coordinate) +
                            mRainfallList.get(position).getLat() + "," + mRainfallList.get(position).getLon();
                    String obsTime = mContext.getString(R.string.obsTime) +
                            mRainfallList.get(position).getTime().getObsTime();
                    String elev = mContext.getString(R.string.elev) +
                            String.valueOf(mRainfallList.get(position).getWeatherElement().getElev());
                    String accumulate1 =
                            mContext.getString(R.string.now) + mRainfallList.get(position).getWeatherElement().getNow() + "\n" +
                                    mContext.getString(R.string.min_10) + mRainfallList.get(position).getWeatherElement().getMin_10() + "\n" +
                                    mContext.getString(R.string.hour_1) + mRainfallList.get(position).getWeatherElement().getRain() + "\n" +
                                    mContext.getString(R.string.hour_3) + mRainfallList.get(position).getWeatherElement().getHour_3();
                    String accumulate2 = mContext.getString(R.string.hour_6) + mRainfallList.get(position).getWeatherElement().getHour_6() + "\n" +
                            mContext.getString(R.string.hour_12) + mRainfallList.get(position).getWeatherElement().getHour_12() + "\n" +
                            mContext.getString(R.string.hour_24) + mRainfallList.get(position).getWeatherElement().getHour_24();
                    String town_city = mRainfallList.get(position).getParameter().getTown() + "-" +
                            mRainfallList.get(position).getParameter().getCity();
                    holder.coordinate.setText(coordinate);
                    holder.obsTime.setText(obsTime);
                    holder.elev.setText(elev);
                    holder.accumulate1.setText(accumulate1);
                    holder.accumulate2.setText(accumulate2);
                    holder.town_city.setText(town_city);
                    holder.linearDetail.setVisibility(View.VISIBLE);
                }
            }
        });
        //Set visibility gone to prevent recyclerView reloading mistake
        holder.linearDetail.setVisibility(View.GONE);
        holder.locationName.setText(mRainfallList.get(position).getLocationName());
        holder.town_city.setText(mRainfallList.get(position).getParameter().getTown());
        //Set imageResource decided by rainfall status
        RainfallData.WeatherElement element = mRainfallList.get(position).getWeatherElement();
        double hour_24 = element.getHour_24();
        double rain = element.getRain();
        if (hour_24 <= 50) {
            int pic = position % 2;
            switch (pic) {
                case 0: {
                    holder.rainfallStatus.setImageResource(R.drawable.go_out_boy_1);
                    break;
                }
                case 1: {
                    holder.rainfallStatus.setImageResource(R.drawable.go_out_girl_1);
                    break;
                }
            }
        } else if (hour_24 > 50 && rain <= 15 && hour_24 <= 130) {
            holder.rainfallStatus.setImageResource(R.drawable.water_drop_0);
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
     * as well as the AsyncTask responsible for loading rainfall data is reset.
     * <p>
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
