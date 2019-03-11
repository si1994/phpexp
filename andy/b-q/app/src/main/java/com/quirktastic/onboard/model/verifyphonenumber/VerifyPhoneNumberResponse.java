package com.quirktastic.onboard.model.verifyphonenumber;

import com.google.gson.annotations.SerializedName;
import com.quirktastic.onboard.model.fblogin.FbLoginDetailsItem;

import java.util.ArrayList;

public class VerifyPhoneNumberResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("FLAG")
	private boolean fLAG;

	@SerializedName("is_registered")
	private String isRegistered;

	@SerializedName("USER_DETAILS")
	private ArrayList<FbLoginDetailsItem> lOGINDETAILS;

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

	public String getIsRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(String isRegistered) {
		this.isRegistered = isRegistered;
	}

	public ArrayList<FbLoginDetailsItem> getLOGINDETAILS() {
		return lOGINDETAILS;
	}

	public void setLOGINDETAILS(ArrayList<FbLoginDetailsItem> lOGINDETAILS) {
		this.lOGINDETAILS = lOGINDETAILS;
	}

	@Override
 	public String toString(){
		return 
			"VerifyPhoneNumberResponse{" +
			"mESSAGE = '" + mESSAGE + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}