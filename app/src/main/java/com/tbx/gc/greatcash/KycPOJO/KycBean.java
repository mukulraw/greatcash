package com.tbx.gc.greatcash.KycPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.redeemPOJO.Datum;

import java.util.List;

public class KycBean
{
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<DataKyc> data = null;

    public String getMessage_kyc() {
        return message;
    }

    public void setMessage_kyc(String message) {
        this.message = message;
    }

    public String getStatus_kyc() {
        return status;
    }

    public void setStatus_kyc(String status) {
        this.status = status;
    }

    public List<DataKyc> getData_kyc() {
        return data;
    }

    public void setData_kyc
            (List<DataKyc> data) {
        this.data = data;
    }
}
