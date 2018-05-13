package com.frfphlapp.weather_app.openweathermap;

import com.frfphlapp.weather_app.R;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    private Integer id;
    @SerializedName("main")
    private String main;
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String icon;

    public Integer getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public int getWeatherConditionIcon() {

        int weatherCondition = 0;
        switch (this.icon) {
            case "01d":
                weatherCondition =  R.drawable.clear_sky;
                break;
            case "01n":
                weatherCondition =  R.drawable.clear_sky_n;
                break;

            case "02d":
                weatherCondition =  R.drawable.sun;
                break;
            case "02n":
                weatherCondition =  R.drawable.few_clouds;
                break;

            case "03d":
            case "03n":
                weatherCondition =  R.drawable.sc;
                break;

            case "04d":
            case "04n":
                weatherCondition =  R.drawable.brokenclouds;
                break;

            case "09d":
            case "09n":
                weatherCondition =  R.drawable.shower_rain;
                break;

            case "10d":
            case "10n":
                weatherCondition =  R.drawable.rain;
                break;

            case "11d":
            case "11n":
                weatherCondition =  R.drawable.thunderstorm;
                break;

            case "13d":
            case "13n":
                weatherCondition =  R.drawable.snow;
                break;

            case "50d":
            case "50n":
                weatherCondition = R.drawable.fog;
                break;


        }
        return weatherCondition;
    }
}

