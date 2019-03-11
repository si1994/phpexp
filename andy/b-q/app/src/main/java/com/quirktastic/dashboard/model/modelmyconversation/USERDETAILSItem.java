package com.quirktastic.dashboard.model.modelmyconversation;

import com.google.gson.annotations.SerializedName;

public class USERDETAILSItem {

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("about_us")
    private String aboutUs;

    @SerializedName("state_name")
    private String stateName;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("profile_pic")
    private String profilePic;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("last_message")
    private String lastMessage;

    @SerializedName("last_message_url")
    private String lastMessageUrl;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("age")
    private String age;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("state_short_name")
    private String stateShortName;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("unread_count")
    private String unreadCount;

    @SerializedName("is_blocked")
    private String isBlocked;


    @SerializedName("is_unfriend")
    private String isUnfriend;

    @SerializedName("block_by")
    private String blockBy;


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
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

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getIsUnfriend() {
        return isUnfriend;
    }

    public void setIsUnfriend(String isUnfriend) {
        this.isUnfriend = isUnfriend;
    }

    public String getBlockBy() {
        return blockBy;
    }

    public void setBlockBy(String blockBy) {
        this.blockBy = blockBy;
    }

    public String getLastMessageUrl() {
        return lastMessageUrl;
    }

    public void setLastMessageUrl(String lastMessageUrl) {
        this.lastMessageUrl = lastMessageUrl;
    }
}