package com.tbx.gc.greatcash.challengePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("taskId")
    @Expose
    private String taskId;
    @SerializedName("taskImage")
    @Expose
    private String taskImage;
    @SerializedName("taskPackage")
    @Expose
    private String taskPackage;
    @SerializedName("taskUrl")
    @Expose
    private String taskUrl;
    @SerializedName("tastStatus")
    @Expose
    private String tastStatus;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskImage() {
        return taskImage;
    }

    public void setTaskImage(String taskImage) {
        this.taskImage = taskImage;
    }

    public String getTaskPackage() {
        return taskPackage;
    }

    public void setTaskPackage(String taskPackage) {
        this.taskPackage = taskPackage;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getTastStatus() {
        return tastStatus;
    }

    public void setTastStatus(String tastStatus) {
        this.tastStatus = tastStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
