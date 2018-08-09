package com.tbx.gc.greatcash.registerResponsePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("refId")
    @Expose
    private String refId;
    @SerializedName("parentId")
    @Expose
    private String parentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("userPic")
    @Expose
    private String userPic;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("nominee")
    @Expose
    private String nominee;
    @SerializedName("relationNominee")
    @Expose
    private String relationNominee;
    @SerializedName("joinDate")
    @Expose
    private String joinDate;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("firbaseId")
    @Expose
    private String firbaseId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("earningAmount")
    @Expose
    private String earningAmount;
    @SerializedName("uplinerRef")
    @Expose
    private String uplinerRef;
    @SerializedName("uplinerPhone")
    @Expose
    private String uplinerPhone;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getRelationNominee() {
        return relationNominee;
    }

    public void setRelationNominee(String relationNominee) {
        this.relationNominee = relationNominee;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEarningAmount() {
        return earningAmount;
    }

    public void setEarningAmount(String earningAmount) {
        this.earningAmount = earningAmount;
    }

    public String getUplinerRef() {
        return uplinerRef;
    }

    public void setUplinerRef(String uplinerRef) {
        this.uplinerRef = uplinerRef;
    }

    public String getUplinerPhone() {
        return uplinerPhone;
    }

    public void setUplinerPhone(String uplinerPhone) {
        this.uplinerPhone = uplinerPhone;
    }

}
