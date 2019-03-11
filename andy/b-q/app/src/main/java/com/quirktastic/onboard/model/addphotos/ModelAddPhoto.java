package com.quirktastic.onboard.model.addphotos;


import com.google.gson.annotations.SerializedName;
import com.quirktastic.onboard.model.fblogin.FbLoginDetailsItem;

import java.util.ArrayList;

public class ModelAddPhoto{

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("FLAG")
	private boolean fLAG;

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

	public ArrayList<FbLoginDetailsItem> getlOGINDETAILS() {
		return lOGINDETAILS;
	}

	public void setlOGINDETAILS(ArrayList<FbLoginDetailsItem> lOGINDETAILS) {
		this.lOGINDETAILS = lOGINDETAILS;
	}

	@Override
 	public String toString(){
		return 
			"ModelAddPhoto{" + 
			"mESSAGE = '" + mESSAGE + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}