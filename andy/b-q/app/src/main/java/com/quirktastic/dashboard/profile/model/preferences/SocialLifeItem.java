package com.quirktastic.dashboard.profile.model.preferences;


import com.google.gson.annotations.SerializedName;

public class SocialLifeItem{

	@SerializedName("is_selected")
	private int isSelected;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public void setIsSelected(int isSelected){
		this.isSelected = isSelected;
	}

	public int getIsSelected(){
		return isSelected;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"SocialLifeItem{" + 
			"is_selected = '" + isSelected + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}