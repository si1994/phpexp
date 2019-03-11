package com.quirktastic.onboard.model.sociallife;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SocialLifeResponse {

    @SerializedName("MESSAGE")
    private String message;

    @SerializedName("FLAG")
    private boolean flag;

    @SerializedName("SOCIAL_LIFE_LIST")
    private ArrayList<SocialLifeListItem> socialLifeList;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setFlag(boolean fLAG) {
        this.flag = fLAG;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setSociallifeList(ArrayList<SocialLifeListItem> socialLifeList) {
        this.socialLifeList = socialLifeList;
    }

    public ArrayList<SocialLifeListItem> getSociallifeList() {
        return socialLifeList;
    }

    @Override
    public String toString() {
        return
                "SocialLifeResponse{" +
                        "message = '" + message + '\'' +
                        ",flag = '" + flag + '\'' +
                        ",social_life_list = '" + socialLifeList + '\'' +
                        "}";
    }
}