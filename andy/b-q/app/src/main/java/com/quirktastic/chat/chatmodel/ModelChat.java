package com.quirktastic.chat.chatmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelChat{

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("USER_DETAILS")
	private List<USERDETAILSItem> uSERDETAILS;

	@SerializedName("TOTAL_RECORDS")
	private int tOTALRECORDS;

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

	public void setTOTALRECORDS(int tOTALRECORDS){
		this.tOTALRECORDS = tOTALRECORDS;
	}

	public int getTOTALRECORDS(){
		return tOTALRECORDS;
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
			"ModelChat{" + 
			"mESSAGE = '" + mESSAGE + '\'' + 
			",uSER_DETAILS = '" + uSERDETAILS + '\'' + 
			",tOTAL_RECORDS = '" + tOTALRECORDS + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}