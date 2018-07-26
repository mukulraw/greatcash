package com.tbx.gc.greatcash.CountryPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.EarnMorePOJO.DataEarn;

import java.util.List;

/**
 * Created by Priyanka on 7/9/2018.
 */

public class DataCountry
{
    @SerializedName("countryId")
    @Expose
    private String country_id;

    @SerializedName("countryName")
    @Expose
    private String countryName;

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
