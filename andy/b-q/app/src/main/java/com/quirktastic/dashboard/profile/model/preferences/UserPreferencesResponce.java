package com.quirktastic.dashboard.profile.model.preferences;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UserPreferencesResponce{

	@SerializedName("MESSAGE")
	private String mESSAGE;

	@SerializedName("USER_PREFERENCES")
	private List<USERPREFERENCESItem> uSERPREFERENCES;

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

	public void setUSERPREFERENCES(List<USERPREFERENCESItem> uSERPREFERENCES){
		this.uSERPREFERENCES = uSERPREFERENCES;
	}

	public List<USERPREFERENCESItem> getUSERPREFERENCES(){
		return uSERPREFERENCES;
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
			"UserPreferencesResponce{" + 
			"mESSAGE = '" + mESSAGE + '\'' + 
			",uSER_PREFERENCES = '" + uSERPREFERENCES + '\'' + 
			",fLAG = '" + fLAG + '\'' + 
			",iS_ACTIVE = '" + iSACTIVE + '\'' + 
			"}";
		}
}