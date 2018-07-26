package com.tbx.gc.greatcash.incomeJunctionPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataJunction
{
    @SerializedName("todayEarnAmount")
    @Expose
    private String todayEarnAmount;

    @SerializedName("yesterdayEarnAmount")
    @Expose
    private String yesterdayEarnAmount;

    @SerializedName("thisWeekEarnAmount")
    @Expose
    private String thisWeekEarnAmount;

    @SerializedName("thisMonthEarnAmount")
    @Expose
    private String thisMonthEarnAmount;

    public String getTodayEarnAmount() {
        return todayEarnAmount;
    }

    public void setTodayEarnAmount(String todayEarnAmount) {
        this.todayEarnAmount = todayEarnAmount;
    }

    public String getYesterdayEarnAmount() {
        return yesterdayEarnAmount;
    }

    public void setYesterdayEarnAmount(String yesterdayEarnAmount) {
        this.yesterdayEarnAmount = yesterdayEarnAmount;
    }

    public String getThisWeekEarnAmount() {
        return thisWeekEarnAmount;
    }

    public void setThisWeekEarnAmount(String thisWeekEarnAmount) {
        this.thisWeekEarnAmount = thisWeekEarnAmount;
    }

    public String getThisMonthEarnAmount() {
        return thisMonthEarnAmount;
    }

    public void setThisMonthEarnAmount(String thisMonthEarnAmount) {
        this.thisMonthEarnAmount = thisMonthEarnAmount;
    }

    public String getThisYearEarnAmount() {
        return thisYearEarnAmount;
    }

    public void setThisYearEarnAmount(String thisYearEarnAmount) {
        this.thisYearEarnAmount = thisYearEarnAmount;
    }

    @SerializedName("thisYearEarnAmount")
    @Expose
    private String thisYearEarnAmount;


}
