package com.cauliflower.danielt.govopendata.utilities;

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

    private static final String GET_CWB_OPENDATA_REST_BASE_URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/O-A0002-001";
    /**
     * Used as key of {@link Uri.Builder#appendQueryParameter(String, String)}
     */
    private static final String AUTHORIZATION_KEY = "Authorization";
    /**
     * The authorization code is applied from http://opendata.cwb.gov.tw/usages
     * <p>
     * Used as value of {@link Uri.Builder#appendQueryParameter(String, String)}
     */
    private static final String AUTHORIZATION_VALUE = "CWB-B45459B7-404B-4C83-A4D5-79F975CBC9D7";
    /**
     * The format we want http response
     * <p>
     * Used as key of {@link Uri.Builder#appendQueryParameter(String, String)}
     */
    private static final String FORMAT_KEY = "format";
    /**
     * Ask http response in JSON format.
     * We only handle JSON format in GovOpenData.
     */
    private static final String FORMAT_VALUE_JSON = "json";
    /**
     * Ask http response in XML format.
     * We do'nt handle XML in GovOpenData.
     */
    private static final String FORMAT_VALUE_XML = "xml";

    /**
     * Limit the amount of locations returned.
     * The locations is a JSONObject
     */
    private static final String LIMIT_KEY = "limit";

    private static final String ELEMENT_NAME_KEY = "elementName";

    private static final String ELEMENT_VALUE_KEY = "elementValue";

    private static final String LAT_KEY = "lat";
    //經度
    private static final String LON_KEY = "lon";
    //測站名稱
    private static final String LOCATION_NAME_KEY = "locationName";
    //測站ID
    private static final String STATION_ID_KEY = "stationID";
    //觀測資料時間
    private static final String OBS_TIME_KEY = "obsTime";

    /**
     * Height of the location
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_ELEV = "ELEV";

    /**
     * Height of the location
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_RAIN = "RAIN";

    /**
     * Accumulated rainfall in ten minutes.
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_MIN_10 = "MIN_10";
    /**
     * Accumulated rainfall in 3 hours.
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_HOUR_3 = "HOUR_3";

    /**
     * Accumulated rainfall in 6 hours.
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_HOUR_6 = "HOUR_6";

    /**
     * Accumulated rainfall in 12 hours.
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_HOUR_12 = "HOUR_12";

    /**
     * Accumulated rainfall in 24 hours.
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_HOUR_24 = "HOUR_24";

    /**
     * Accumulated rainfall today.
     * Possible value for ELEMENT_NAME_KEY
     */
    private static final String ELEMENT_VALUE_NOW = "NOW";

    /**
     * For example:
     * https://opendata.cwb.gov.tw/api/v1/rest/datastore/O-A0002-001?parameterValue=city,city_SN
     */
    private static final String PARAMETER_NAME_KEY = "parameterName";

    /**
     * For example:
     * https://opendata.cwb.gov.tw/api/v1/rest/datastore/O-A0002-001?parameterValue=台北市
     */
    private static final String PARAMETER_VALUE_KEY = "parameterValue";

    /**
     * Indicate which city the location locates.
     */
    private static final String PARAMETER_NAME_VALUE_CITY = "CITY";

    /**
     * Indicate serial number of the city where the location locates.
     */
    private static final String PARAMETER_NAME_VALUE_CITY_SN = "CITY_SN";

    /**
     * Indicate which town the location locates.
     */
    private static final String PARAMETER_NAME_VALUE_TOWN = "TOWN";

    /**
     * Indicate serial number of the town where the location locates.
     */
    private static final String PARAMETER_NAME_VALUE_TOWN_SN = "TOWN_SN";

    /**
     * The attribute of the automatic station
     */
    private static final String PARAMETER_NAME_KEY_ATTRIBUTE = "ATTRIBUTE";

    static public URL getRainfallUrl() {
        Uri uri = Uri.parse(GET_CWB_OPENDATA_REST_BASE_URL).buildUpon()
                .appendQueryParameter(AUTHORIZATION_KEY, AUTHORIZATION_VALUE)
                .appendQueryParameter(FORMAT_KEY, FORMAT_VALUE_JSON)
                .appendQueryParameter(ELEMENT_NAME_KEY,ELEMENT_VALUE_HOUR_24)
//                .appendQueryParameter(ELEMENT_NAME_KEY, ELEMENT_VALUE_MIN_10)

//                .appendQueryParameter(LOCATION_NAME_KEY, "福山")
                .appendQueryParameter(PARAMETER_NAME_KEY, PARAMETER_NAME_VALUE_CITY)
                .appendQueryParameter(LIMIT_KEY, "7")
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
