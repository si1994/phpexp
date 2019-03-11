package com.quirktastic.network;

public class WSUrl {

    public static final String BASE_URL = "https://www.app.quirktastic.co/beta/ws/";  // Beta server
    //public static final String BASE_URL = "http://topsdemo.co.in/webservices/quirktastic/v2.0/ws/";  // Tops server
    // public static final String BASE_URL = "http://192.168.4.56/web_services/quirktastic/ws/";  // Local server
    //public static final String BASE_URL = "https://www.app.quirktastic.co/ws/";   // Live server
    //public static final String BASE_URL = "http://topsdemo.co.in/webservices/quirktastic/v1.0/ws/";  // Tops server

    public static final String POST_SOCIAL_LOGIN = BASE_URL + "social_login";
    public static final String POST_INTEREST_LIST = BASE_URL + "interest_list";
    public static final String POST_EVENTS_LIST = BASE_URL + "events_list";
    public static final String POST_IDENTITY_LIST = BASE_URL + "identity_list";
    public static final String POST_SOCIAL_LIFE_LIST = BASE_URL + "social_life_list";
    public static final String POST_EVENTS_DETAIL = BASE_URL + "events_detail";
    public static final String GET_PROFILE_DETAILS = BASE_URL + "profile_details"; // append userId after /id/
    public static final String POST_UPDATE_BASIC_DETAILS = BASE_URL + "update_basic_details";
    public static final String POST_UPDATE_INTERESTS_TEXT = BASE_URL + "update_interests_text";
    public static final String POST_UPDATE_PERSONALITY_TEXT = BASE_URL + "update_personality_text";
    public static final String POST_UPDATE_GENDER = BASE_URL + "update_gender"; // update_gender (identity)
    public static final String POST_UPDATE_IDENTITY = BASE_URL + "update_identity";
    public static final String POST_UPDATE_PHOTOS = BASE_URL + "update_photos";
    public static final String POST_USER_SIGNUP_PHONE = BASE_URL + "user_signup_phone";
    public static final String POST_VERIFICATION_CODE = BASE_URL + "verification_code";
    public static final String POST_UPDATE_INTEREST = BASE_URL + "update_interest";
    public static final String POST_UPDATE_SOCIAL_LIFE = BASE_URL + "update_social_life";
    public static final String POST_USER_LIST = BASE_URL + "user_list";
    public static final String POST_USER_NOT_INTERESTED = BASE_URL + "user_not_interested";
    public static final String POST_SEND_FRIEND_REQUEST = BASE_URL + "send_friend_request";
    public static final String GET_FRND_LIST = BASE_URL + "user_friend_list?user_id=";
    public static final String GET_FRND_MSG_LIST = BASE_URL + "user_friend_msg_list?user_id=";
    public static final String POST_FRIEND_REQUEST_STATUS = BASE_URL + "change_friend_request_status";
    public static final String GET_USER_CHAT_MSG_LIST = BASE_URL + "user_msg_list?from_user_id=";
    public static final String TO_USER_CHAT_MSG_LIST = "&to_user_id=";
    public static final String POST_SEND_MASSAGE = BASE_URL + "send_massage";
    public static final String GET_PREFERENCES = BASE_URL + "get_preferences/id/";
    public static final String POST_SET_PREFERENCES = BASE_URL + "set_preferences";
    public static final String POST_UPDATE_EVENT = BASE_URL + "update_event";
    public static final String GET_USER_PENDING_LIST = BASE_URL + "user_pending_list?user_id=";
    public static final String LAST_ID = "&last_id=";
    public static final String IS_NEW = "&is_new=";
    public static final String POST_UPDATE_USER_PROFILE = BASE_URL + "update_user_profile";
    public static final String POST_DELETE_USER_IMAGE = BASE_URL + "delete_img";
    public static final String POST_CHECK_PROFILE_COMPLETION = BASE_URL + "check_profile_completion";
    public static final String POST_MAKE_FRIEND = BASE_URL + "make_friend";
    public static final String POST_UPDATE_LOCATION = BASE_URL + "update_location";
    public static final String POST_LOGOUT = BASE_URL + "logout";
    public static final String CHANGE_PHOTOS_ORDER = BASE_URL + "change_photos_order";


    public static final String INSTA_AUTHURL = "https://api.instagram.com/oauth/authorize/";
    public static final String INSTA_TOKENURL = "https://api.instagram.com/oauth/access_token";
    public static final String INSTA_APIURL = "https://api.instagram.com/v1";
    public static final String INSTA_CALLBACKURL = "http://www.quirktastic.co/";


}
