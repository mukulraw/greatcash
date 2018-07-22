package com.tbx.gc.greatcash.CityPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.StatePOJO.DataState;

import java.util.List;

/**
 * Created by Priyanka on 7/10/2018.
 */

public class CityBean
{
    @SerializedName("message")
    @Expose
    private String message_city;

    @SerializedName("status")
    @Expose
    private String status_city;

    public String getMessage_city() {
        return message_city;
    }

    public void setMessage_city(String message_city) {
        this.message_city = message_city;
    }

    public String getStatus_city() {
        return status_city;
    }

    public void setStatus_city(String status_city) {
        this.status_city = status_city;
    }

    public List<DataCity> getData_city() {
        return data_city;
    }

    public void setData_city(List<DataCity> data_city) {
        this.data_city = data_city;
    }

    @SerializedName("data")
    @Expose
    private List<DataCity> data_city = null;

}
