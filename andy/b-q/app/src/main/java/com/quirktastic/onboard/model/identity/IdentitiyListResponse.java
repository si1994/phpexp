package com.quirktastic.onboard.model.identity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IdentitiyListResponse {

    @SerializedName("MESSAGE")
    private String message;

    @SerializedName("IDENTITY_LIST")
    private ArrayList<IdentityListItem> identityList;

    @SerializedName("FLAG")
    private boolean fLAG;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setIdentityList(ArrayList<IdentityListItem> identityList) {
        this.identityList = identityList;
    }

    public ArrayList<IdentityListItem> getIdentityList() {
        return identityList;
    }

    public void setFlag(boolean fLAG) {
        this.fLAG = fLAG;
    }

    public boolean isFlag() {
        return fLAG;
    }

    @Override
    public String toString() {
        return
                "IdentitiyListResponse{" +
                        "message = '" + message + '\'' +
                        ",identity_list = '" + identityList + '\'' +
                        ",flag = '" + fLAG + '\'' +
                        "}";
    }
}