package com.frfphlapp.weather_app.DateTime;

import com.google.gson.annotations.SerializedName;

public class DateAndTime {

    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("countryCode")
    public String countryCode;
    @SerializedName("countryName")
    public String countryName;
    @SerializedName("zoneName")
    public String zoneName;
    @SerializedName("abbreviation")
    public String abbreviation;
    @SerializedName("gmtOffset")
    public Integer gmtOffset;
    @SerializedName("dst")
    public String dst;
    @SerializedName("dstStart")
    public Integer dstStart;
    @SerializedName("dstEnd")
    public Integer dstEnd;
    @SerializedName("nextAbbreviation")
    public String nextAbbreviation;
    @SerializedName("timestamp")
    public Integer timestamp;
    @SerializedName("formatted")
    public String formatted;
}
