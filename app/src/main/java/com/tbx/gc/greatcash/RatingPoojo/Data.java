
package com.tbx.gc.greatcash.RatingPoojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable
{

    @SerializedName("userRating")
    @Expose
    private String userRating;
    @SerializedName("appRating")
    @Expose
    private String appRating;
    private final static long serialVersionUID = 1718650302864675690L;

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

}
