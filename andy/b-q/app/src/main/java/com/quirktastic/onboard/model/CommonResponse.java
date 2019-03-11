package com.quirktastic.onboard.model;

import com.google.gson.annotations.SerializedName;

public class CommonResponse {

    @SerializedName("MESSAGE")
    private String message;

    @SerializedName("FLAG")
    private boolean flag;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
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
                "CommonResponse{" +
                        "message = '" + message + '\'' +
                        ",flag = '" + flag + '\'' +
                        "}";
    }
}