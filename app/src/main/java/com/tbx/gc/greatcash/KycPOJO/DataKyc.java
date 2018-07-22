package com.tbx.gc.greatcash.KycPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataKyc
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("userId")
    @Expose
    private String userID;

    @SerializedName("userPic")
    @Expose
    private String userpic;

    @SerializedName("idNumber")
    @Expose
    private String idNumber;

    @SerializedName("file")
    @Expose
    private String docPic;

    @SerializedName("nominee_name")
    @Expose
    private String nomineeName;

    @SerializedName("nominee_relation")
    @Expose
    private String nomineeRelation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDocPic() {
        return docPic;
    }

    public void setDocPic(String docPic) {
        this.docPic = docPic;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeDoc() {
        return nomineeDoc;
    }

    public void setNomineeDoc(String nomineeDoc) {
        this.nomineeDoc = nomineeDoc;
    }

    @SerializedName("nominee_dob")
    @Expose
    private String nomineeDoc;


}
