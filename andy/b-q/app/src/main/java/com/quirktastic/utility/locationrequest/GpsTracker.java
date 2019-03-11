package com.quirktastic.utility.locationrequest;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.quirktastic.R;
import com.quirktastic.network.ProgressDialog;

import java.util.List;

public class GpsTracker {

    private  Activity activity;
    private ProgressDialog progressDialog;

    private  final int GPS_TRACKER_REQUEST_CHECK_SETTINGS = 112;
    private  LocationRequest mLocationRequest;
    private  GetLatLng interfaceLatLng;
    private  LocationCallback locationCallback;
    private final long UPDATE_INTERVAL =  10000L;
    private  final long FASTEST_INTERVAL = 30000L;
    private  boolean showDialog;

    public GpsTracker(Activity activity,boolean showDialog) {
        this.activity = activity;
        this.showDialog = showDialog;
    }


    public void getLatLng(GetLatLng param) {
        interfaceLatLng = param;

        startLocationUpdates();
/*
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                startLocationUpdates();

            }


            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                interfaceLatLng.getLatLngFromInterface(0.0, 0.0, false);
            }


        };

        TedPermission.with(activity)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(activity.getString(R.string.permission_denied))
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();*/
    }

    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing() && !((Activity) activity).isFinishing()) {
            progressDialog.show();
        } else if (!((Activity) activity).isFinishing()) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
    }


    void startLocationUpdates() {
        if(showDialog){
            showProgressDialog();
        }

        // create location request object
        mLocationRequest = LocationRequest.create();

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);


        // initialize location setting request builder object
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();


        // initialize location service object
        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                registerLocationListner();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                hideProgressDialog();
                if (exception instanceof ResolvableApiException) {
                    try {
                        ((ResolvableApiException) exception).startResolutionForResult(activity, GPS_TRACKER_REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException var3) {

                    }
                }
            }
        });
        // call register location listner


    }


    private  void registerLocationListner() {
        locationCallback= new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onLocationChanged(locationResult.getLastLocation());
            }
        };


        LocationServices.getFusedLocationProviderClient(activity).requestLocationUpdates(mLocationRequest, locationCallback, null);

    }

    private void onLocationChanged(Location location) {

        if (location != null) {
            interfaceLatLng.getLatLngFromInterface(location.getLatitude(), location.getLongitude(), true);
           hideProgressDialog();
        }

    }

  public void stopLocationUpdates()
    {
        LocationServices.getFusedLocationProviderClient(activity).removeLocationUpdates(locationCallback);
    }

}
