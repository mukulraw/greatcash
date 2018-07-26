package com.tbx.gc.greatcash.createTicketPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTicket
{

    @SerializedName("ticketNo")
    @Expose
    private String ticketNo;

    @SerializedName("userId")
    @Expose
    private String userID;

    @SerializedName("report")
    @Expose
    private String report;

    @SerializedName("status")
    @Expose
    private String status;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @SerializedName("createDate")
    @Expose
    private String createdDate;

}
