package com.quirktastic.dashboard.model.modelmyconversation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMyConversation{

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("USER_DETAILS")
	private List<USERDETAILSItem> uSERDETAILS;

	@SerializedName("FLAG")
	private boolean fLAG;

	public void setMESSAGE(String mESSAGE){
		this.mESSAGE = mESSAGE;
	}

	public String getMESSAGE(){
		return mESSAGE;
	}

	public void setUSERDETAILS(List<USERDETAILSItem> uSERDETAILS){
		this.uSERDETAILS = uSERDETAILS;
	}

	public List<USERDETAILSItem> getUSERDETAILS(){
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
			"ModelMyConversation{" + 
			"mESSAGE = '" + mESSAGE + '\'' + 
			",uSER_DETAILS = '" + uSERDETAILS + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}