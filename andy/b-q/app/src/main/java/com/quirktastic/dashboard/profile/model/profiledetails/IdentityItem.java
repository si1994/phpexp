package com.quirktastic.dashboard.profile.model.profiledetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IdentityItem  implements Serializable {

    @SerializedName("is_selected")
    private int isSelected;

    @SerializedName("identity_name")
    private String identityName;

    @SerializedName("id")
    private String id;

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdentityName() {
        return identityName;
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
                "IdentityItem{" +
                        "is_selected = '" + isSelected + '\'' +
                        ",identity_name = '" + identityName + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}