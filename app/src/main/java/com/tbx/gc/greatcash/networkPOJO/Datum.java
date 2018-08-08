package com.tbx.gc.greatcash.networkPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("joiningId")
    @Expose
    private String joiningId;
    @SerializedName("joiningName")
    @Expose
    private String joiningName;
    @SerializedName("joiningEmail")
    @Expose
    private String joiningEmail;
    @SerializedName("joiningPhone")
    @Expose
    private String joiningPhone;
    @SerializedName("joiningRefId")
    @Expose
    private String joiningRefId;
    @SerializedName("joiningPic")
    @Expose
    private String joiningPic;
    @SerializedName("joiningDate")
    @Expose
    private String joiningDate;

    public String getJoiningId() {
        return joiningId;
    }

    public void setJoiningId(String joiningId) {
        this.joiningId = joiningId;
    }

    public String getJoiningName() {
        return joiningName;
    }

    public void setJoiningName(String joiningName) {
        this.joiningName = joiningName;
    }

    public String getJoiningEmail() {
        return joiningEmail;
    }

    public void setJoiningEmail(String joiningEmail) {
        this.joiningEmail = joiningEmail;
    }

    public String getJoiningPhone() {
        return joiningPhone;
    }

    public void setJoiningPhone(String joiningPhone) {
        this.joiningPhone = joiningPhone;
    }

    public String getJoiningRefId() {
        return joiningRefId;
    }

    public void setJoiningRefId(String joiningRefId) {
        this.joiningRefId = joiningRefId;
    }

    public String getJoiningPic() {
        return joiningPic;
    }

    public void setJoiningPic(String joiningPic) {
        this.joiningPic = joiningPic;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

}
