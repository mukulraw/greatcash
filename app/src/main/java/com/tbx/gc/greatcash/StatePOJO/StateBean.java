package com.tbx.gc.greatcash.StatePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.EarnMorePOJO.DataEarn;

import java.util.List;

/**
 * Created by Priyanka on 7/10/2018.
 */

public class StateBean
{
    @SerializedName("message")
    @Expose
    private String message_state;

    public String getMessage_state() {
        return message_state;
    }

    public void setMessage_state(String message_state) {
        this.message_state = message_state;
    }

    public String getStatus_state() {
        return status_state;
    }

    public void setStatus_state(String status_state) {
        this.status_state = status_state;
    }

    public List<DataState> getData_state() {
        return data_state;
    }

    public void setData_state(List<DataState> data_state) {
        this.data_state = data_state;
    }

    @SerializedName("status")
    @Expose
    private String status_state;

    @SerializedName("data")
    @Expose
    private List<DataState> data_state = null;
}
