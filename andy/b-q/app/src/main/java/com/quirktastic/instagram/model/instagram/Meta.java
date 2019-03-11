package com.quirktastic.instagram.model.instagram;


import com.google.gson.annotations.SerializedName;


public class Meta{

	@SerializedName("code")
	private int code;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	@Override
 	public String toString(){
		return 
			"Meta{" + 
			"code = '" + code + '\'' + 
			"}";
		}
}