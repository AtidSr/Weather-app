package com.frfphlapp.weather_app.Service;

import com.frfphlapp.weather_app.DateTime.DateAndTime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DateTimeAPIService {
    @GET("get-time-zone?")
    Call<DateAndTime> getDate(@Query("key")String keys, @Query("format") String format, @Query("by") String by, @Query("zone") String zone);
}
