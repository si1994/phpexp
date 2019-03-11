package com.quirktastic.dashboard.profile.model.preferences;


import com.google.gson.annotations.SerializedName;

public class GenderItem{

	@SerializedName("gender")
	private String gender;

	@SerializedName("is_selected")
	private int isSelected;

	@SerializedName("id")
	private String id;

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setIsSelected(int isSelected){
		this.isSelected = isSelected;
	}

	public int getIsSelected(){
		return isSelected;
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
			"GenderItem{" + 
			"gender = '" + gender + '\'' + 
			",is_selected = '" + isSelected + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}