package com.tbx.gc.greatcash.KycRequestPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycRequest
{
    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("file")
    @Expose
    private String fileDoc;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("userPic")
    @Expose
    private String userPic;

    @SerializedName("idNumber")
    @Expose
    private String idNumber;

    @SerializedName("nominee_name")
    @Expose
    private String NomineeName;

    @SerializedName("nominee_relation")
    @Expose
    private String NomineeRelation;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileDoc() {
        return fileDoc;
    }

    public void setFileDoc(String fileDoc) {
        this.fileDoc = fileDoc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNomineeName() {
        return NomineeName;
    }

    public void setNomineeName(String nomineeName) {
        NomineeName = nomineeName;
    }

    public String getNomineeRelation() {
        return NomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        NomineeRelation = nomineeRelation;
    }

    public String getNomineeDob() {
        return NomineeDob;
    }

    public void setNomineeDob(String nomineeDob) {
        NomineeDob = nomineeDob;
    }

    @SerializedName("nominee_dob")
    @Expose
    private String NomineeDob;

}
