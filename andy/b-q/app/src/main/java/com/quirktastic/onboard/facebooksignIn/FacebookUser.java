package com.quirktastic.onboard.facebooksignIn;

import org.json.JSONObject;

/**
 * Created by multidots on 6/16/2016.
 * This class represents facebook user profile.
 */
public class FacebookUser {
    //.
    public String name;

    public String email;

    public String facebookID;

    public String gender;

    public String dob;

    public String about;

    public String bio;

    public String coverPicUrl;

    public String profilePic;

    public String first_name;

    public String last_name;

    /**
     * JSON response received. If you want to parse more fields.
     */
    public JSONObject response;

}
