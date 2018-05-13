package com.frfphlapp.weather_app.Service;

import com.frfphlapp.weather_app.openweathermap.OpenWeatherMapData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIService {
    @GET("weather?")
    Call<OpenWeatherMapData> getWeatherData(@Query("q") String name, @Query("appid") String key);

    @GET("weather?")
    Call<OpenWeatherMapData> getWeatherByLocation(@Query("lat") double lat, @Query("lon") double lon , @Query("appid") String key);
}

