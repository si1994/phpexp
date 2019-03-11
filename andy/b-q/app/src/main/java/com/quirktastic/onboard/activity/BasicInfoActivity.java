package com.quirktastic.onboard.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.model.basicinfo.BasicInfoResponse;
import com.quirktastic.onboard.model.basicinfo.BasicInfoUserDetailsItem;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;
import static com.quirktastic.utility.AppContants.GOOGLE_MAP_API_KEY;

public class BasicInfoActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llBottomBtnNextAddPhotos;
    EditText edtFirstName, edtLastName, edtDOB, edtEmail, edtZipCode;
    private int age = 0;
    private String cityName = "";
    private String stateName = "";
    private String stateShortName = "";
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        hideSoftKeyboard();
        viewBinding();
        init();
        addListner();

    }

    private void viewBinding() {
        llBottomBtnNextAddPhotos = (LinearLayout) findViewById(R.id.llBottomBtnNextAddPhotos);
        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtDOB = (EditText) findViewById(R.id.edtDOB);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtZipCode = (EditText) findViewById(R.id.edtZipCode);


        edtLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    edtDOB.requestFocus();
                    return true;
                }
                return false;
            }
        });
        edtDOB.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    edtEmail.requestFocus();
                    return true;
                }
                return false;
            }
        });

        edtDOB.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private boolean deletingHyphen;
            private int hyphenStart;
            private boolean deletingBackward;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (isFormatting)
                    return;

                // Make sure user is deleting one char, without a selection
                final int selStart = Selection.getSelectionStart(charSequence);
                final int selEnd = Selection.getSelectionEnd(charSequence);
                if (charSequence.length() > 1 // Can delete another character
                        && count == 1 // Deleting only one character
                        && after == 0 // Deleting
                        && charSequence.charAt(start) == '/' // a hyphen
                        && selStart == selEnd) { // no selection
                    deletingHyphen = true;
                    hyphenStart = start;
                    // Check if the user is deleting forward or backward
                    if (selStart == start + 1) {
                        deletingBackward = true;
                    } else {
                        deletingBackward = false;
                    }
                } else {
                    deletingHyphen = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable text) {
                if (isFormatting)
                    return;

                isFormatting = true;

                // If deleting hyphen, also delete character before or after it
                if (deletingHyphen && hyphenStart > 0) {
                    if (deletingBackward) {
                        if (hyphenStart - 1 < text.length()) {
                            text.delete(hyphenStart - 1, hyphenStart);
                        }
                    } else if (hyphenStart < text.length()) {
                        text.delete(hyphenStart, hyphenStart + 1);
                    }
                }
                if (text.length() == 2 || text.length() == 5) {
                    text.append('/');
                }

                isFormatting = false;


            }
        });
    }

    private void init() {

        if (!Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, "").equals("")) {
            edtFirstName.setText(Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, ""));

        }

        if (!Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, "").equals("")) {
            edtLastName.setText(Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, ""));

        }

        if (!Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, "").equals("")) {
            edtDOB.setText(Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, ""));

        }

        if (!Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_EMAIL, "").equals("")) {
            edtEmail.setText(Prefs.getString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_EMAIL, ""));
        }

    }


    private void addListner() {
        llBottomBtnNextAddPhotos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llBottomBtnNextAddPhotos:
                validInfo();
                break;
        }

    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    // calling ws for sending basic info to server
    private void basicInfoWs() {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(BasicInfoActivity.this, PrefsKey.ADD_PNM_OTP_USER_ID, ""));
        map.put(WSKey.FIRST_NAME, edtFirstName.getText().toString());
        map.put(WSKey.LAST_NAME, edtLastName.getText().toString());
        map.put(WSKey.DATE_OF_BIRTH, Util.getYYYYMMDDfromMDY(edtDOB.getText().toString()));
        map.put(WSKey.EMAIL_ID, edtEmail.getText().toString());
        map.put(WSKey.ZIPCODE, edtZipCode.getText().toString());
        if (cityName != null) {
            map.put(WSKey.CITY_NAME, cityName);
        } else {
            map.put(WSKey.CITY_NAME, "");
        }

        if (stateName != null) {
            map.put(WSKey.STATE_NAME, stateName);
        } else {
            map.put(WSKey.STATE_NAME, "");
        }

        if (stateShortName != null) {
            map.put(WSKey.STATE_SHORT_NAME, stateShortName);
        } else {
            map.put(WSKey.STATE_SHORT_NAME, "");
        }

        new CallNetworkRequest().postResponse(BasicInfoActivity.this, true, "update Basic Info", AUTH_VALUE, WSUrl.POST_UPDATE_BASIC_DETAILS, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            BasicInfoResponse basicInfoResponse = new Gson().fromJson(response, BasicInfoResponse.class);
                            if (basicInfoResponse.isFLAG()) {

                                if (basicInfoResponse.getUserDetails() != null && basicInfoResponse.getUserDetails().size() > 0) {

                                    BasicInfoUserDetailsItem basicInfoUserDetailsItem = basicInfoResponse.getUserDetails().get(0);

                                    if (basicInfoUserDetailsItem.getFirstName() != null && basicInfoUserDetailsItem.getFirstName().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, basicInfoUserDetailsItem.getFirstName());
                                    }

                                    if (basicInfoUserDetailsItem.getLastName() != null && basicInfoUserDetailsItem.getLastName().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, basicInfoUserDetailsItem.getLastName());
                                    }

                                    if (basicInfoUserDetailsItem.getDateOfBirth() != null && basicInfoUserDetailsItem.getDateOfBirth().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, basicInfoUserDetailsItem.getDateOfBirth());
                                    }

                                    if (basicInfoUserDetailsItem.getPhoneNumber() != null && basicInfoUserDetailsItem.getPhoneNumber().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_PHONE_NUMBER, basicInfoUserDetailsItem.getPhoneNumber());
                                    }

                                    if (basicInfoUserDetailsItem.getEmailId() != null && basicInfoUserDetailsItem.getEmailId().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.BASIC_INFO_EMAIL, basicInfoUserDetailsItem.getEmailId());
                                    }

                                    if (basicInfoUserDetailsItem.getProfilePic() != null && basicInfoUserDetailsItem.getProfilePic().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.PROFILE_PIC, basicInfoUserDetailsItem.getProfilePic());
                                    }


                                    if (basicInfoUserDetailsItem.getProfilePic() != null && basicInfoUserDetailsItem.getProfilePic().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.CITY_NAME, cityName);
                                    }

                                    if (basicInfoUserDetailsItem.getProfilePic() != null && basicInfoUserDetailsItem.getProfilePic().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.STATE_NAME, stateName);
                                    }

                                    if (basicInfoUserDetailsItem.getProfilePic() != null && basicInfoUserDetailsItem.getProfilePic().trim().length() > 0) {
                                        Prefs.setString(BasicInfoActivity.this, PrefsKey.STATE_SHORT_NAME, stateShortName);
                                    }
                                }

