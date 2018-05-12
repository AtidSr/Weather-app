package com.frfphlapp.weather_app.Service;

import com.frfphlapp.weather_app.Timezone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TimezoneAPIService {
    @GET("json?")
    Call<Timezone> getTimeZone(@Query("location") String location , @Query("timestamp") long timestamp, @Query("key") String key);
}
