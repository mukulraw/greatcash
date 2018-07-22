package com.tbx.gc.greatcash.redeemPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("requestId")
    @Expose
    private String requestId;

    @SerializedName("redeemAmount")
    @Expose
    private String redeemAmount;

    @SerializedName("paymentMode")
    @Expose
    private String payMentMode;

    @SerializedName("bankId")
    @Expose
    private String bankID;

    @SerializedName("accountNo")
    @Expose
    private String accountNumber;

    @SerializedName("ifscCode")
    @Expose
    private String ifscCode;

    @SerializedName("bankName")
    @Expose
    private String bankName;

    @SerializedName("createDate")
    @Expose
    private String createDate;


    public String getRedeemAmount() {
        return redeemAmount;
    }

    public void setRedeemAmount(String redeemAmount) {
        this.redeemAmount = redeemAmount;
    }

    public String getPayMentMode() {
        return payMentMode;
    }

    public void setPayMentMode(String payMentMode) {
        this.payMentMode = payMentMode;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


}


