package com.quirktastic.dashboard.model.modeluserlist;


import com.google.gson.annotations.SerializedName;

public class SocialLife{

	@SerializedName("marijuanas")
	private String marijuanas;

	@SerializedName("drinking")
	private String drinking;

	@SerializedName("smoking")
	private String smoking;

	public void setMarijuanas(String marijuanas){
		this.marijuanas = marijuanas;
	}

	public String getMarijuanas(){
		return marijuanas;
	}

	public void setDrinking(String drinking){
		this.drinking = drinking;
	}

	public String getDrinking(){
		return drinking;
	}

	public void setSmoking(String smoking){
		this.smoking = smoking;
	}

	public String getSmoking(){
		return smoking;
	}

	@Override
 	public String toString(){
		return 
			"SocialLife{" + 
			"marijuanas = '" + marijuanas + '\'' + 
			",drinking = '" + drinking + '\'' + 
			",smoking = '" + smoking + '\'' + 
			"}";
		}
}