package com.tbx.gc.greatcash.TicketReq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.RedeemRequest.DataRedReq;

public class Ticketrequest
{
    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("data")
    @Expose
    private DataReqticket data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataReqticket getData() {
        return data;
    }

    public void setData(DataReqticket data) {
        this.data = data;
    }
}
