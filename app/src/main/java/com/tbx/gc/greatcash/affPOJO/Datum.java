package com.tbx.gc.greatcash.affPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("affilateId")
    @Expose
    private String affilateId;
    @SerializedName("affilateUrl")
    @Expose
    private String affilateUrl;
    @SerializedName("affilateThumbnail")
    @Expose
    private String affilateThumbnail;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getAffilateId() {
        return affilateId;
    }

    public void setAffilateId(String affilateId) {
        this.affilateId = affilateId;
    }

    public String getAffilateUrl() {
        return affilateUrl;
    }

    public void setAffilateUrl(String affilateUrl) {
        this.affilateUrl = affilateUrl;
    }

    public String getAffilateThumbnail() {
        return affilateThumbnail;
    }

    public void setAffilateThumbnail(String affilateThumbnail) {
        this.affilateThumbnail = affilateThumbnail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
