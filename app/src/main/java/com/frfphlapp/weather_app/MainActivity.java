package com.frfphlapp.weather_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

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
    AutoCompleteTextView textView;
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.weatherIcon);
        // Get a reference to the AutoCompleteTextView in the layout
        textView = findViewById(R.id.autocomplete_country);
        // Get the string array
        String[] countries = getResources().getStringArray(R.array.countries_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String cityName = v.getText().toString();
                createWeatherData(cityName);

                return true;
            }
        });
    }


    public void createWeatherData(String cityName) {


        mAPIInterface = WeatherAPIManager.getData().create(APIInterface.class);
        Call<OpenWeatherMapData> data = mAPIInterface.getWeatherData(cityName, openWeatherAPI_key);

        data.enqueue(new Callback<OpenWeatherMapData>() {
            @Override
            public void onResponse(Call<OpenWeatherMapData> call, Response<OpenWeatherMapData> response) {
                if (response.isSuccessful()) {
                    OpenWeatherMapData weather = response.body();
                    Log.d("Call", "");

                    Log.d("Call", weather.coord.getLat() + "");
                    mImageView.setImageResource(weather.weather.get(0).getWeatherConditionIcon());
                    createTimezone(weather.coord.getLat(), weather.coord.getLon());

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

