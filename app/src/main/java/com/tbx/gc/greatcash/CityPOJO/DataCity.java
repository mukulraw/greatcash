package com.tbx.gc.greatcash.CityPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Priyanka on 7/10/2018.
 */

public class DataCity
{
    @SerializedName("cityId")
    @Expose
    private String cityID;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @SerializedName("cityName")
    @Expose
    private String cityName;
}
