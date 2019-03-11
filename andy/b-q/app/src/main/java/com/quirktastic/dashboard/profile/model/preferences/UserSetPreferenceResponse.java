package com.quirktastic.dashboard.profile.model.preferences;

import com.google.gson.annotations.SerializedName;

public class UserSetPreferenceResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("FLAG")
	private boolean fLAG;

	@SerializedName("IS_ACTIVE")
	private boolean iSACTIVE;

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

	public void setISACTIVE(boolean iSACTIVE){
		this.iSACTIVE = iSACTIVE;
	}

	public boolean isISACTIVE(){
		return iSACTIVE;
	}

	@Override
 	public String toString(){
		return 
			"UserSetPreferenceResponse{" +
			"mESSAGE = '" + mESSAGE + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			",iS_ACTIVE = '" + iSACTIVE + '\'' + 
			"}";
		}
}
