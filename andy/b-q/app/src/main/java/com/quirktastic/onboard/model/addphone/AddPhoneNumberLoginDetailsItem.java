package com.quirktastic.onboard.model.addphone;

import com.google.gson.annotations.SerializedName;

public class AddPhoneNumberLoginDetailsItem {

	@SerializedName("email_id")
	private String emailId;

	@SerializedName("gender")
	private String gender;

	@SerializedName("is_verify_phone_number")
	private String isVerifyPhoneNumber;

	@SerializedName("date_of_birth")
	private String dateOfBirth;

	@SerializedName("device_token")
	private String deviceToken;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("device_type")
	private String deviceType;

	@SerializedName("id")
	private String id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("facebook_id")
	private String facebookId;

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setIsVerifyPhoneNumber(String isVerifyPhoneNumber){
		this.isVerifyPhoneNumber = isVerifyPhoneNumber;
	}

	public String getIsVerifyPhoneNumber(){
		return isVerifyPhoneNumber;
	}

	public void setDateOfBirth(String dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfBirth(){
		return dateOfBirth;
	}

	public void setDeviceToken(String deviceToken){
		this.deviceToken = deviceToken;
	}

	public String getDeviceToken(){
		return deviceToken;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}

	public String getDeviceType(){
		return deviceType;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setFacebookId(String facebookId){
		this.facebookId = facebookId;
	}

	public String getFacebookId(){
		return facebookId;
	}

}