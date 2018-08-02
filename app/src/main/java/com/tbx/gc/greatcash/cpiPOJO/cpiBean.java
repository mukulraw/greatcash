package com.tbx.gc.greatcash.cpiPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class cpiBean {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("number_offers")
    @Expose
    private Integer numberOffers;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumberOffers() {
        return numberOffers;
    }

    public void setNumberOffers(Integer numberOffers) {
        this.numberOffers = numberOffers;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
