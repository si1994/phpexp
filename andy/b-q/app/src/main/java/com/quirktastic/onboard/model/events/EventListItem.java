package com.quirktastic.onboard.model.events;

import com.google.gson.annotations.SerializedName;

public class EventListItem {

    @SerializedName("date")
    private String date;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("place_name")
    private String placeName;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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
                "EventListItem{" +
                        "date = '" + date + '\'' +
                        ",start_time = '" + startTime + '\'' +
                        ",place_name = '" + placeName + '\'' +
                        ",end_time = '" + endTime + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        ",title = '" + title + '\'' +
                        "}";
    }
}