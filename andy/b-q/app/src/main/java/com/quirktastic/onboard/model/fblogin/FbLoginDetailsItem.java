package com.quirktastic.onboard.model.fblogin;

import com.google.gson.annotations.SerializedName;

public class FbLoginDetailsItem {

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("gender")
    private String gender;

    @SerializedName("is_verify_phone_number")
    private String isVerifyPhoneNumber;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("profile_pic")
    private String profilePic;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("device_type")
    private String deviceType;

    @SerializedName("facebook_id")
    private String facebookId;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("state_name")
    private String stateName;

    @SerializedName("about_us")
    private String aboutUs;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("id")
    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("age")
    private String age;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("state_short_name")
    private String stateShortName;

    @SerializedName("profile_completion")
    private String profileCompletion;


    @SerializedName("is_photo_uploaded")
    private String isPhotoUploaded;


    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setIsVerifyPhoneNumber(String isVerifyPhoneNumber) {
        this.isVerifyPhoneNumber = isVerifyPhoneNumber;
    }

    public String getIsVerifyPhoneNumber() {
        return isVerifyPhoneNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setStateShortName(String stateShortName) {
        this.stateShortName = stateShortName;
    }

    public String getStateShortName() {
        return stateShortName;
    }

    public String getProfileCompletion() {
        return profileCompletion;
    }

    public String getIsPhotoUploaded() {
        return isPhotoUploaded;
    }

    public void setIsPhotoUploaded(String isPhotoUploaded) {
        this.isPhotoUploaded = isPhotoUploaded;
    }

    public void setProfileCompletion(String profileCompletion) {
        this.profileCompletion = profileCompletion;
    }

    @Override
    public String toString() {
        return
                "LoginDetailsItem{" +
                        "email_id = '" + emailId + '\'' +
                        ",gender = '" + gender + '\'' +
                        ",is_verify_phone_number = '" + isVerifyPhoneNumber + '\'' +
                        ",date_of_birth = '" + dateOfBirth + '\'' +
                        ",latitude = '" + latitude + '\'' +
                        ",profile_pic = '" + profilePic + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",device_type = '" + deviceType + '\'' +
                        ",facebook_id = '" + facebookId + '\'' +
                        ",city_name = '" + cityName + '\'' +
                        ",state_name = '" + stateName + '\'' +
                        ",about_us = '" + aboutUs + '\'' +
                        ",device_token = '" + deviceToken + '\'' +
                        ",phone_number = '" + phoneNumber + '\'' +
                        ",id = '" + id + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",age = '" + age + '\'' +
                        ",longitude = '" + longitude + '\'' +
                        ",state_short_name = '" + stateShortName + '\'' +
                        "}";
    }

	/*@SerializedName("email_id")
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

	@Override
 	public String toString(){
		return
			"FbLoginDetailsItem{" +
			"email_id = '" + emailId + '\'' +
			",gender = '" + gender + '\'' +
			",is_verify_phone_number = '" + isVerifyPhoneNumber + '\'' +
			",date_of_birth = '" + dateOfBirth + '\'' +
			",device_token = '" + deviceToken + '\'' +
			",last_name = '" + lastName + '\'' +
			",phone_number = '" + phoneNumber + '\'' +
			",device_type = '" + deviceType + '\'' +
			",id = '" + id + '\'' +
			",first_name = '" + firstName + '\'' +
			",facebook_id = '" + facebookId + '\'' +
			"}";
		}*/
}