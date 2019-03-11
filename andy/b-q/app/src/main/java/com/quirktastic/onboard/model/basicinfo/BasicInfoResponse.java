package com.quirktastic.onboard.model.basicinfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicInfoResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("user_details")
	private List<BasicInfoUserDetailsItem> userDetails;

	@SerializedName("FLAG")
	private boolean fLAG;

	public void setMESSAGE(String mESSAGE){
		this.mESSAGE = mESSAGE;
	}

	public String getMESSAGE(){
		return mESSAGE;
	}

	public void setUserDetails(List<BasicInfoUserDetailsItem> userDetails){
		this.userDetails = userDetails;
	}

	public List<BasicInfoUserDetailsItem> getUserDetails(){
		return userDetails;
	}

	public void setFLAG(boolean fLAG){
		this.fLAG = fLAG;
	}

	public boolean isFLAG(){
		return fLAG;
	}

}