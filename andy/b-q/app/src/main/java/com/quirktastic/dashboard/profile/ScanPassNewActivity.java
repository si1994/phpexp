package com.quirktastic.dashboard.profile;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.quirktastic.R;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.sendrequest.SendRequestActivity;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class ScanPassNewActivity extends AppCompatActivity {

    private String otherUserId = "";
    private BeepManager beepManager;
    private boolean torchOn = false;

    @BindView(R.id.barcode_scanner)
    CompoundBarcodeView barcodeView;

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_pass_new);
        ButterKnife.bind(this);

        barcodeView.setTorchListener(torchListener);
        barcodeView.decodeSingle(callback);
        beepManager = new BeepManager(this);
    }

    public void onResume() {
        this.barcodeView.resume();
        super.onResume();
    }

    public void onPause() {
        this.barcodeView.pause();
        this.torchOn = false;
        super.onPause();
    }

    private void toggleLight() {
        if (!hasFlash()) {
            return;
        }
        if (this.torchOn) {
            this.barcodeView.setTorchOff();
        } else {
            this.barcodeView.setTorchOn();
        }
    }

    private boolean hasFlash() {
        return getApplication().getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }


    CompoundBarcodeView.TorchListener torchListener = new CompoundBarcodeView.TorchListener() {
        @Override
        public void onTorchOn() {
            torchOn = true;
        }

        @Override
        public void onTorchOff() {
            torchOn = false;
        }
    };


    BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                boolean shouldBeep = PreferenceManager.getDefaultSharedPreferences(ScanPassNewActivity.this.getApplicationContext()).getBoolean("beep", true);
                beepManager.setBeepEnabled(shouldBeep);
                beepManager.setVibrateEnabled(false);
                if (shouldBeep) {
                    beepManager.playBeepSoundAndVibrate();
                }
//                Toast.show(ScanPassNewActivity.this, "" + result.getText());
                Logger.e(TAG, "scanned result ===> " + result.getText());

                if(result.getText().trim().contains(ScanPassNewActivity.this.getString(R.string.app_name)+"_")){
                    String[] scannedText = result.getText().trim().split("_");
                    if(scannedText != null && scannedText.length>1){
                        makeFriendAPI(scannedText[1]);
                    }else{
                        Toast.show(ScanPassNewActivity.this,getString(R.string.invalid_user));
                        finish();
                    }


                }else{
                    Toast.show(ScanPassNewActivity.this,getString(R.string.invalid_user));
                    finish();
                }


            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    private void makeFriendAPI(String scanedUserId) {

        if(Prefs.getString(ScanPassNewActivity.this,PrefsKey.USER_ID,"").trim().equalsIgnoreCase(scanedUserId.trim())){
            Toast.show(ScanPassNewActivity.this,getString(R.string.scan_error));
            finish();
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(ScanPassNewActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, scanedUserId);

        new CallNetworkRequest().postResponse(ScanPassNewActivity.this, true, "update Interest", AUTH_VALUE, WSUrl.POST_MAKE_FRIEND, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                Toast.show(ScanPassNewActivity.this, commonResponse.getMessage());
                                finish();
                            } else {
                                Toast.show(ScanPassNewActivity.this, commonResponse.getMessage());
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(ScanPassNewActivity.this,getString(R.string.error_contact_server));
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        finish();
                    }
                });
    }

     private boolean isStringInteger(String number ){
        try{
            Integer.parseInt(number);
        }catch(Exception e ){
            return false;
        }
        return true;
    }
}
