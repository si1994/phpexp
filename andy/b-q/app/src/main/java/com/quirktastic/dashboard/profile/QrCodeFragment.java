package com.quirktastic.dashboard.profile;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.quirktastic.R;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.activity.AddPhotosActivity;
import com.quirktastic.onboard.activity.WelcomeActivity;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.onboard.model.addphotos.AddPhotoModel;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class QrCodeFragment extends BaseFragment {
    private static final String TAG = QrCodeFragment.class.getName();

    private View rootView;
    private LinearLayout llQRBack, llBottomBtnScanQR;
    private ImageView imgQrCode;

    private DashboardActivity dashboardActivity;
    private static final int RESULT_LOAD_IMAGE = 123;


    public QrCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardActivity = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_qr_code, container, false);
        // Inflate the layout for this fragment
        llQRBack = (LinearLayout) rootView.findViewById(R.id.llQRBack);
        llBottomBtnScanQR = (LinearLayout) rootView.findViewById(R.id.llBottomBtnScanQR);
        imgQrCode = rootView.findViewById(R.id.imgQrCode);
        llQRBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardActivity.onBackPressed();
            }
        });

        llBottomBtnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //scanQRCode();
                scannDialog();
            }
        });

        generateQRCode();

        return rootView;
    }

    private void scanQRCode() {
        checkCameraPermission();
    }

    private void generateQRCode() {

        int qrWidthHeight = (int) getResources().getDimension(R.dimen._200sdp);

        String text = dashboardActivity.getString(R.string.app_name) + "_" + Prefs.getString(dashboardActivity, PrefsKey.USER_ID, ""); // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, qrWidthHeight, qrWidthHeight);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void checkCameraPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                Intent intent = new Intent(dashboardActivity, ScanPassNewActivity.class);
                dashboardActivity.startActivity(intent);

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(getActivity())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(getString(R.string.permission_denied))
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.CAMERA)
                .check();
    }


    private void scannDialog() {

        final Dialog dialog = new Dialog(dashboardActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup_qr_code);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        final TextView txtFromCame = (TextView) dialog.findViewById(R.id.txtFromCame);
        final TextView txtFromGallery = (TextView) dialog.findViewById(R.id.txtFromGallery);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setAttributes(lp);


        txtFromCame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                checkCameraPermission();

            }
        });

        txtFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                       /* CropImage.activity().setAspectRatio(1, 1)
                                .start(dashboardActivity);*/

                        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        getIntent.setType("image/*");
                        getIntent.addCategory(Intent.CATEGORY_OPENABLE);

                        /*Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("image/*");*/

                        /*Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});*/

                        startActivityForResult(getIntent, RESULT_LOAD_IMAGE);

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }


                };

                TedPermission.with(dashboardActivity)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage(getString(R.string.permission_denied))
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            CropImage.activity(selectedImage).setAspectRatio(1, 1)
                    .start(dashboardActivity);
        }

        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {


                    Uri resultUri = result.getUri();
                    File compressedImageFile = new Compressor(getActivity()).compressToFile(new File(resultUri.getPath()));


                    InputStream is = new BufferedInputStream(new FileInputStream(compressedImageFile));
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    String decoded = scanQRImage(bitmap);

                    Logger.e("QrTest", decoded);

                    if (decoded != null) {
                        if (decoded.trim().contains(dashboardActivity.getString(R.string.app_name) + "_")) {
                            String[] scannedText = decoded.trim().split("_");
                            if (scannedText != null && scannedText.length > 1) {
                                makeFriendAPI(scannedText[1]);
                            } else {
                                Toast.show(dashboardActivity, getString(R.string.invalid_user));
                            }


                        } else {
                            Toast.show(dashboardActivity, getString(R.string.invalid_user));
                        }
                    } else {
                        Toast.show(dashboardActivity, getString(R.string.no_qr_code_found));
                    }


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static String scanQRImage(Bitmap bMap) {
        String contents = null;

        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        } catch (Exception e) {
              Logger.e("QrTest", "Error decoding barcode");
        }
        return contents;
    }


    private void makeFriendAPI(String scanedUserId) {

        if (Prefs.getString(dashboardActivity, PrefsKey.USER_ID, "").trim().equalsIgnoreCase(scanedUserId.trim())) {
            Toast.show(dashboardActivity, getString(R.string.scan_error));
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(dashboardActivity, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, scanedUserId);

        new CallNetworkRequest().postResponse(dashboardActivity, true, "make friend API", AUTH_VALUE, WSUrl.POST_MAKE_FRIEND, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                Toast.show(dashboardActivity, commonResponse.getMessage());
                            } else {
                                Toast.show(dashboardActivity, commonResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(dashboardActivity, getString(R.string.error_contact_server));
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                    }
                });
    }

}
