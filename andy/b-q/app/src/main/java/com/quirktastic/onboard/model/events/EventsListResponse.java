package com.quirktastic.onboard.model.events;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsListResponse {

    @SerializedName("MESSAGE")
    private String message;

    @SerializedName("EVENT_LIST")
    private ArrayList<EventListItem> eventList;

    @SerializedName("FLAG")
    private boolean flag;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setEventList(ArrayList<EventListItem> eventList) {
        this.eventList = eventList;
    }

    public ArrayList<EventListItem> getEventList() {
        return eventList;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return
                "EventsListResponse{" +
                        "message = '" + message + '\'' +
                        ",event_list = '" + eventList + '\'' +
                        ",flag = '" + flag + '\'' +
                        "}";
    }
}