package com.tbx.gc.greatcash.achieversPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum
{

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("userPic")
    @Expose
    private String userPic;

    @SerializedName("amount")
    @Expose
    private String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
