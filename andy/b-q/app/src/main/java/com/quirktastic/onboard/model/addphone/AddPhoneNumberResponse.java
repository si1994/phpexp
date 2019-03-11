package com.quirktastic.onboard.model.addphone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddPhoneNumberResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("user_details")
	private AddPhoneNumberUserDetails userDetails;

	@SerializedName("LOGIN_DETAILS")
	private List<AddPhoneNumberLoginDetailsItem> lOGINDETAILS;

	@SerializedName("FLAG")
	private boolean fLAG;

	@SerializedName("IS_PHONE")
	private String iSPHONE;

	public void setMESSAGE(String mESSAGE){
		this.mESSAGE = mESSAGE;
	}

	public String getMESSAGE(){
		return mESSAGE;
	}

	public void setUserDetails(AddPhoneNumberUserDetails userDetails){
		this.userDetails = userDetails;
	}

	public AddPhoneNumberUserDetails getUserDetails(){
		return userDetails;
	}

	public void setFLAG(boolean fLAG){
		this.fLAG = fLAG;
	}

	public boolean isFLAG(){
		return fLAG;
	}

	public void setISPHONE(String iSPHONE){
		this.iSPHONE = iSPHONE;
	}

	public String getISPHONE(){
		return iSPHONE;
	}

	public void setLOGINDETAILS(List<AddPhoneNumberLoginDetailsItem> lOGINDETAILS){
		this.lOGINDETAILS = lOGINDETAILS;
	}

	public List<AddPhoneNumberLoginDetailsItem> getLOGINDETAILS(){
		return lOGINDETAILS;
	}

}