package com.quirktastic.dashboard.profile.model.profiledetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventItem  implements Serializable {

    @SerializedName("is_selected")
    private int isSelected;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return
                "EventItem{" +
                        "is_selected = '" + isSelected + '\'' +
                        ",id = '" + id + '\'' +
                        ",title = '" + title + '\'' +
                        "}";
    }
}