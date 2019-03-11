package com.quirktastic.dashboard.profile.model.preferences;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class USERPREFERENCESItem{

	@SerializedName("attending_my_event")
	private String attendingMyEvent;

	@SerializedName("gender")
	private List<GenderItem> gender;

	@SerializedName("ethnicity")
	private List<EthnicityItem> ethnicity;

	@SerializedName("social_life")
	private List<SocialLifeItem> socialLife;

	@SerializedName("friends_location")
	private String friendsLocation;

	@SerializedName("friends_age")
	private String friendsAge;

	public String getIsEmailNotification() {
		return isEmailNotification;
	}

	public void setIsEmailNotification(String isEmailNotification) {
		this.isEmailNotification = isEmailNotification;
	}

	public String getIsPushNotification() {
		return isPushNotification;
	}

	public void setIsPushNotification(String isPushNotification) {
		this.isPushNotification = isPushNotification;
	}

	public String getIsMobileNotification() {
		return isMobileNotification;
	}

	public void setIsMobileNotification(String isMobileNotification) {
		this.isMobileNotification = isMobileNotification;
	}

	@SerializedName("is_email_notification")
	private String isEmailNotification;

	@SerializedName("is_push_notification")
	private String isPushNotification;

	@SerializedName("is_mobile_notification")
	private String isMobileNotification;

	@SerializedName("interests")
	private List<InterestsItem> interests;

	public void setAttendingMyEvent(String attendingMyEvent){
		this.attendingMyEvent = attendingMyEvent;
	}

	public String getAttendingMyEvent(){
		return attendingMyEvent;
	}

	public void setGender(List<GenderItem> gender){
		this.gender = gender;
	}

	public List<GenderItem> getGender(){
		return gender;
	}

	public void setEthnicity(List<EthnicityItem> ethnicity){
		this.ethnicity = ethnicity;
	}

	public List<EthnicityItem> getEthnicity(){
		return ethnicity;
	}

	public void setSocialLife(List<SocialLifeItem> socialLife){
		this.socialLife = socialLife;
	}

	public List<SocialLifeItem> getSocialLife(){
		return socialLife;
	}

	public void setFriendsLocation(String friendsLocation){
		this.friendsLocation = friendsLocation;
	}

	public String getFriendsLocation(){
		return friendsLocation;
	}

	public void setFriendsAge(String friendsAge){
		this.friendsAge = friendsAge;
	}

	public String getFriendsAge(){
		return friendsAge;
	}

	public void setInterests(List<InterestsItem> interests){
		this.interests = interests;
	}

	public List<InterestsItem> getInterests(){
		return interests;
	}

	@Override
 	public String toString(){
		return 
			"USERPREFERENCESItem{" + 
			"attending_my_event = '" + attendingMyEvent + '\'' + 
			",gender = '" + gender + '\'' + 
			",ethnicity = '" + ethnicity + '\'' + 
			",social_life = '" + socialLife + '\'' + 
			",friends_location = '" + friendsLocation + '\'' + 
			",friends_age = '" + friendsAge + '\'' + 
			",interests = '" + interests + '\'' + 
			"}";
		}
}