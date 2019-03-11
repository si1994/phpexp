package com.quirktastic.onboard.model.addphone;

import com.google.gson.annotations.SerializedName;

public class AddPhoneNumberUserDetails {

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private String id;

	@SerializedName("token")
	private String token;

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}