package com.tbx.gc.greatcash.TicketReq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataReqticket
{

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("questionId")
    @Expose
    private String questionID;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @SerializedName("report")
    @Expose

    private String report;

}
