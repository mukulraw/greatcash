package com.tbx.gc.greatcash.EarnMorePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.achieversPOJO.Datum;

import java.util.List;

/**
 * Created by Priyanka on 7/9/2018.
 */

public class earnMoreBean
{
    @SerializedName("message")
    @Expose
    private String message_earn;

    @SerializedName("status")
    @Expose
    private String status_earn;

    @SerializedName("data")
    @Expose
    private List<DataEarn> data_earn = null;

    public String getMessage_earn() {
        return message_earn;
    }

    public void setMessage_earn(String message_earn) {
        this.message_earn = message_earn;
    }

    public String getStatus_earn() {
        return status_earn;
    }

    public void setStatus_earn(String status_earn) {
        this.status_earn = status_earn;
    }

    public List<DataEarn> getData_earn() {
        return data_earn;
    }

    public void setData_earn(List<DataEarn> data_earn) {
        this.data_earn = data_earn;
    }
}
