package com.tbx.gc.greatcash.hotListPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Priyanka on 7/7/2018.
 */

public class DataHotList
{
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("userPic")
    @Expose
    private String userPic;

    @SerializedName("totalEarning")
    @Expose
    private String totalEarning;

    public String getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(String totalEarning) {
        this.totalEarning = totalEarning;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    @SerializedName("userLavel")
    @Expose
    private String userLevel;

    public String getUserIdd() {
        return userId;
    }

    public void setUserIdd(String userId) {
        this.userId = userId;
    }

    public String getNamee() {
        return name;
    }

    public void setNamee(String name) {
        this.name = name;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }







}
