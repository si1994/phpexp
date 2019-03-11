package com.quirktastic.dashboard.friendrequeststatus.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendRequestListResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("USER_DETAILS")
	private List<FriendRequestListUserDetailsItem> uSERDETAILS;

	@SerializedName("FLAG")
	private boolean fLAG;

	public void setMESSAGE(String mESSAGE){
		this.mESSAGE = mESSAGE;
	}

	public String getMESSAGE(){
		return mESSAGE;
	}

	public void setUSERDETAILS(List<FriendRequestListUserDetailsItem> uSERDETAILS){
		this.uSERDETAILS = uSERDETAILS;
	}

	public List<FriendRequestListUserDetailsItem> getUSERDETAILS(){
		return uSERDETAILS;
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
			"FriendRequestListResponse{" +
			"mESSAGE = '" + mESSAGE + '\'' + 
			",uSER_DETAILS = '" + uSERDETAILS + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}