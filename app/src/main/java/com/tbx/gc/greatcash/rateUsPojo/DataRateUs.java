package com.tbx.gc.greatcash.rateUsPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Priyanka on 7/7/2018.
 */

public class DataRateUs
{
    @SerializedName("userRating")
    @Expose
    private String userRating;

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getAppRating() {
        return appRating;
    }

    public void setAppRating(String appRating) {
        this.appRating = appRating;
    }

    @SerializedName("appRating")
    @Expose
    private String appRating;


}
