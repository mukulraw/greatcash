package com.tbx.gc.greatcash.audioPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("audioId")
    @Expose
    private String audioId;
    @SerializedName("tittle")
    @Expose
    private String tittle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fileUrl")
    @Expose
    private String fileUrl;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
