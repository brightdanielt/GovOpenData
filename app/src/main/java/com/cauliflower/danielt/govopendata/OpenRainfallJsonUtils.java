package com.cauliflower.danielt.govopendata;

import android.util.Log;

import com.cauliflower.danielt.govopendata.RainfallObj.RainfallData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Functions to handle OpenWaterfallMap JSON data
 */
public class OpenRainfallJsonUtils {
    private static final String TAG = OpenRainfallJsonUtils.class.getSimpleName();

    private static final String OWM_SUCCESS = "success";
    private static final String OWM_SUCCESS_VALUE_TRUE = "true";

    private static final String OWM_RESULT = "result";
    private static final String OWM_RECORDS = "records";
    private static final String OWM_LOCATION = "location";

    //緯度
    public static final String OWM_LAT = "lat";
    //經度
    public static final String OWM_LON = "lon";
    //測站名稱
    public static final String OWM_LOCATION_NAME = "locationName";
    //測站ID
    public static final String OWM_STATION_ID = "stationID";
    //觀測資料時間
    private static final String OWM_TIME = "time";
    private static final String OWM_OBS_TIME = "obsTime";

    private static final String OWM_WEATHER_ELEMENT = "weatherElement";
    private static final String OWM_ELEMENT_NAME = "elementName";
    private static final String OWM_ELEMENT_VALUE = "elementValue";

    //小時累積雨量
    private static final String OWM_ELEMENT_NAME_RAIN = "RAIN";
    //10分鐘累積雨量
    private static final String OWM_ELEMENT_NAME_MIN_10 = "MIN_10";
    //3小時累積雨量
    private static final String OWM_ELEMENT_NAME_HOUR_3 = "HOUR_3";
    //6小時累積雨量
    private static final String OWM_ELEMENT_NAME_HOUR_6 = "HOUR_6";
    //12小時累積雨量
    private static final String OWM_ELEMENT_NAME_HOUR_12 = "HOUR_12";
    //24小時累積雨量
    private static final String OWM_ELEMENT_NAME_HOUR_24 = "HOUR_24";
    //日累積雨量
    private static final String OWM_ELEMENT_NAME_NOW = "NOW";

    private static final String OWM_PARAMETER = "parameter";
    private static final String OWM_PARAMETER_NAME = "parameterName";
    private static final String OWM_PARAMETER_VALUE = "parameterValue";

    //縣市
    public static final String OWM_PARAMETER_NAME_CITY = "CITY";
    //縣市編號
    public static final String OWM_PARAMETER_NAME_CITY_SN = "CITY_SN";
    //鄉鎮
    public static final String OWM_PARAMETER_NAME_TOWN = "TOWN";
    //鄉鎮編號
    public static final String OWM_PARAMETER_NAME_TOWN_SN = "TOWN_SN";
    //自動站屬性
    public static final String OWM_PARAMETER_NAME_ATTRIBUTE = "ATTRIBUTE";


