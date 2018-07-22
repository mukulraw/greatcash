package com.tbx.gc.greatcash.comboPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("comboId")
    @Expose
    private String comboId;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("earnAmount")
    @Expose
    private String earnAmount;
    @SerializedName("comboUserStatus")
    @Expose
    private String comboUserStatus;
    @SerializedName("comboItem")
    @Expose
    private List<ComboItem> comboItem = null;

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEarnAmount() {
        return earnAmount;
    }

    public void setEarnAmount(String earnAmount) {
        this.earnAmount = earnAmount;
    }

    public String getComboUserStatus() {
        return comboUserStatus;
    }

    public void setComboUserStatus(String comboUserStatus) {
        this.comboUserStatus = comboUserStatus;
    }

    public List<ComboItem> getComboItem() {
        return comboItem;
    }

    public void setComboItem(List<ComboItem> comboItem) {
        this.comboItem = comboItem;
    }
}
