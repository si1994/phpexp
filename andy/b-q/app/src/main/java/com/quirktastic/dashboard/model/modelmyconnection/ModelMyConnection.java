package com.quirktastic.dashboard.model.modelmyconnection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMyConnection{

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("USER_DETAILS")
	private List<USERDETAILSItem> uSERDETAILS;

	@SerializedName("PENDING_COUNT")
	private String pENDINGCOUNT;

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

	public void setPENDINGCOUNT(String pENDINGCOUNT){
		this.pENDINGCOUNT = pENDINGCOUNT;
	}

	public String getPENDINGCOUNT(){
		return pENDINGCOUNT;
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
			"ModelMyConnection{" + 
			"mESSAGE = '" + mESSAGE + '\'' + 
			",uSER_DETAILS = '" + uSERDETAILS + '\'' + 
			",pENDING_COUNT = '" + pENDINGCOUNT + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}