package com.quirktastic.onboard.model.identity;

import com.google.gson.annotations.SerializedName;

public class IdentityListItem {

    @SerializedName("identity_name")
    private String identityName;

    @SerializedName("identity_description")
    private String identityDescription;

    @SerializedName("id")
    private String id;

    @SerializedName("is_selected")
    private boolean isSelected = false;

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityDescription(String identityDescription) {
        this.identityDescription = identityDescription;
    }

    public String getIdentityDescription() {
        return identityDescription;
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
        return "IdentityListItem{" +
                "identityName='" + identityName + '\'' +
                ", identityDescription='" + identityDescription + '\'' +
                ", id='" + id + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}