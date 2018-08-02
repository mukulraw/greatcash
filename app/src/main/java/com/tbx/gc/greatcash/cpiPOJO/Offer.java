package com.tbx.gc.greatcash.cpiPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("payout_currency")
    @Expose
    private String payoutCurrency;
    @SerializedName("payout_type")
    @Expose
    private String payoutType;
    @SerializedName("traffic_type")
    @Expose
    private String trafficType;
    @SerializedName("campid")
    @Expose
    private String campid;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("epc")
    @Expose
    private String epc;
    @SerializedName("ratio")
    @Expose
    private String ratio;
    @SerializedName("creatives")
    @Expose
    private List<Object> creatives = null;
    @SerializedName("previews")
    @Expose
    private List<Preview> previews = null;
    @SerializedName("preview_url")
    @Expose
    private String previewUrl;
    @SerializedName("mobile_optimized")
    @Expose
    private String mobileOptimized;
    @SerializedName("mobile_app")
    @Expose
    private String mobileApp;
    @SerializedName("mobile_app_id")
    @Expose
    private Object mobileAppId;
    @SerializedName("mobile_app_minimum_version")
    @Expose
    private Object mobileAppMinimumVersion;
    @SerializedName("mobile_app_type")
    @Expose
    private String mobileAppType;
    @SerializedName("mobile_app_icon_url")
    @Expose
    private Object mobileAppIconUrl;
    @SerializedName("button_text")
    @Expose
    private String buttonText;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("conversion")
    @Expose
    private String conversion;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayoutCurrency() {
        return payoutCurrency;
    }

    public void setPayoutCurrency(String payoutCurrency) {
        this.payoutCurrency = payoutCurrency;
    }

    public String getPayoutType() {
        return payoutType;
    }

    public void setPayoutType(String payoutType) {
        this.payoutType = payoutType;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public String getCampid() {
        return campid;
    }

    public void setCampid(String campid) {
        this.campid = campid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public List<Object> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<Object> creatives) {
        this.creatives = creatives;
    }

    public List<Preview> getPreviews() {
        return previews;
    }

    public void setPreviews(List<Preview> previews) {
        this.previews = previews;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getMobileOptimized() {
        return mobileOptimized;
    }

    public void setMobileOptimized(String mobileOptimized) {
        this.mobileOptimized = mobileOptimized;
    }

    public String getMobileApp() {
        return mobileApp;
    }

    public void setMobileApp(String mobileApp) {
        this.mobileApp = mobileApp;
    }

    public Object getMobileAppId() {
        return mobileAppId;
    }

    public void setMobileAppId(Object mobileAppId) {
        this.mobileAppId = mobileAppId;
    }

    public Object getMobileAppMinimumVersion() {
        return mobileAppMinimumVersion;
    }

    public void setMobileAppMinimumVersion(Object mobileAppMinimumVersion) {
        this.mobileAppMinimumVersion = mobileAppMinimumVersion;
    }

    public String getMobileAppType() {
        return mobileAppType;
    }

    public void setMobileAppType(String mobileAppType) {
        this.mobileAppType = mobileAppType;
    }

    public Object getMobileAppIconUrl() {
        return mobileAppIconUrl;
    }

    public void setMobileAppIconUrl(Object mobileAppIconUrl) {
        this.mobileAppIconUrl = mobileAppIconUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }
}
