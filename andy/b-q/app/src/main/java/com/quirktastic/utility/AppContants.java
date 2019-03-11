package com.quirktastic.utility;

import com.quirktastic.instagram.model.instagram.DataItem;
import com.quirktastic.onboard.model.addphotos.AddPhotoModel;

import java.util.ArrayList;
import java.util.List;

public class AppContants {

    public static final String AUTH_VALUE = "Basic YWRtaW46MTIzNA==";

    public static final String CONTECT_SUPPORT_EMAIL = "support@quirktastic.co";
    public static final String FAQ_LINK = "https://www.quirktastic.co/faqs";
    public static final String PRIVACY_POLICY = "http://www.quirktastic.co/privacy";
    public static final String TERMS = "http://www.quirktastic.co/terms";
    public static final String URL_SOCIAL = "http://news.quirktastic.co/post/category/read-all/";
    public static final String URL_EVENTS = "https://www.quirkcon.com/";

    public static final String GENDER_MAN = "1";
    public static final String GENDER_WOMAN = "2";
    public static final String GENDER_NON_BINARY = "3";

    public static final String GOOGLE_MAP_API_KEY = "AIzaSyDSQcTVK_OiEfPqSmcdDS-P8mjvJdyBJxE";

    public static String FCM_TOKEN = "";


    public static boolean IS_UPLOAD_CALLED = false;

    public static boolean IS_FROM_FILL_OUT = false;


    public static String  INSTA_CLIENT_ID = "6812109627d54788a44124e3224f7a94";
    public static String INSTA_CLIENT_SECRET = "4b1406d7723441ec913c91c1979344ef";



    public static List<DataItem> INSTA_FEED_LIST;
    public static int INSTA_CURRENT_PAGER_ITEM=0;

    public static boolean IS_SEND_FRIEND_REQUEST = true;

    public static ArrayList<AddPhotoModel> listAddPhoto = new ArrayList<>();


}
