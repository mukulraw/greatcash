package com.tbx.gc.greatcash.acPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userPic")
    @Expose
    private String userPic;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("refId")
    @Expose
    private String refId;
    @SerializedName("rank")
    @Expose
    private String rank;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
