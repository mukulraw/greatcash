package com.tbx.gc.greatcash.CountryPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.EarnMorePOJO.DataEarn;

import java.util.List;

/**
 * Created by Priyanka on 7/9/2018.
 */

public class countryBean
{
    @SerializedName("message")
    @Expose
    private String message_country;

    @SerializedName("status")
    @Expose
    private String status_country;

    public List<DataCountry> getData_country() {
        return data_country;
    }

    public void setData_country(List<DataCountry> data_country) {
        this.data_country = data_country;
    }

    @SerializedName("data")
    @Expose
    private List<DataCountry> data_country = null;

    public String getMessage_country() {
        return message_country;
    }

    public void setMessage_country(String message_country) {
        this.message_country = message_country;
    }

    public String getStatus_country() {
        return status_country;
    }

    public void setStatus_country(String status_country) {
        this.status_country = status_country;
    }




}
