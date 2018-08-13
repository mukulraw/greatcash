package com.tbx.gc.greatcash.shoppingCountPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("affilateId")
    @Expose
    private String affilateId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAffilateId() {
        return affilateId;
    }

    public void setAffilateId(String affilateId) {
        this.affilateId = affilateId;
    }
}