//                            Toast.show(BasicInfoActivity.this, basicInfoResponse.getMESSAGE());
                                startActivity(new Intent(BasicInfoActivity.this, AddPhotosActivity.class));
                            } else {
                                Toast.show(BasicInfoActivity.this, basicInfoResponse.getMESSAGE());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(BasicInfoActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    private void validInfo() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date birthDate = sdf.parse(edtDOB.getText().toString());
            age = Util.getAge(birthDate);
            System.out.println("age------------>" + age);
/*
            String userAge = Util.calculateAge(new SimpleDateFormat("MM-dd-yyyy").parse(edtDOB.getText().toString().trim()));
*/
        } catch (ParseException e) {
            e.printStackTrace();
        }

      /*   else if (Util.calculateAge( edtDOB.getText().toString().trim(),"MM-dd-yyyy") ){
            Toast.show( BasicInfoActivity.this, "You should be over 18 years old." );
        }*/

        if (edtFirstName.getText().toString().trim().equals("")) {
            Toast.show(BasicInfoActivity.this, "Please enter the first name");
        } else if (!Util.isValidWord(edtFirstName.getText().toString())) {
            Toast.show(BasicInfoActivity.this, "Please enter the valid first name");
        } else if (edtLastName.getText().toString().trim().equals("")) {
            Toast.show(BasicInfoActivity.this, "Please enter the last name");
        } else if (!Util.isValidWord(edtLastName.getText().toString())) {
            Toast.show(BasicInfoActivity.this, "Please enter the valid last name");
        } else if (edtDOB.getText().toString().trim().equals("")) {
            Toast.show(BasicInfoActivity.this, "Please enter the Date of birth");
        } else if (!Util.isDateValid(edtDOB.getText().toString().trim(), "MM/dd/yyyy")) {
            Toast.show(BasicInfoActivity.this, "Please enter the valid Date of birth");
        } else if (age < 18) {
            Toast.show(BasicInfoActivity.this, "To use this app.You must 18 years old");
        } else if (edtEmail.getText().toString().trim().equals("")) {
            Toast.show(BasicInfoActivity.this, "Please enter the email");
        } else if (!Util.isValidEmail(edtEmail.getText().toString())) {
            Toast.show(BasicInfoActivity.this, "Please enter the valid email");
        } else if (edtZipCode.getText().toString().trim().equals("")) {
            Toast.show(BasicInfoActivity.this, "Please enter the zip code");
        } else if (edtZipCode.getText().toString().trim().length() < 5) {
            Toast.show(BasicInfoActivity.this, "Please enter the valid zip code");
        } else {
            wsGetCityState(edtZipCode.getText().toString());
        }
    }


    // call google ws for get city and state name from lat and long
    private void wsGetCityState(String zipcode) {

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&sensor=false&key=" + GOOGLE_MAP_API_KEY;

        new CallNetworkRequest().getResponse(BasicInfoActivity.this, true, "update Eevent", AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            JSONObject responseObject = new JSONObject(response);

                            if (responseObject != null) {
                                if (responseObject.has("status")) {

                                    if (responseObject.get("status").equals("OK")) {

                                        if (responseObject.has("results")) {


                                            JSONArray resultsArray = responseObject.getJSONArray("results");

                                            if (resultsArray.length() > 0) {

                                                JSONObject resultMainObject = resultsArray.getJSONObject(0);

                                                JSONArray addressComponentsArray = resultMainObject.getJSONArray("address_components");

                                                for (int i = 0; i < addressComponentsArray.length(); i++) {

                                                    JSONObject dataObject = addressComponentsArray.getJSONObject(i);

                                                    if (dataObject.getJSONArray("types").getString(0).equalsIgnoreCase("locality")) {
                                                        cityName = dataObject.getString("long_name");
                                                    }

                                                    if (dataObject.getJSONArray("types").getString(0).equalsIgnoreCase("administrative_area_level_1")) {
                                                        stateName = dataObject.getString("long_name");
                                                        stateShortName = dataObject.getString("short_name");
                                                    }
                                                }

                                                Prefs.setString(BasicInfoActivity.this, PrefsKey.CITY_NAME, cityName);
                                                Prefs.setString(BasicInfoActivity.this, PrefsKey.STATE_NAME, stateName);
                                                Prefs.setString(BasicInfoActivity.this, PrefsKey.STATE_SHORT_NAME, stateShortName);

                                                basicInfoWs();
                                            } else {
                                                Toast.show(BasicInfoActivity.this, "Please enter valid zipcode");
                                            }
                                        } else {
                                            Toast.show(BasicInfoActivity.this, "Please enter valid zipcode");
                                        }
                                    } else {
                                        Toast.show(BasicInfoActivity.this, "Please enter valid zipcode");
                                    }
                                } else {
                                    Toast.show(BasicInfoActivity.this, "Please enter valid zipcode");
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(BasicInfoActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(BasicInfoActivity.this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }


}
