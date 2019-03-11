package com.quirktastic.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    public FirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get InstanceID token.
        String mToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("token:" + mToken);

        Prefs.setString(this, PrefsKey.FCM_TOKEN, mToken);
        // we can send this mToken to the server for the sending Notification to the Application.

    }
}
