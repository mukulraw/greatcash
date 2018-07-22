package com.tbx.gc.greatcash.rateUsPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.hotListPOJO.DataHotList;

import java.util.List;

/**
 * Created by Priyanka on 7/7/2018.
 */

public class rateUsBean
{
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMessagee() {
        return message;
    }

    public void setMessagee(String message) {
        this.message = message;
    }

    public String getStatuss()
    {
        return status;
    }

    public void setStatuss(String status) {
        this.status = status;
    }


    public DataRateUs getData() {
        return data;
    }

    public void setData(DataRateUs data) {
        this.data = data;
    }

    @SerializedName("data")

    @Expose
    private DataRateUs data;
}
