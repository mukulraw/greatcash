package com.tbx.gc.greatcash.hotListPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Priyanka on 7/7/2018.
 */

public class hotListBean
{
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<DataHotList> data=null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataHotList> getDataHot() {
        return data;
    }

    public void setDataHot(List<DataHotList> data) {
        this.data = data;
    }
}
