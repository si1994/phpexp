package com.quirktastic.dashboard.friendrequeststatus.model;

import com.google.gson.annotations.SerializedName;

public class FriendRequestStatusResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("FLAG")
	private boolean fLAG;

	public void setMESSAGE(String mESSAGE){
		this.mESSAGE = mESSAGE;
	}

	public String getMESSAGE(){
		return mESSAGE;
	}

	public void setFLAG(boolean fLAG){
		this.fLAG = fLAG;
	}

	public boolean isFLAG(){
		return fLAG;
	}

	@Override
 	public String toString(){
		return 
			"FriendRequestStatusResponse{" +
			"mESSAGE = '" + mESSAGE + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}