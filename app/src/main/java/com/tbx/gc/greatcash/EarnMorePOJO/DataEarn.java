package com.tbx.gc.greatcash.EarnMorePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Priyanka on 7/9/2018.
 */

public class DataEarn
{

    public String getTypee() {
        return type;
    }

    public void setTypee(String type) {
        this.type = type;
    }

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("earnAmount")
    @Expose
    private String earnAmount;

    @SerializedName("heading")
    @Expose
    private String heading;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("video")
    @Expose
    private String video;

    @SerializedName("audio")
    @Expose
    private String audio;

    @SerializedName("redirectUrl")
    @Expose
    private String redirectUrl;

    public String getIdd() {
        return id;
    }

    public void setIdd(String id) {
        this.id = id;
    }

    public String getEarnAmount() {
        return earnAmount;
    }

    public void setEarnAmount(String earnAmount) {
        this.earnAmount = earnAmount;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImagee() {
        return image;
    }

    public void setImagee(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
