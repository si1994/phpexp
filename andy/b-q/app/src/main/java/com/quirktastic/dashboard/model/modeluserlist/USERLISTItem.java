package com.quirktastic.dashboard.model.modeluserlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class USERLISTItem implements Parcelable {

	@SerializedName("email_id")
	private String emailId;

	@SerializedName("gender_text")
	private String genderText;

	@SerializedName("gender")
	private String gender;

	@SerializedName("distance")
	private String distance;

	@SerializedName("date_of_birth")
	private String dateOfBirth;

	@SerializedName("personality_text")
	private String personalityText;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("interest_name")
	private List<Object> interestName;

	@SerializedName("photos")
	private List<Object> photos;

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("about_us")
	private String aboutUs;

	@SerializedName("state_name")
	private String stateName;

	@SerializedName("social_life")
	private SocialLife socialLife;

	@SerializedName("identity_description")
	private Object identityDescription;

	@SerializedName("id")
	private String id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("identity_id")
	private String identityId;

	@SerializedName("is_verify_phone_number")
	private String isVerifyPhoneNumber;

	@SerializedName("identity_name")
	private Object identityName;

	@SerializedName("profile_pic")
	private String profilePic;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("social_user_type")
	private String socialUserType;

	@SerializedName("header_bio")
	private String headerBio;

	@SerializedName("interests_text")
	private String interestsText;

	@SerializedName("event_name")
	private List<Object> eventName;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("age")
	private String age;

	@SerializedName("status")
	private String status;

	@SerializedName("state_short_name")
	private String stateShortName;

	@SerializedName("instagram_id")
	private String instagramId;

	@SerializedName("instagram_access_token")
	private String instagramAccessToken;

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setGenderText(String genderText){
		this.genderText = genderText;
	}

	public String getGenderText(){
		return genderText;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
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

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setInterestName(List<Object> interestName){
		this.interestName = interestName;
	}

	public List<Object> getInterestName(){
		return interestName;
	}

	public void setPhotos(List<Object> photos){
		this.photos = photos;
	}

	public List<Object> getPhotos(){
		return photos;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setAboutUs(String aboutUs){
		this.aboutUs = aboutUs;
	}

	public String getAboutUs(){
		return aboutUs;
	}

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setSocialLife(SocialLife socialLife){
		this.socialLife = socialLife;
	}

	public SocialLife getSocialLife(){
		return socialLife;
	}

	public void setIdentityDescription(Object identityDescription){
		this.identityDescription = identityDescription;
	}

	public Object getIdentityDescription(){
		return identityDescription;
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

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
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

	public void setIdentityName(Object identityName){
		this.identityName = identityName;
	}

	public Object getIdentityName(){
		return identityName;
	}

	public void setProfilePic(String profilePic){
		this.profilePic = profilePic;
	}

	public String getProfilePic(){
		return profilePic;
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

	public void setHeaderBio(String headerBio){
		this.headerBio = headerBio;
	}

	public String getHeaderBio(){
		return headerBio;
	}

	public void setInterestsText(String interestsText){
		this.interestsText = interestsText;
	}

	public String getInterestsText(){
		return interestsText;
	}

	public void setEventName(List<Object> eventName){
		this.eventName = eventName;
	}

	public List<Object> getEventName(){
		return eventName;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setStateShortName(String stateShortName){
		this.stateShortName = stateShortName;
	}

	public String getStateShortName(){
		return stateShortName;
	}

	public String getInstagramId() {
		return instagramId;
	}

	public void setInstagramId(String instagramId) {
		this.instagramId = instagramId;
	}

	public String getInstagramAccessToken() {
		return instagramAccessToken;
	}

	public void setInstagramAccessToken(String instagramAccessToken) {
		this.instagramAccessToken = instagramAccessToken;
	}

	@Override
 	public String toString(){
		return 
			"USERLISTItem{" + 
			"email_id = '" + emailId + '\'' + 
			",gender_text = '" + genderText + '\'' + 
			",gender = '" + gender + '\'' + 
			",distance = '" + distance + '\'' + 
			",date_of_birth = '" + dateOfBirth + '\'' + 
			",personality_text = '" + personalityText + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",interest_name = '" + interestName + '\'' + 
			",photos = '" + photos + '\'' + 
			",city_name = '" + cityName + '\'' + 
			",about_us = '" + aboutUs + '\'' + 
			",state_name = '" + stateName + '\'' + 
			",social_life = '" + socialLife + '\'' + 
			",identity_description = '" + identityDescription + '\'' + 
			",id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",identity_id = '" + identityId + '\'' + 
			",is_verify_phone_number = '" + isVerifyPhoneNumber + '\'' + 
			",identity_name = '" + identityName + '\'' + 
			",profile_pic = '" + profilePic + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",social_user_type = '" + socialUserType + '\'' + 
			",header_bio = '" + headerBio + '\'' + 
			",interests_text = '" + interestsText + '\'' + 
			",event_name = '" + eventName + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",age = '" + age + '\'' + 
			",status = '" + status + '\'' + 
			",state_short_name = '" + stateShortName + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
}