package com.frfphlapp.weather_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.frfphlapp.weather_app.APIManager.TimezoneAPIManager;
import com.frfphlapp.weather_app.APIManager.WeatherAPIManager;
import com.frfphlapp.weather_app.openweathermap.OpenWeatherMapData;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private APIInterface mAPIInterfaceWeather;
    private APIInterface mAPIInterfaceTimezone;

    OpenWeatherMapData weatherMapData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWeatherData();
        createTimezone();
    }


    public void createWeatherData(){

        mAPIInterfaceWeather = WeatherAPIManager.getData().create(APIInterface.class);

        Call<OpenWeatherMapData> data = mAPIInterfaceWeather.getWeatherData("London","XD");
        data.enqueue(new Callback<OpenWeatherMapData>() {
            @Override
            public void onResponse(Call< OpenWeatherMapData > call, Response<OpenWeatherMapData> response) {
                weatherMapData = response.body();
                Log.d("Call", response.toString());
            }

            @Override
            public void onFailure(Call<OpenWeatherMapData> call, Throwable t) {

            }
        });

    }


    public void createTimezone(){
        mAPIInterfaceTimezone = TimezoneAPIManager.getData().create(APIInterface.class);
        Call<Timezone> data = mAPIInterfaceTimezone.getTimeZone("39.6034810,-119.6822510",1331766000,"XD");
        data.enqueue(new Callback<Timezone>() {
            @Override
            public void onResponse(Call<Timezone> call, Response<Timezone> response) {
                Log.d("Call", "onResponse: "+response.toString());
                Log.d("Call", "onResponse: "+response.body().timeZoneId);


            }

            @Override
            public void onFailure(Call<Timezone> call, Throwable t) {

            }
        });


    }
}

