package com.quirktastic.onboard.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InterestListResponse implements Serializable {

    @SerializedName("MESSAGE")
    private String message;

    @SerializedName("INTEREST_LIST")
    private ArrayList<InterestListItem> interestList;

    @SerializedName("FLAG")
    private boolean flag;

    public void setMessage(String mESSAGE) {
        this.message = mESSAGE;
    }

    public String getMessage() {
        return message;
    }

    public void setInterestList(ArrayList<InterestListItem> interestList) {
        this.interestList = interestList;
    }

    public ArrayList<InterestListItem> getInterestList() {
        return interestList;
    }

    public void setFlag(boolean fLAG) {
        this.flag = fLAG;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return
                "InterestListResponse{" +
                        "message = '" + message + '\'' +
                        ",interestList = '" + interestList + '\'' +
                        ",fLAG = '" + flag + '\'' +
                        "}";
    }
}