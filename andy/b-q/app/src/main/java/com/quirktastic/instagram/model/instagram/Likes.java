package com.quirktastic.instagram.model.instagram;


import com.google.gson.annotations.SerializedName;


public class Likes{

	@SerializedName("count")
	private int count;

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	@Override
 	public String toString(){
		return 
			"Likes{" + 
			"count = '" + count + '\'' + 
			"}";
		}
}