package com.example.bin.weatherapp;

import android.util.Log;
import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.List;

import static com.example.bin.weatherapp.weatherListFragment.location;


/**
 * @Author: Bhy
 * @Date: 2018/11/25
 */
public class weatherSpider {
    public static weatherRealTime sWeatherRealTime;
    public static weatherForecast sWeatherForecast;
    public static citys sCitys;
    private static final String TAG = "weatherSpider";

    public static weatherRealTime getRealTimeWeather(String location){
        try {
            Connection.Response response = Jsoup.connect("https://free-api.heweather.com/s6/weather/now?key=f07e1d0027a24a47ad7e47dbf62a2e7c&location="+location)
                                        .ignoreContentType(true)
                                        .execute();
            //只取result下的数据
            String json = response.body();
            //System.out.println(json);
            int index = json.indexOf("\"now\"");
            json = json.substring(index+6);
            json = json.substring(0,json.length()-3);
            //System.out.println(json);

            sWeatherRealTime = new Gson().fromJson(json, weatherRealTime.class);
            Log.i(TAG, "getRealTimeWeather: output test info");
            System.out.println(sWeatherRealTime.getCloud()+"\n"+
                    sWeatherRealTime.getFl()+"\n"+
                    sWeatherRealTime.getHum()+"\n"+
                    sWeatherRealTime.getTmp()+"\n"+
                    sWeatherRealTime.getVis()+"\n");

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sWeatherRealTime;
    }

    public static List<weatherForecast.Daily_forecast> getForecastWeather(String location){
        try{
            Connection.Response response = Jsoup.connect("https://free-api.heweather.com/s6/weather/forecast?key=f07e1d0027a24a47ad7e47dbf62a2e7c&location="+location)
                                                    .ignoreContentType(true)
                                                    .execute();
            String json = response.body();
            int index1 = json.indexOf("\"daily_forecast\"");
            json = json.substring(index1,json.length()-3);
            json = "{"+json+"}";
            //System.out.println(json);
            //使用Gson将json转换成对象。
            sWeatherForecast = new Gson().fromJson(json, weatherForecast.class);
            Log.i(TAG, "getForecastWeather: "+sWeatherForecast.getDaily_forecast().get(1).getTmp_max()+"\n"+
                    sWeatherForecast.getDaily_forecast().get(1).getDate());
        }catch (Exception e){
            e.printStackTrace();
        }
        return sWeatherForecast.getDaily_forecast();
    }

    public static List<citys.City> getCity(String name) throws Exception{
        Connection.Response response = Jsoup.connect("https://search.heweather.com/find?key=f07e1d0027a24a47ad7e47dbf62a2e7c&location="+name)
                .ignoreContentType(true)
                .execute();
        String json = response.body();
        int index1 = json.indexOf("{\"basic");
        int index2 = json.indexOf(",\"status");
        json = json.substring(index1,index2);
        json+="}";
        json = json.replaceAll("basic","city");
        System.out.println(json);
        sCitys = new Gson().fromJson(json,citys.class);
        System.out.println(sCitys.getCity().size());
        return sCitys.getCity();
    }

    public static weatherForecast.Daily_forecast getTodayForecast(String location){
        return getForecastWeather(location).get(0);
    }
}
