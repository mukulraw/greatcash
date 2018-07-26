package com.tbx.gc.greatcash.shoppingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EarningSummary {

    @SerializedName("thismonth")
    @Expose
    private String thismonth;
    @SerializedName("today")
    @Expose
    private String today;

    public String getThismonth() {
        return thismonth;
    }

    public void setThismonth(String thismonth) {
        this.thismonth = thismonth;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }


}
