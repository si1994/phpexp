package com.quirktastic.dashboard.profile.model.profiledetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfileDetailsResponse implements Serializable {

    @SerializedName("MESSAGE")
    private String mESSAGE;

    @SerializedName("USER_DETAILS")
    private ArrayList<UserDetailsItem> userDetails;

    @SerializedName("FLAG")
    private boolean fLAG;

    @SerializedName("IS_ACTIVE")
    private boolean iSACTIVE;

    public void setMessage(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public String getMessage() {
        return mESSAGE;
    }

    public void setUserDetails(ArrayList<UserDetailsItem> userDetails) {
        this.userDetails = userDetails;
    }

    public ArrayList<UserDetailsItem> getUserDetails() {
        return userDetails;
    }

    public void setFlag(boolean fLAG) {
        this.fLAG = fLAG;
    }

    public boolean isFlag() {
        return fLAG;
    }

    public void setISACTIVE(boolean iSACTIVE) {
        this.iSACTIVE = iSACTIVE;
    }

    public boolean isISACTIVE() {
        return iSACTIVE;
    }

    @Override
    public String toString() {
        return
                "ProfileDetailsResponse{" +
                        "mESSAGE = '" + mESSAGE + '\'' +
                        ",uSER_DETAILS = '" + userDetails + '\'' +
                        ",fLAG = '" + fLAG + '\'' +
                        ",iS_ACTIVE = '" + iSACTIVE + '\'' +
                        "}";
    }
}