package com.tbx.gc.greatcash.RedeemRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRedReq
{
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("redeemAmount")
    @Expose
    private String redeemAmt;

    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;

    @SerializedName("bankId")
    @Expose
    private String bankID;

    @SerializedName("accountNo")
    @Expose
    private String accountNum;

    @SerializedName("ifscCode")
    @Expose
    private String ifscCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRedeemAmt() {
        return redeemAmt;
    }

    public void setRedeemAmt(String redeemAmt) {
        this.redeemAmt = redeemAmt;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
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

    @SerializedName("bankName")
    @Expose
    private String bankName;

}
