package com.quirktastic.onboard.model.fblogin;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FbLoginResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("LOGIN_DETAILS")
	private List<FbLoginDetailsItem> lOGINDETAILS;

	@SerializedName("IS_PHONE")
	private String iSPHONE;

	@SerializedName("FLAG")
	private boolean fLAG;

	public void setMESSAGE(String mESSAGE){
		this.mESSAGE = mESSAGE;
	}

	public String getMESSAGE(){
		return mESSAGE;
	}

	public void setLOGINDETAILS(List<FbLoginDetailsItem> lOGINDETAILS){
		this.lOGINDETAILS = lOGINDETAILS;
	}

	public List<FbLoginDetailsItem> getLOGINDETAILS(){
		return lOGINDETAILS;
	}

	public void setISPHONE(String iSPHONE){
		this.iSPHONE = iSPHONE;
	}

	public String getISPHONE(){
		return iSPHONE;
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
			"FbLoginResponse{" +
			"mESSAGE = '" + mESSAGE + '\'' + 
			",lOGIN_DETAILS = '" + lOGINDETAILS + '\'' + 
			",iS_PHONE = '" + iSPHONE + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}