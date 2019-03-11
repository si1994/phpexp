package com.quirktastic.onboard.model;

import com.google.gson.annotations.SerializedName;

public class InterestListItem {

    @SerializedName("interest_name")
    private String interestName;

    @SerializedName("id")
    private String id;

    @SerializedName("is_selected")
    private boolean isSelected = false;

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "InterestListItem{" +
                "interestName='" + interestName + '\'' +
                ", id='" + id + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}