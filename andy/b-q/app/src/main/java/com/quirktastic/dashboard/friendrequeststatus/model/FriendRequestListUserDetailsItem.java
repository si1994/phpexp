package com.quirktastic.dashboard.friendrequeststatus.model;

import com.google.gson.annotations.SerializedName;

public class FriendRequestListUserDetailsItem {

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("state_name")
	private String stateName;

	@SerializedName("about_us")
	private String aboutUs;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("profile_pic")
	private String profilePic;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("age")
	private String age;

	@SerializedName("from_user_id")
	private String fromUserId;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("state_short_name")
	private String stateShortName;

	public String getIntroYourSelfToFriend() {
		return introYourSelfToFriend;
	}

	public void setIntroYourSelfToFriend(String introYourSelfToFriend) {
		this.introYourSelfToFriend = introYourSelfToFriend;
	}

	@SerializedName("intro_yourself_to_friend")
	private String introYourSelfToFriend;

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setAboutUs(String aboutUs){
		this.aboutUs = aboutUs;
	}

	public String getAboutUs(){
		return aboutUs;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
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

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	public void setFromUserId(String fromUserId){
		this.fromUserId = fromUserId;
	}

	public String getFromUserId(){
		return fromUserId;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setStateShortName(String stateShortName){
		this.stateShortName = stateShortName;
	}

	public String getStateShortName(){
		return stateShortName;
	}

}