package com.cauliflower.danielt.govopendata.RainfallObj;

/**
 * A RainfallData contains all information of rainfall in somewhere.
 * LocationName plays the rule of key value like database so there are
 * different locations for every Rainfall data.
 */
public class RainfallData {
    //緯度
    private double lat;
    //經度
    private double lon;
    //測站名稱
    private String locationName;
    //測站ID
    private String stationId;

//    private String elementName;
//    private double elementValue;

    //    private String parameterName;
//    private String parameterValue;
    private Time time;
    private WeatherElement weatherElement;
    private Parameter parameter;

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }


//    public void setElementName(String elementName) {
//        this.elementName = elementName;
//    }
//
//    public void setElementValue(double elementValue) {
//        this.elementValue = elementValue;
//    }
//
//    public void setParameterName(String parameterName) {
//        this.parameterName = parameterName;
//    }
//
//    public void setParameterValue(String parameterValue) {
//        this.parameterValue = parameterValue;
//    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setWeatherElement(WeatherElement weatherElement) {
        this.weatherElement = weatherElement;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getStationId() {
        return stationId;
    }

//    public String getElementName() {
//        return elementName;
//    }
//
//    public double getElementValue() {
//        return elementValue;
//    }
//
//    public String getParameterName() {
//        return parameterName;
//    }
//
//    public String getParameterValue() {
//        return parameterValue;
//    }

    public Time getTime() {
        return time;
    }

    public WeatherElement getWeatherElement() {
        return weatherElement;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public static class Time {
        private String obsTime;

        public void setObsTime(String obsTime) {
            this.obsTime = obsTime;
        }

        public String getObsTime() {
            return obsTime;
        }
    }

    public static class WeatherElement {
        //小時累積雨量
        private double rain;
        //10分鐘累積雨量
        private double min_10;
        //3小時累積雨量
        private double hour_3;
        //6小時累積雨量
        private double hour_6;
        //12小時累積雨量
        private double hour_12;
        //24小時累積雨量
        private double hour_24;
        //日累積雨量
        private double now;

        public void setRain(double rain) {
            this.rain = rain;
        }

        public void setMin_10(double min_10) {
            this.min_10 = min_10;
        }

        public void setHour_3(double hour_3) {
            this.hour_3 = hour_3;
        }

        public void setHour_6(double hour_6) {
            this.hour_6 = hour_6;
        }

        public void setHour_12(double hour_12) {
            this.hour_12 = hour_12;
        }

        public void setHour_24(double hour_24) {
            this.hour_24 = hour_24;
        }

        public void setNow(double now) {
            this.now = now;
        }

        public double getRain() {
            return rain;
        }

        public double getMin_10() {
            return min_10;
        }

        public double getHour_3() {
            return hour_3;
        }

        public double getHour_6() {
            return hour_6;
        }

        public double getHour_12() {
            return hour_12;
        }

        public double getHour_24() {
            return hour_24;
        }

        public double getNow() {
            return now;
        }
    }

    public static class Parameter {
        private String city;
        private int citySN;
        private String town;
        private int townSN;
        private String attribute;

        public void setCity(String city) {
            this.city = city;
        }

        public void setCitySN(int citySN) {
            this.citySN = citySN;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public void setTownSN(int townSN) {
            this.townSN = townSN;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getCity() {
            return city;
        }

        public int getCitySN() {
            return citySN;
        }

        public String getTown() {
            return town;
        }

        public int getTownSN() {
            return townSN;
        }

        public String getAttribute() {
            return attribute;
        }
    }

}
