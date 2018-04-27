package com.frfphlapp.weather_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timezone {
    @SerializedName("status")
    public String status;
    @SerializedName("timeZoneId")
    public String timeZoneId;
    @SerializedName("timeZoneName")
    public String timeZoneName;
}
