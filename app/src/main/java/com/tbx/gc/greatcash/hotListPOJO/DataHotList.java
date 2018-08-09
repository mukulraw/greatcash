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
    @SerializedName("userPhone")
    @Expose
    private String userPhone;
    @SerializedName("userRef")
    @Expose
    private String userRef;
    @SerializedName("totalEarning")
    @Expose
    private String totalEarning;
    @SerializedName("totalJoin")
    @Expose
    private String totalJoin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public String getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(String totalEarning) {
        this.totalEarning = totalEarning;
    }

    public String getTotalJoin() {
        return totalJoin;
    }

    public void setTotalJoin(String totalJoin) {
        this.totalJoin = totalJoin;
    }


}