    /**
     * Convert the string to ContentValues
     */
    public static List<RainfallData> getRainfallContentValuesFromJson(String rainfallJsonStr) {
        if (rainfallJsonStr == null) {
            return null;
        }
        try {
            JSONObject object = new JSONObject(rainfallJsonStr);
            if (object.has(OWM_SUCCESS)) {
                String success = object.getString(OWM_SUCCESS);
                switch (success) {
                    case OWM_SUCCESS_VALUE_TRUE: {
                        //result contains the type of data
                        JSONObject result = object.getJSONObject(OWM_RESULT);

                        JSONObject records = object.getJSONObject(OWM_RECORDS);
                        JSONArray location = records.getJSONArray(OWM_LOCATION);

                        List<RainfallData> locationList = new ArrayList<>();

                        for (int i = 0; i <= location.length() - 1; i++) {
                            //Init anything in a RainfallData
                            RainfallData rainfallData = new RainfallData();
                            RainfallData.Time time = new RainfallData.Time();
                            RainfallData.WeatherElement weatherElement = new RainfallData.WeatherElement();
                            RainfallData.Parameter parameter = new RainfallData.Parameter();

                            JSONObject locationObject = location.getJSONObject(i);

                            double lat = locationObject.getDouble(OWM_LAT);
                            double lon = locationObject.getDouble(OWM_LON);
                            String locationName = locationObject.getString(OWM_LOCATION_NAME);
                            String stationId = locationObject.getString(OWM_STATION_ID);
                            rainfallData.setLat(lat);
                            rainfallData.setLon(lon);
                            rainfallData.setLocationName(locationName);
                            rainfallData.setStationId(stationId);

                            JSONObject timeObject = locationObject.getJSONObject(OWM_TIME);
                            String obsTime = timeObject.getString(OWM_OBS_TIME);
                            time.setObsTime(obsTime);

                            JSONArray weatherElementArray = locationObject.getJSONArray(OWM_WEATHER_ELEMENT);
                            for (int j = 0; j <= weatherElementArray.length(); j++) {
                                JSONObject weatherElementObject = weatherElementArray.getJSONObject(j);
                                switch (weatherElementObject.getString(OWM_ELEMENT_NAME)) {
                                    case OWM_ELEMENT_NAME_RAIN: {
                                        double rain = weatherElementObject.getDouble(OWM_ELEMENT_VALUE);
                                        weatherElement.setRain(rain);
                                        break;
                                    }
                                    case OWM_ELEMENT_NAME_MIN_10: {
                                        double min_10 = weatherElementObject.getDouble(OWM_ELEMENT_VALUE);
                                        weatherElement.setMin_10(min_10);
                                        break;
                                    }
                                    case OWM_ELEMENT_NAME_HOUR_3: {
                                        double min_10 = weatherElementObject.getDouble(OWM_ELEMENT_VALUE);
                                        weatherElement.setHour_3(min_10);
                                        break;
                                    }
                                    case OWM_ELEMENT_NAME_HOUR_6: {
                                        double min_10 = weatherElementObject.getDouble(OWM_ELEMENT_VALUE);
                                        weatherElement.setHour_6(min_10);
                                        break;
                                    }
                                    case OWM_ELEMENT_NAME_HOUR_12: {
                                        double hour_12 = weatherElementObject.getDouble(OWM_ELEMENT_VALUE);
                                        weatherElement.setHour_12(hour_12);
                                        break;
                                    }
                                    case OWM_ELEMENT_NAME_HOUR_24: {
                                        double hour_24 = weatherElementObject.getDouble(OWM_ELEMENT_VALUE);
                                        weatherElement.setHour_24(hour_24);
                                        break;
                                    }
                                    case OWM_ELEMENT_NAME_NOW: {
                                        double now = weatherElementObject.getDouble(OWM_ELEMENT_VALUE);
                                        weatherElement.setRain(now);
                                        break;
                                    }
                                    default: {
                                        Log.i(TAG, "No element in weatherElement jsonArray");
                                    }
                                }
                            }
                            rainfallData.setWeatherElement(weatherElement);

                            JSONArray parameterArray = locationObject.getJSONArray(OWM_PARAMETER);
                            for (int j = 0; j <= parameterArray.length(); j++) {
                                JSONObject parameterObject = parameterArray.getJSONObject(j);
                                switch (parameterObject.getString(OWM_PARAMETER_NAME)) {
                                    case OWM_PARAMETER_NAME_CITY: {
                                        String city = parameterObject.getString(OWM_PARAMETER_VALUE);
                                        parameter.setCity(city);
                                        break;
                                    }
                                    case OWM_PARAMETER_NAME_CITY_SN: {
                                        int citySn = parameterObject.getInt(OWM_PARAMETER_VALUE);
                                        parameter.setCitySN(citySn);
                                        break;
                                    }
                                    case OWM_PARAMETER_NAME_TOWN: {
                                        String town = parameterObject.getString(OWM_PARAMETER_VALUE);
                                        parameter.setTown(town);
                                        break;
                                    }
                                    case OWM_PARAMETER_NAME_TOWN_SN: {
                                        int townSn = parameterObject.getInt(OWM_PARAMETER_VALUE);
                                        parameter.setTownSN(townSn);
                                        break;
                                    }
                                    case OWM_PARAMETER_NAME_ATTRIBUTE: {
                                        String attribute = parameterObject.getString(OWM_PARAMETER_VALUE);
                                        parameter.setAttribute(attribute);
                                        break;
                                    }
                                    default: {
                                        Log.i(TAG, "No parameter in parameter jsonArray");
                                    }
                                }
                            }
                            rainfallData.setParameter(parameter);
                            locationList.add(rainfallData);
                        }
                        return locationList;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
