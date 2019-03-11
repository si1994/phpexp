package com.quirktastic.network;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.quirktastic.R;
import com.quirktastic.onboard.activity.WelcomeActivity;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CallNetworkRequest {
    private ProgressDialog progressDialog;
    private String TAG = getClass().getSimpleName();

    public void postResponse(final Context context, boolean shouldDisplayDialog, final String tag, String headers, String url, HashMap<String, Object> map, final INetworkResponse networkResponse) {
        if (Utility.isInternetOn(context)) {
            if (shouldDisplayDialog) {
                showProgressDialog(context);
            }
            Logger.e(TAG, "POST url ===> " + url);
            Logger.e(TAG, "request paramas ===> " + map.toString());
            AndroidNetworking.post(url)
                    .addBodyParameter(map)
                    .addHeaders("Authorization", headers)
                    .setTag(tag)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Logger.e(TAG, tag + " response ===> " + response.toString());

                            JSONObject jobj = null;
                            try {
                                jobj = new JSONObject(response.toString());
                                if (jobj.has("IS_ACTIVE")) {
                                    if (jobj.getString("IS_ACTIVE").equalsIgnoreCase("false")) {
                                        try {
                                            logOutDialog(context);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        networkResponse.onSuccess(response);
                                    }
                                } else {
                                    networkResponse.onSuccess(response);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            hideProgressDialog();
                        }

                        @Override
                        public void onError(ANError anError) {
                            networkResponse.onError(anError);
                            hideProgressDialog();
                        }
                    });

        } else {
            Toast.makeText(context, context.getString(R.string.internet_offline), Toast.LENGTH_LONG).show();
        }
    }

    public void getResponse(final Context context, boolean shouldDisplayDialog, final String tag, String headers, String url, final INetworkResponse networkResponse) {
        if (Utility.isInternetOn(context)) {
            if (shouldDisplayDialog) {
                showProgressDialog(context);
            }
            Logger.e(TAG, "GET url ==> " + url);
            AndroidNetworking.get(url)
                    .addHeaders("Authorization", headers)
                    .setTag(tag)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Logger.e(TAG, tag + " response ===> " + response.toString());
                            JSONObject jobj = null;
                            try {
                                jobj = new JSONObject(response.toString());
                                if (jobj.has("IS_ACTIVE")) {
                                    if (jobj.getString("IS_ACTIVE").equalsIgnoreCase("false")) {
                                        try {
                                            logOutDialog(context);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        networkResponse.onSuccess(response);
                                    }
                                } else {
                                    networkResponse.onSuccess(response);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onError(ANError anError) {
                            networkResponse.onError(anError);
                            hideProgressDialog();
                        }
                    });

        } else {
            Toast.makeText(context, context.getString(R.string.internet_offline), Toast.LENGTH_LONG).show();
        }
    }

    public void uploadFile(final Context context, boolean shouldDisplayDialog, final String tag, String headers, String url, HashMap<String, Object> map, ArrayList<File> listFile, final INetworkResponse networkResponse) {
        if (Utility.isInternetOn(context)) {
            Logger.e(TAG, "upload url ===> " + url);
            Logger.e(TAG, "request paramas ===> " + map.toString());
            if (shouldDisplayDialog) {
                showProgressDialog(context);
                AndroidNetworking.upload(url)
                        .addMultipartFileList("photos[]", listFile)
                        .addHeaders("Authorization", headers)
                        .setTag(tag)
                        .addMultipartParameter(map)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Logger.e(TAG, tag + " response ===> " + response.toString());
                                JSONObject jobj = null;
                                try {
                                    jobj = new JSONObject(response.toString());
                                    if (jobj.has("IS_ACTIVE")) {
                                        if (jobj.getString("IS_ACTIVE").equalsIgnoreCase("false")) {
                                            try {
                                                logOutDialog(context);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            networkResponse.onSuccess(response);
                                        }
                                    } else {
                                        networkResponse.onSuccess(response);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                hideProgressDialog();
                            }

                            @Override
                            public void onError(ANError anError) {
                                networkResponse.onError(anError);
                               // com.quirktastic.utility.Toast.show(context, context.getString(R.string.error_contact_server));
                                hideProgressDialog();
                            }
                        });
            }
        } else {
            Toast.makeText(context, context.getString(R.string.internet_offline), Toast.LENGTH_LONG).show();
        }
    }

    public void uploadFilesHashMap(final Context context, boolean shouldDisplayDialog, final String tag, String headers, String url, HashMap<String, Object> map,HashMap<String, Object> mapFiles, final INetworkResponse networkResponse) {
        if (Utility.isInternetOn(context)) {
            Logger.e(TAG, "upload url ===> " + url);
            Logger.e(TAG, "request paramas ===> " + map.toString());
            if (shouldDisplayDialog) {
                showProgressDialog(context);
                AndroidNetworking.upload(url)
                        .addMultipartFile(mapFiles)
                        .addHeaders("Authorization", headers)
                        .setTag(tag)
                        .addMultipartParameter(map)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Logger.e(TAG, tag + " response ===> " + response.toString());
                                JSONObject jobj = null;
                                try {
                                    jobj = new JSONObject(response.toString());
                                    if (jobj.has("IS_ACTIVE")) {
                                        if (jobj.getString("IS_ACTIVE").equalsIgnoreCase("false")) {
                                            try {
                                                logOutDialog(context);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            networkResponse.onSuccess(response);
                                        }
                                    } else {
                                        networkResponse.onSuccess(response);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                hideProgressDialog();
                            }

                            @Override
                            public void onError(ANError anError) {
                                networkResponse.onError(anError);
                                // com.quirktastic.utility.Toast.show(context, context.getString(R.string.error_contact_server));
                                hideProgressDialog();
                            }
                        });
            }
        } else {
            Toast.makeText(context, context.getString(R.string.internet_offline), Toast.LENGTH_LONG).show();
        }
    }


    public void uploadGif(final Context context, boolean shouldDisplayDialog, final String tag, String headers, String url, HashMap<String, Object> map, File file, final INetworkResponse networkResponse) {
        if (Utility.isInternetOn(context)) {
            Logger.e(tag, "upload url ===> " + url);
            Logger.e(tag, "request paramas ===> " + map.toString());
                AndroidNetworking.upload(url)
                        .addMultipartFile(WSKey.MESSAGE_FILE,file)
                        .addHeaders("Authorization", headers)
                        .setTag(tag)
                        .addMultipartParameter(map)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Logger.e(tag, " response GIF ===> " + response.toString());
                                JSONObject jobj = null;
                                try {
                                    jobj = new JSONObject(response.toString());
                                    if (jobj.has("IS_ACTIVE")) {
                                        if (jobj.getString("IS_ACTIVE").equalsIgnoreCase("false")) {
                                            try {
                                                logOutDialog(context);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            networkResponse.onSuccess(response);
                                        }
                                    } else {
                                        networkResponse.onSuccess(response);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                hideProgressDialog();
                            }

                            @Override
                            public void onError(ANError anError) {
                                networkResponse.onError(anError);
                                Logger.e(tag, " anError GIF ===> " + anError.getErrorBody().toString());
                                // com.quirktastic.utility.Toast.show(context, context.getString(R.string.error_contact_server));
                                hideProgressDialog();
                            }
                        });

        } else {
            Toast.makeText(context, context.getString(R.string.internet_offline), Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressDialog(Context context) {
        if (progressDialog != null && !progressDialog.isShowing() && !((Activity) context).isFinishing()) {
            progressDialog.show();
        } else if (!((Activity) context).isFinishing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    // Show dialog for LogOut from Application..
    private void logOutDialog(final Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup_ok_layout);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        final TextView btnOk = (TextView) dialog.findViewById(R.id.btnOk);
        final TextView txtDescription = (TextView) dialog.findViewById(R.id.txtDescription);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        txtDescription.setText("Your access to the application is restricted by admin.");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Prefs.clearPreferences(context);
                Intent intent = new Intent(context, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }


}
