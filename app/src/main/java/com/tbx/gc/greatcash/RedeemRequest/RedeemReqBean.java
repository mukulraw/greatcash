package com.tbx.gc.greatcash.RedeemRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;

public class RedeemReqBean
{
    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("data")
    @Expose
    private DataRedReq data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataRedReq getData() {
        return data;
    }

    public void setData(DataRedReq data) {
        this.data = data;
    }
}
