package com.frfphlapp.weather_app.openweathermap;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private Double temp;
    @SerializedName("pressure")
    private Double pressure;
    @SerializedName("humidity")
    private Integer humidity;
    @SerializedName("temp_min")
    private Double tempMin;
    @SerializedName("temp_max")
    private Double tempMax;

    public String getTemp() {

        return String.format("%.2f ℃",temp-273.15);
    }

    public Double getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }
}
