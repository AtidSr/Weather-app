package com.frfphlapp.weather_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.frfphlapp.weather_app.APIManager.DateTimeAPIManager;
import com.frfphlapp.weather_app.APIManager.TimezoneAPIManager;
import com.frfphlapp.weather_app.APIManager.WeatherAPIManager;
import com.frfphlapp.weather_app.DateTime.DateAndTime;
import com.frfphlapp.weather_app.openweathermap.OpenWeatherMapData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private APIInterface mAPIInterface;
    String openWeatherAPI_key = "Enter key here";
    String googleMapApi_key = "Enter key here";
    String timezoneAPI_Key = "Enter key here";
    final long unixTime = System.currentTimeMillis() / 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWeatherData();
    }


    public void createWeatherData() {


        mAPIInterface = WeatherAPIManager.getData().create(APIInterface.class);
        Call<OpenWeatherMapData> data = mAPIInterface.getWeatherData("London", openWeatherAPI_key);

        data.enqueue(new Callback<OpenWeatherMapData>() {
            @Override
            public void onResponse(Call<OpenWeatherMapData> call, Response<OpenWeatherMapData> response) {
                if (response.isSuccessful()) {
                    OpenWeatherMapData weather = response.body();
                    Log.d("Call", "");

                    Log.d("Call", response.body().coord.getLat() + "");
                    createTimezone(response.body().coord.getLat(), response.body().coord.getLon());


                } else {
                    Log.d("Call", "not sucessful");
                }

            }

            @Override
            public void onFailure(Call<OpenWeatherMapData> call, Throwable t) {
                call.cancel();
            }
        });


    }


    public void createTimezone(Double lad, Double lon) {
        String location = lad + "," + lon;
        mAPIInterface = TimezoneAPIManager.getData().create(APIInterface.class);
        Call<Timezone> data = mAPIInterface.getTimeZone(location, unixTime, googleMapApi_key);
        data.enqueue(new Callback<Timezone>() {
            @Override
            public void onResponse(Call<Timezone> call, Response<Timezone> response) {
                if(response.isSuccessful()){

                    Log.d("Call", "onResponse: " + response.toString());
                    Log.d("Call", "onResponse: " + response.body());

                    createDate(response.body().timeZoneId);
                }

            }

            @Override
            public void onFailure(Call<Timezone> call, Throwable t) {
                call.cancel();
            }
        });

    }

    public void createDate(String zone){
        mAPIInterface  = DateTimeAPIManager.getData().create(APIInterface.class);
        Call<DateAndTime> call = mAPIInterface.getDate(timezoneAPI_Key,"json","zone",zone);
        call.enqueue(new Callback<DateAndTime>() {
            @Override
            public void onResponse(Call<DateAndTime> call, Response<DateAndTime> response) {
                if(response.isSuccessful()){
                    Log.d("Call", response.toString());
                }
            }

            @Override
            public void onFailure(Call<DateAndTime> call, Throwable t) {
                call.cancel();

            }
        });
    }



}

