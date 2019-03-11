package com.quirktastic.dashboard.model.modeluserlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelUserList{

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("TOTAL_USER")
	private String tOTALUSER;

	@SerializedName("USER_LIST")
	private List<USERLISTItem> uSERLIST;

	@SerializedName("FLAG")
	private boolean fLAG;

	public void setMESSAGE(String mESSAGE){
		this.mESSAGE = mESSAGE;
	}

	public String getMESSAGE(){
		return mESSAGE;
	}

	public void setTOTALUSER(String tOTALUSER){
		this.tOTALUSER = tOTALUSER;
	}

	public String getTOTALUSER(){
		return tOTALUSER;
	}

	public void setUSERLIST(List<USERLISTItem> uSERLIST){
		this.uSERLIST = uSERLIST;
	}

	public List<USERLISTItem> getUSERLIST(){
		return uSERLIST;
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
			"ModelUserList{" + 
			"mESSAGE = '" + mESSAGE + '\'' + 
			",tOTAL_USER = '" + tOTALUSER + '\'' + 
			",uSER_LIST = '" + uSERLIST + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			"}";
		}
}