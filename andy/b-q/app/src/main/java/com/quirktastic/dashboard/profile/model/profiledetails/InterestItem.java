package com.quirktastic.dashboard.profile.model.profiledetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InterestItem implements Serializable {

    @SerializedName("is_selected")
    private int isSelected;

    @SerializedName("interest_name")
    private String interestName;

    @SerializedName("id")
    private String id;

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "InterestItem{" +
                        "is_selected = '" + isSelected + '\'' +
                        ",interest_name = '" + interestName + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}