package com.quirktastic.network;

import com.androidnetworking.error.ANError;

public interface INetworkResponse {
    public void onSuccess(String response);
    public void onError(ANError error);
}
