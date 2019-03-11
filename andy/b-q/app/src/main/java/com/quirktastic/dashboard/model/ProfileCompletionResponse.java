package com.quirktastic.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class ProfileCompletionResponse {

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("FLAG")
	private boolean fLAG;

	@SerializedName("PROFILE_COMPLETION")
	private double pROFILECOMPLETION;

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

	public void setPROFILECOMPLETION(double pROFILECOMPLETION){
		this.pROFILECOMPLETION = pROFILECOMPLETION;
	}

	public double getPROFILECOMPLETION(){
		return pROFILECOMPLETION;
	}

	@Override
 	public String toString(){
		return 
			"ProfileCompletionResponse{" +
			"mESSAGE = '" + mESSAGE + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			",pROFILE_COMPLETION = '" + pROFILECOMPLETION + '\'' + 
			"}";
		}
}