package com.frfphlapp.weather_app;

import com.frfphlapp.weather_app.DateTime.DateAndTime;
import com.frfphlapp.weather_app.openweathermap.OpenWeatherMapData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("weather?")
    Call<OpenWeatherMapData> getWeatherData(@Query("q") String name, @Query("appid") String key);
    @GET("json?")
    Call<Timezone> getTimeZone(@Query("location") String location , @Query("timestamp") long timestamp, @Query("key") String key);
    @GET("get-time-zone?")
    Call<DateAndTime> getDate(@Query("key")String keys, @Query("format") String format, @Query("by") String by, @Query("zone") String zone);
}
