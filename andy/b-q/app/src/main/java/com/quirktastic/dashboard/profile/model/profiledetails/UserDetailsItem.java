package com.quirktastic.dashboard.profile.model.profiledetails;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDetailsItem  implements Serializable {

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("gender")
    private String gender;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("personality_text")
    private String personalityText;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("device_type")
    private String deviceType;

    @SerializedName("photos")
    private ArrayList<PhotosItem> photos;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("about_us")
    private String aboutUs;

    @SerializedName("state_name")
    private String stateName;

    @SerializedName("interest")
    private ArrayList<InterestItem> interest;

    @SerializedName("social_life")
    private ArrayList<SocialLifeItem> socialLife;

    @SerializedName("identity")
    private ArrayList<IdentityItem> identity;

    @SerializedName("id")
    private String id;

    @SerializedName("event")
    private ArrayList<EventItem> event;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("social_user_type")
    private String socialUserType;

    @SerializedName("facebook_id")
    private String facebookId;

    @SerializedName("header_bio")
    private String headerBio;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("interests_text")
    private String interestsText;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("state_short_name")
    private String stateShortName;

    @SerializedName("status")
    private String status;

    @SerializedName("profile_completion")
    private String profileCompletion;

    @SerializedName("profile_pic")
    private String profilePic;



    @SerializedName("is_photo_uploaded")
    private String isPhotoUploaded;


    @SerializedName("zipcode")
    private String zipcode;


    @SerializedName("instagram_id")
    private String instagramId;

    @SerializedName("instagram_access_token")
    private String instagramAccessToken;


    public String getIsPhotoUploaded() {
        return isPhotoUploaded;
    }

    public void setIsPhotoUploaded(String isPhotoUploaded) {
        this.isPhotoUploaded = isPhotoUploaded;
    }

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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setPersonalityText(String personalityText) {
        this.personalityText = personalityText;
    }

    public String getPersonalityText() {
        return personalityText;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setPhotos(ArrayList<PhotosItem> photos) {
        this.photos = photos;
    }

    public ArrayList<PhotosItem> getPhotos() {
        return photos;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setInterest(ArrayList<InterestItem> interest) {
        this.interest = interest;
    }

    public ArrayList<InterestItem> getInterest() {
        return interest;
    }

    public void setSocialLife(ArrayList<SocialLifeItem> socialLife) {
        this.socialLife = socialLife;
    }

    public ArrayList<SocialLifeItem> getSocialLife() {
        return socialLife;
    }

    public void setIdentity(ArrayList<IdentityItem> identity) {
        this.identity = identity;
    }

    public ArrayList<IdentityItem> getIdentity() {
        return identity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEvent(ArrayList<EventItem> event) {
        this.event = event;
    }

    public ArrayList<EventItem> getEvent() {
        return event;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setSocialUserType(String socialUserType) {
        this.socialUserType = socialUserType;
    }

    public String getSocialUserType() {
        return socialUserType;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setHeaderBio(String headerBio) {
        this.headerBio = headerBio;
    }

    public String getHeaderBio() {
        return headerBio;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setInterestsText(String interestsText) {
        this.interestsText = interestsText;
    }

    public String getInterestsText() {
        return interestsText;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setStateShortName(String stateShortName) {
        this.stateShortName = stateShortName;
    }

    public String getStateShortName() {
        return stateShortName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getProfileCompletion() {
        return profileCompletion;
    }

    public void setProfileCompletion(String profileCompletion) {
        this.profileCompletion = profileCompletion;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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
    public String toString() {
        return
                "UserDetailsItem{" +
                        "email_id = '" + emailId + '\'' +
                        ",gender = '" + gender + '\'' +
                        ",date_of_birth = '" + dateOfBirth + '\'' +
                        ",personality_text = '" + personalityText + '\'' +
                        ",latitude = '" + latitude + '\'' +
                        ",device_type = '" + deviceType + '\'' +
                        ",photos = '" + photos + '\'' +
                        ",city_name = '" + cityName + '\'' +
                        ",about_us = '" + aboutUs + '\'' +
                        ",state_name = '" + stateName + '\'' +
                        ",interest = '" + interest + '\'' +
                        ",social_life = '" + socialLife + '\'' +
                        ",identity = '" + identity + '\'' +
                        ",id = '" + id + '\'' +
                        ",event = '" + event + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",longitude = '" + longitude + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",social_user_type = '" + socialUserType + '\'' +
                        ",facebook_id = '" + facebookId + '\'' +
                        ",header_bio = '" + headerBio + '\'' +
                        ",device_token = '" + deviceToken + '\'' +
                        ",interests_text = '" + interestsText + '\'' +
                        ",phone_number = '" + phoneNumber + '\'' +
                        ",created_date = '" + createdDate + '\'' +
                        ",state_short_name = '" + stateShortName + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}