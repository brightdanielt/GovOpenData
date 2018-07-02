package com.cauliflower.danielt.govopendata;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private NetworkUtils() {
    }

    //key value很亂，需再整理
    private static final String GET_CWB_OPENDATA_REST_BASE_URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/O-A0002-001";
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String AUTHORIZATION_VALUE = "CWB-B45459B7-404B-4C83-A4D5-79F975CBC9D7";

    //回傳資料格式
    public static final String FORMAT_KEY = "format";
    public static final String FORMAT_VALUE_JSON = "json";
    public static final String FORMAT_VALUE_XML = "xml";

    //回傳資料筆數
    public static final String LIMIT_KEY = "limit";

    public static final String TIME_FROM_KEY = "timeFrom";
    public static final String TIME_TO_KEY = "timeTo";
    public static final String ELEMENT_NAME_KEY = "elementName";

    //緯度
    public static final String LAT_PARAM = "lat";
    //經度
    public static final String LON_PARAM = "lon";
    //測站名稱
    public static final String LOCATION_NAME_PARAM = "locationName";
    //測站ID
    public static final String STATION_ID_PARAM = "stationID";
    //觀測資料時間
    public static final String OBS_TIME_PARAM = "obsTime";
    //高度
    public static final String ELEV_PARAM = "ELEV";
    //小時累積雨量
    public static final String RAIN_PARAM = "RAIN";
    //10分鐘累積雨量
    public static final String MIN_10_PARAM = "MIN_10";
    //3小時累積雨量
    public static final String HOUR_3_PARAM = "HOUR_3";
    //6小時累積雨量
    public static final String HOUR_6_PARAM = "HOUR_6";
    //12小時累積雨量
    public static final String HOUR_12_PARAM = "HOUR_12";
    //24小時累積雨量
    public static final String HOUR_24_PARAM = "HOUR_24";
    //日累積雨量
    public static final String NOW_PARAM = "NOW";
    //縣市
    public static final String CITY_PARAM = "CITY";
    //縣市編號
    public static final String CITY_SN_PARAM = "CITY_SN";
    //鄉鎮
    public static final String TOWN_PARAM = "TOWN";
    //鄉鎮編號
    public static final String TOWN_SN_PARAM = "TOWN_SN";
    //自動站屬性
    public static final String ATTRIBUTE_PARAM = "ATTRIBUTE";

    static public URL getRainfallUrl() {
        Uri uri = Uri.parse(GET_CWB_OPENDATA_REST_BASE_URL).buildUpon()
                .appendQueryParameter(AUTHORIZATION_KEY, AUTHORIZATION_VALUE)
                .appendQueryParameter(FORMAT_KEY, FORMAT_VALUE_JSON)
                .appendQueryParameter(LOCATION_NAME_PARAM, "福山")
                .appendQueryParameter(ELEMENT_NAME_KEY, MIN_10_PARAM)
//                .appendQueryParameter(LIMIT_KEY,"1")
//                .appendQueryParameter(TIME_FROM_KEY,"2018-07-02T06:00:00")
//                .appendQueryParameter(TIME_TO_KEY,"2018-07-03T06:00:00")
                .build();
        try {
            URL url = new URL(uri.toString());
            Log.v(TAG, "URL: " + url);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public String getHttpResponseFromUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean next = scanner.hasNext();
            String response = null;
            if (next) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }
}
