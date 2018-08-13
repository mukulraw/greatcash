package com.tbx.gc.greatcash.offerEarnRequestPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("offerId")
    @Expose
    private String offerId;
    @SerializedName("earnAmount")
    @Expose
    private String earnAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getEarnAmount() {
        return earnAmount;
    }

    public void setEarnAmount(String earnAmount) {
        this.earnAmount = earnAmount;
    }

}
