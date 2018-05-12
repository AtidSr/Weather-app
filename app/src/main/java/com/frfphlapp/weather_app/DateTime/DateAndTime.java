package com.frfphlapp.weather_app.DateTime;

import com.google.gson.annotations.SerializedName;

public class DateAndTime {


    @SerializedName("countryCode")
    public String countryCode;
    @SerializedName("nextAbbreviation")
    public String nextAbbreviation;
    @SerializedName("timestamp")
    public Integer timestamp;
    @SerializedName("formatted")
    public String formatted;
}
