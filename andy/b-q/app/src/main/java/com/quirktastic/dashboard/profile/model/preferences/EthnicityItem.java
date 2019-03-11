package com.quirktastic.dashboard.profile.model.preferences;


import com.google.gson.annotations.SerializedName;

public class EthnicityItem{

	@SerializedName("is_selected")
	private int isSelected;

	@SerializedName("identity_name")
	private String identityName;

	@SerializedName("id")
	private String id;

	public void setIsSelected(int isSelected){
		this.isSelected = isSelected;
	}

	public int getIsSelected(){
		return isSelected;
	}

	public void setIdentityName(String identityName){
		this.identityName = identityName;
	}

	public String getIdentityName(){
		return identityName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"EthnicityItem{" + 
			"is_selected = '" + isSelected + '\'' + 
			",identity_name = '" + identityName + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}