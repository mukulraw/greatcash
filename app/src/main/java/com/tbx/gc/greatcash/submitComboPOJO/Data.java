package com.tbx.gc.greatcash.submitComboPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("comboId")
    @Expose
    private String comboId;
    @SerializedName("earnAmount")
    @Expose
    private String earnAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getEarnAmount() {
        return earnAmount;
    }

    public void setEarnAmount(String earnAmount) {
        this.earnAmount = earnAmount;
    }
}
