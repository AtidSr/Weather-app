package com.frfphlapp.weather_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
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
import com.frfphlapp.weather_app.Service.DateTimeAPIService;
import com.frfphlapp.weather_app.Service.TimezoneAPIService;
import com.frfphlapp.weather_app.Service.WeatherAPIService;
import com.frfphlapp.weather_app.openweathermap.OpenWeatherMapData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    private DateTimeAPIService mDateTimeAPIService;
    private TimezoneAPIService mTimezoneAPIService;
    private WeatherAPIService mWeatherAPIService;
    private FusedLocationProviderClient mClient;

    String openWeatherAPI_key = "c537deebb8d0b4747b9ffcfb580214fd";
    String googleMapApi_key = "AIzaSyCAg4wLXgj61tWvvV8u-MMvz9rNc9hFYHk";
    String timezoneAPI_Key = "IWKCFXD3O064";

    final long unixTime = System.currentTimeMillis() / 1000L;

    AutoCompleteTextView textView;
    ImageView mImageView;
    TextView mTemperatureTextView;
    TextView mDescriptionTextView;
    TextView mTimeTextView;
    TextView mHumidityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTemperatureTextView = findViewById(R.id.TemperatureTextView);
        mDescriptionTextView = findViewById(R.id.description);
        mImageView = findViewById(R.id.weatherIcon);
        mTimeTextView = findViewById(R.id.Time);
        mHumidityTextView = findViewById(R.id.humidity);

        mWeatherAPIService = WeatherAPIManager.createService(WeatherAPIService.class);
        mTimezoneAPIService = TimezoneAPIManager.createService(TimezoneAPIService.class);
        mDateTimeAPIService = DateTimeAPIManager.createService(DateTimeAPIService.class);

        textView = findViewById(R.id.autocomplete_country);

        mClient = LocationServices.getFusedLocationProviderClient(this);


        if (savedInstanceState != null) {

            mTemperatureTextView.setText(savedInstanceState.getCharSequence("Temp"));
            mDescriptionTextView.setText(savedInstanceState.getCharSequence("Description"));
            mImageView.setImageResource(savedInstanceState.getInt("Image"));
            mImageView.setTag(savedInstanceState.getInt("Image"));
            mTimeTextView.setText(savedInstanceState.getCharSequence("Time"));
            mHumidityTextView.setText(savedInstanceState.getCharSequence("humidity"));

        }else{
            getWeatherByCurrentLocation();
        }



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
                textView.setText("");
                return true;
            }
        });
    }

    public void createWeatherData(final String cityName) {

        Call<OpenWeatherMapData> data = mWeatherAPIService.getWeatherData(cityName, openWeatherAPI_key);

        data.enqueue(new Callback<OpenWeatherMapData>() {
            @Override
            public void onResponse(Call<OpenWeatherMapData> call, Response<OpenWeatherMapData> response) {

                if (response.isSuccessful()) {

                    OpenWeatherMapData weather = response.body();
                    setTitle(cityName);
                    mImageView.setImageResource(weather.weather.get(0).getWeatherConditionIcon());
                    mImageView.setTag(weather.weather.get(0).getWeatherConditionIcon());
                    mDescriptionTextView.setText(weather.weather.get(0).getDescription());
                    mTemperatureTextView.setText(weather.main.getTemp());
                    mHumidityTextView.setText("humidity: " + weather.main.getHumidity());

                    createTimezone(weather.coord.getLat(), weather.coord.getLon());

                }
            }

            @Override
            public void onFailure(Call<OpenWeatherMapData> call, Throwable t) {

                call.cancel();
            }
        });


    }

    public void createWeatherDataByLocation(double lat, double lon) {

        Call<OpenWeatherMapData> data = mWeatherAPIService.getWeatherByLocation(lat, lon, openWeatherAPI_key);

        data.enqueue(new Callback<OpenWeatherMapData>() {
            @Override
            public void onResponse(Call<OpenWeatherMapData> call, Response<OpenWeatherMapData> response) {

                if (response.isSuccessful()) {

                    OpenWeatherMapData weather = response.body();
                    setTitle(weather.name);
                    mImageView.setImageResource(weather.weather.get(0).getWeatherConditionIcon());
                    mImageView.setTag(weather.weather.get(0).getWeatherConditionIcon());
                    mDescriptionTextView.setText(weather.weather.get(0).getDescription());
                    mTemperatureTextView.setText(weather.main.getTemp());
                    mHumidityTextView.setText("humidity: " + weather.main.getHumidity());

                    createTimezone(weather.coord.getLat(), weather.coord.getLon());

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
        Call<Timezone> call = mTimezoneAPIService.getTimeZone(location, unixTime, googleMapApi_key);
        call.enqueue(new Callback<Timezone>() {
            @Override
            public void onResponse(Call<Timezone> call, Response<Timezone> response) {
                if (response.isSuccessful()) {

                    createDate(response.body().timeZoneId);
                }

            }

            @Override
            public void onFailure(Call<Timezone> call, Throwable t) {
                call.cancel();
            }
        });


    }

    public void createDate(String zone) {
        Call<DateAndTime> call = mDateTimeAPIService.getDate(timezoneAPI_Key, "json", "zone", zone);
        call.enqueue(new Callback<DateAndTime>() {
            @Override
            public void onResponse(Call<DateAndTime> call, Response<DateAndTime> response) {
                if (response.isSuccessful()) {
                    mTimeTextView.setText(response.body().formatted);

                }
            }

            @Override
            public void onFailure(Call<DateAndTime> call, Throwable t) {
                call.cancel();

            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putCharSequence("Temp", mTemperatureTextView.getText());
        savedInstanceState.putCharSequence("Time", mTimeTextView.getText());
        savedInstanceState.putCharSequence("Description", mDescriptionTextView.getText());
        savedInstanceState.putCharSequence("humidity", mHumidityTextView.getText());
        if(mImageView.getTag() != null)
        savedInstanceState.putInt("Image", (Integer) mImageView.getTag());


        super.onSaveInstanceState(savedInstanceState);
    }

    public void getWeatherByCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }
        mClient.getLastLocation().addOnSuccessListener(Main2Activity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location == null){
                }else {
                    createWeatherDataByLocation(location.getLatitude(), location.getLatitude());
                }
            }
        });
    }


}
