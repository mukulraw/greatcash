package com.tbx.gc.greatcash.shoppingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {


    @SerializedName("earningSummary")
    @Expose
    private EarningSummary earningSummary;
    @SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("sale")
    @Expose
    private List<Sale> sale = null;

    public EarningSummary getEarningSummary() {
        return earningSummary;
    }

    public void setEarningSummary(EarningSummary earningSummary) {
        this.earningSummary = earningSummary;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Sale> getSale() {
        return sale;
    }

    public void setSale(List<Sale> sale) {
        this.sale = sale;
    }

}
