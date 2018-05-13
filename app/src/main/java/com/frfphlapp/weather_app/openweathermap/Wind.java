package com.frfphlapp.weather_app.openweathermap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private Double speed;
    @SerializedName("deg")
    private Integer deg;

    public Double getSpeed() {
        return speed;
    }
}
