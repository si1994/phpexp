package com.quirktastic.instagram.model.instagram;


import com.google.gson.annotations.SerializedName;


public class Caption{

	@SerializedName("created_time")
	private String createdTime;

	@SerializedName("from")
	private From from;

	@SerializedName("id")
	private String id;

	@SerializedName("text")
	private String text;

	public void setCreatedTime(String createdTime){
		this.createdTime = createdTime;
	}

	public String getCreatedTime(){
		return createdTime;
	}

	public void setFrom(From from){
		this.from = from;
	}

	public From getFrom(){
		return from;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	@Override
 	public String toString(){
		return 
			"Caption{" + 
			"created_time = '" + createdTime + '\'' + 
			",from = '" + from + '\'' + 
			",id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}