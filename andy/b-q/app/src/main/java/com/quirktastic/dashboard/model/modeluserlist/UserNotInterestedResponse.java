package com.quirktastic.dashboard.model.modeluserlist;

import com.google.gson.annotations.SerializedName;

public class UserNotInterestedResponse{

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
			"UserNotInterestedResponse{" + 
			"mESSAGE = '" + mESSAGE + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			",iS_ACTIVE = '" + iSACTIVE + '\'' + 
			"}";
		}
}