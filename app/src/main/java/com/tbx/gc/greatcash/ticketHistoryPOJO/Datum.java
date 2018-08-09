package com.tbx.gc.greatcash.ticketHistoryPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("ticketNo")
    @Expose
    private String ticketNo;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("report")
    @Expose
    private String report;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createDate")
    @Expose
    private String createDate;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
