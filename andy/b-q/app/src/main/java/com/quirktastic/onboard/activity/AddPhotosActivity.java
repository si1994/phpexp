package com.quirktastic.onboard.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.quirktastic.BuildConfig;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.adapter.AddPhotoAdapter;
import com.quirktastic.onboard.model.addphotos.AddPhotoModel;
import com.quirktastic.onboard.model.addphotos.ModelAddPhoto;
import com.quirktastic.onboard.model.fblogin.FbLoginDetailsItem;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import id.zelory.compressor.Compressor;


public class AddPhotosActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBottomBtnNextAddInterests;
    private LinearLayout llSelectPhoto;
    private RecyclerView rvAddPhoto;
    private ArrayList<AddPhotoModel> listAddPhoto;
    private AddPhotoAdapter addPhotoAdapter;
    private String TAG = getClass().getSimpleName();
    private static final int RESULT_LOAD_IMAGE = 123;
    private static final int RESULT_LOAD_CAMERA = 120;
    private LinearLayout llBackNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);
        listAddPhoto = new ArrayList<>();
        viewBinding();
        addListner();
    }


    private void viewBinding() {
        llBottomBtnNextAddInterests = (LinearLayout) findViewById(R.id.llBottomBtnNextAddInterests);
        llSelectPhoto = (LinearLayout) findViewById(R.id.llSelectPhoto);
        rvAddPhoto = (RecyclerView) findViewById(R.id.rvAddPhoto);
        llBackNext = (LinearLayout) findViewById(R.id.llBackNext);
    }


    private void addListner() {
        llBottomBtnNextAddInterests.setOnClickListener(this);
        llSelectPhoto.setOnClickListener(this);
        llBackNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llBottomBtnNextAddInterests:
//                startActivity(new Intent(AddPhotosActivity.this, InterestsActivity.class));
                loadNextScreen();
                break;
            case R.id.llSelectPhoto:
                if (listAddPhoto.size() < 4) {
                    checkPermission();
                } else {
                    Toast.show(AddPhotosActivity.this, "You cannot add more than 4 photos");
                }
                break;
            case R.id.llBackNext:
                redirectToInterestsActivity();
                break;
        }

    }

    private void loadNextScreen() {

        if (listAddPhoto.size() > 0) {
            // if (listAddPhoto.size() == 4) {


            for (int i = 0; i < listAddPhoto.size(); i++) {


                    /*try {
                        File compressedImageFile = new Compressor(this).compressToFile(listAddPhoto.get(i).getFile());
                        AddPhotoModel addPhotoModel = listAddPhoto.get(i);
                        addPhotoModel.setFile(compressedImageFile);
                        listAddPhoto.set(i, addPhotoModel);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                AddPhotoModel addPhotoModel = listAddPhoto.get(i);
                addPhotoModel.setFile(listAddPhoto.get(i).getFile());
                listAddPhoto.set(i, addPhotoModel);

            }
            uploadFile();

           /* } else {
                Toast.show(AddPhotosActivity.this, "please add at least 4 photos");
            }*/

        } else {
            Toast.show(AddPhotosActivity.this, "Please add at least one photo");
        }
    }

    // call ws for upload images to server
    private void uploadFile() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(AddPhotosActivity.this, PrefsKey.USER_ID, ""));
        ArrayList<File> listUpload = new ArrayList<>();
        for (int i = 0; i < listAddPhoto.size(); i++) {
            try {
                listUpload.add(new Compressor(this).compressToFile(listAddPhoto.get(i).getFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       // Collections.reverse(listAddPhoto);
        new CallNetworkRequest().uploadFile(this, true, "Add Photos", AppContants.AUTH_VALUE, WSUrl.POST_UPDATE_PHOTOS,
                map, listUpload, new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            if (!TextUtils.isEmpty(response)) {
                                ModelAddPhoto modelAddPhoto = new Gson().fromJson(response, ModelAddPhoto.class);
                                if (modelAddPhoto != null && modelAddPhoto.isFLAG()) {

                                    if (modelAddPhoto.getlOGINDETAILS() != null && modelAddPhoto.getlOGINDETAILS().size() > 0) {

                                        FbLoginDetailsItem fbLoginDetailsItem = modelAddPhoto.getlOGINDETAILS().get(0);

                                        if (fbLoginDetailsItem.getProfilePic() != null && fbLoginDetailsItem.getProfilePic().length() > 0) {
                                            Prefs.setString(AddPhotosActivity.this, PrefsKey.PROFILE_PIC, fbLoginDetailsItem.getProfilePic());
                                        }
                                    }

                                    startActivity(new Intent(AddPhotosActivity.this, InterestsActivity.class));
                                    // finish();
                                } else {
                                    Toast.show(AddPhotosActivity.this, modelAddPhoto.getMESSAGE());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(AddPhotosActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                photoPickDialog();

               /* CropImage.activity().setAspectRatio(1, 1)
                        .start(AddPhotosActivity.this);*/
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(getString(R.string.permission_denied))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            CropImage.activity(selectedImage).setAspectRatio(1, 1)
                    .start(AddPhotosActivity.this);
        }

        if (requestCode == RESULT_LOAD_CAMERA && resultCode == RESULT_OK) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("quirktasticTemp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.show(AddPhotosActivity.this, "Error while capturing image");

                return;

            }

            try {
                Uri selectedImage = Uri.fromFile(f);
                CropImage.activity(selectedImage).setAspectRatio(1, 1)
                        .start(AddPhotosActivity.this);

            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
        }

        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    AddPhotoModel addPhotoModel = new AddPhotoModel();
                    addPhotoModel.setUri(resultUri);
                    addPhotoModel.setFile(new File(resultUri.getPath()));
                    listAddPhoto.add(addPhotoModel);
                    // Collections.reverse(listAddPhoto);
                    setAddPhotoAdapter();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAddPhotoAdapter() {
        if (addPhotoAdapter == null) {
            addPhotoAdapter = new AddPhotoAdapter(AddPhotosActivity.this, listAddPhoto);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(AddPhotosActivity.this, LinearLayoutManager.HORIZONTAL, false);
            rvAddPhoto.setHasFixedSize(true);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            rvAddPhoto.setLayoutManager(mLayoutManager);
            rvAddPhoto.setAdapter(addPhotoAdapter);
        } else {
            addPhotoAdapter.notifyDataSetChanged();
        }
    }

    public void deletePhoto(int pos) {
        Iterator<AddPhotoModel> iterator = listAddPhoto.iterator();
        while (iterator.hasNext()) {
            AddPhotoModel value = iterator.next();
            if (listAddPhoto.get(pos).getUri().equals(value.getUri())) {
                iterator.remove();
                break;
            }
        }
        addPhotoAdapter.notifyDataSetChanged();
    }

    private void photoPickDialog() {

        final Dialog dialog = new Dialog(AddPhotosActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup_qr_code);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        final TextView txtFromCame = (TextView) dialog.findViewById(R.id.txtFromCame);
        final TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        final TextView txtFromGallery = (TextView) dialog.findViewById(R.id.txtFromGallery);

        tvTitle.setText("Pick photo from");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setAttributes(lp);


        txtFromCame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                captureImage();

            }
        });

        txtFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");
                getIntent.addCategory(Intent.CATEGORY_OPENABLE);

                startActivityForResult(getIntent, RESULT_LOAD_IMAGE);

            }
        });
    }


    public void captureImage() {

        File f = new File(android.os.Environment
                .getExternalStorageDirectory(), "quirktasticTemp.jpg");

        Uri outUri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // only for gingerbread and newer versions
            outUri = FileProvider.getUriForFile(AddPhotosActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    f);
        } else
            outUri = Uri.fromFile(f);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);

        startActivityForResult(cameraIntent,
                RESULT_LOAD_CAMERA);

        /*// Creating folders for Image
        String imageFolderPath = Environment.getExternalStorageDirectory().toString()
                + "/quirktastic";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file name
        String imageName="temp.jpg";

        // Creating image here
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imageFolderPath, imageName)));
        startActivityForResult(takePictureIntent,
                RESULT_LOAD_CAMERA);*/

    }


    private void redirectToInterestsActivity() {
        Intent intent = new Intent(AddPhotosActivity.this, InterestsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }


}
