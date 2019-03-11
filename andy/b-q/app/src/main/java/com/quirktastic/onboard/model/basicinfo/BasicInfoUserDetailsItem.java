package com.quirktastic.onboard.model.basicinfo;

import com.google.gson.annotations.SerializedName;

public class BasicInfoUserDetailsItem {

	@SerializedName("email_id")
	private String emailId;

	@SerializedName("gender")
	private String gender;

	@SerializedName("identity_id")
	private String identityId;

	@SerializedName("is_verify_phone_number")
	private String isVerifyPhoneNumber;

	@SerializedName("date_of_birth")
	private String dateOfBirth;

	@SerializedName("personality_text")
	private String personalityText;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("social_user_type")
	private String socialUserType;

	@SerializedName("about_us")
	private String aboutUs;

	@SerializedName("interests_text")
	private String interestsText;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private String id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("status")
	private String status;

	@SerializedName("profile_pic")
	private String profilePic;

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

	public void setIdentityId(String identityId){
		this.identityId = identityId;
	}

	public String getIdentityId(){
		return identityId;
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

	public void setPersonalityText(String personalityText){
		this.personalityText = personalityText;
	}

	public String getPersonalityText(){
		return personalityText;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setSocialUserType(String socialUserType){
		this.socialUserType = socialUserType;
	}

	public String getSocialUserType(){
		return socialUserType;
	}

	public void setAboutUs(String aboutUs){
		this.aboutUs = aboutUs;
	}

	public String getAboutUs(){
		return aboutUs;
	}

	public void setInterestsText(String interestsText){
		this.interestsText = interestsText;
	}

	public String getInterestsText(){
		return interestsText;
	}

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

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
 	public String toString(){
		return 
			"BasicInfoUserDetailsItem{" +
			"email_id = '" + emailId + '\'' + 
			",gender = '" + gender + '\'' + 
			",identity_id = '" + identityId + '\'' + 
			",is_verify_phone_number = '" + isVerifyPhoneNumber + '\'' + 
			",date_of_birth = '" + dateOfBirth + '\'' + 
			",personality_text = '" + personalityText + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",social_user_type = '" + socialUserType + '\'' + 
			",about_us = '" + aboutUs + '\'' + 
			",interests_text = '" + interestsText + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}