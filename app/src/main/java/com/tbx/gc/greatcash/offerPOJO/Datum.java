package com.tbx.gc.greatcash.offerPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("offerId")
    @Expose
    private String offerId;
    @SerializedName("offerImage")
    @Expose
    private String offerImage;
    @SerializedName("offerkPackage")
    @Expose
    private String offerkPackage;
    @SerializedName("offerUrl")
    @Expose
    private String offerUrl;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getOfferkPackage() {
        return offerkPackage;
    }

    public void setOfferkPackage(String offerkPackage) {
        this.offerkPackage = offerkPackage;
    }

    public String getOfferUrl() {
        return offerUrl;
    }

    public void setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
