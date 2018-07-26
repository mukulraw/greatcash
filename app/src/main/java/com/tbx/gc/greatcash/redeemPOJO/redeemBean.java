package com.tbx.gc.greatcash.redeemPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class redeemBean {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getMessage_redeem() {
        return message;
    }

    public void setMessage_redeem(String message) {
        this.message = message;
    }

    public String getStatus_redeem() {
        return status;
    }

    public void setStatus_redeem(String status) {
        this.status = status;
    }

    public List<Datum> getData_redeem() {
        return data;
    }

    public void setData_redeem(List<Datum> data) {
        this.data = data;
    }

}
