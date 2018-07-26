package com.tbx.gc.greatcash.QuestionPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbx.gc.greatcash.CountryPOJO.DataCountry;
import com.tbx.gc.greatcash.hotListPOJO.DataHotList;

import java.util.List;

public class QuestionBean
{
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataQuestion> getData() {
        return data;
    }

    public void setData(List<DataQuestion> data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    private List<DataQuestion> data = null;

}
