package com.quirktastic.onboard.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.model.basicinfo.BasicInfoResponse;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.locationrequest.GetLatLng;
import com.quirktastic.utility.locationrequest.GpsTracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;
import static com.quirktastic.utility.AppContants.GOOGLE_MAP_API_KEY;

public class ContinueActivity extends AppCompatActivity {

    @BindView(R.id.llInterestBottom)
    LinearLayout llInterestBottom;

    private double latitude = 0f;
    private double longitude = 0f;
    private String TAG = getClass().getSimpleName();
    private String cityName = "";
    private String stateName = "";
    private String stateShortName = "";
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);
        ButterKnife.bind(this);
        //checkPermission();
    }

    @OnClick({R.id.llInterestBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.llInterestBottom:
                //   updateBasicInfoAPI();
                redirectToDashBoard();
                break;
        }
    }

    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                getLocation();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(getString(R.string.permission_denied))
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    private void redirectToDashBoard() {
        Intent intent = new Intent(ContinueActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    // calling ws for updating all basic info and lat of long
    private void updateBasicInfoAPI() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(ContinueActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.FIRST_NAME, Prefs.getString(ContinueActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, ""));
        map.put(WSKey.LAST_NAME, Prefs.getString(ContinueActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, ""));
        map.put(WSKey.EMAIL_ID, Prefs.getString(ContinueActivity.this, PrefsKey.BASIC_INFO_EMAIL, ""));
        map.put(WSKey.DATE_OF_BIRTH, Prefs.getString(ContinueActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, ""));
        map.put(WSKey.LATITUDE, String.valueOf(latitude));
        map.put(WSKey.LONGITUDE, String.valueOf(longitude));

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


        new CallNetworkRequest().postResponse(ContinueActivity.this, true, "update basic info", AUTH_VALUE, WSUrl.POST_UPDATE_BASIC_DETAILS, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            BasicInfoResponse basicInfoResponse = new Gson().fromJson(response, BasicInfoResponse.class);
                            if (basicInfoResponse.isFLAG()) {

//                            Toast.show(ContinueActivity.this, basicInfoResponse.getMESSAGE());
                                redirectToDashBoard();
                            } else {
                                Toast.show(ContinueActivity.this, basicInfoResponse.getMESSAGE());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(ContinueActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    // getting current location
    private void getLocation() {
        gpsTracker = new GpsTracker(ContinueActivity.this, true);
        gpsTracker.getLatLng(new GetLatLng() {
            @Override
            public void getLatLngFromInterface(double lat, double lng, boolean gotLatLng) {
                if (gotLatLng) {
                    latitude = lat;
                    longitude = lng;
                    gpsTracker.stopLocationUpdates();
                }

                wsGetCityState(latitude, longitude);
            }
        });
    }


    // call google ws for get city and state name from lat and long
    private void wsGetCityState(double latitude, double longitude) {

        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=false&key=" + GOOGLE_MAP_API_KEY;

        new CallNetworkRequest().getResponse(ContinueActivity.this, true, "update Eevent", AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            JSONObject responseObject = new JSONObject(response);

                            if (responseObject != null) {
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

                                        Prefs.setString(ContinueActivity.this, PrefsKey.CITY_NAME, cityName);
                                        Prefs.setString(ContinueActivity.this, PrefsKey.STATE_NAME, stateName);
                                        Prefs.setString(ContinueActivity.this, PrefsKey.STATE_SHORT_NAME, stateShortName);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(ContinueActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        wsGetCityState(latitude, longitude);
    }
}
