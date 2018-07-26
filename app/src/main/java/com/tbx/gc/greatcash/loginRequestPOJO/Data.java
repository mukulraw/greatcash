package com.tbx.gc.greatcash.loginRequestPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("firbaseId")
    @Expose
    private String firbaseId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFirbaseId() {
        return firbaseId;
    }

    public void setFirbaseId(String firbaseId) {
        this.firbaseId = firbaseId;
    }
}
