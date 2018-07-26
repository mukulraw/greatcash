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

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

}
