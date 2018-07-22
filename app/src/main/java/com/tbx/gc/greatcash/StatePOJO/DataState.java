package com.tbx.gc.greatcash.StatePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Priyanka on 7/10/2018.
 */

public class DataState
{
    @SerializedName("stateId")
    @Expose
    private String stateID;

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @SerializedName("stateName")
    @Expose
    private String stateName;

}
