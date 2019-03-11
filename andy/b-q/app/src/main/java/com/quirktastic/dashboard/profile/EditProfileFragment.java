package com.quirktastic.dashboard.profile;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.androidnetworking.error.ANError;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.quirktastic.BuildConfig;
import com.quirktastic.R;
import com.quirktastic.dashboard.profile.adapter.IdentityCheckAdapter;
import com.quirktastic.dashboard.profile.adapter.InterestCheckAdapter;
import com.quirktastic.dashboard.profile.adapter.MyPhotosAdapter;
import com.quirktastic.dashboard.profile.adapter.SocialCheckAdapter;
import com.quirktastic.dashboard.profile.model.profiledetails.EventItem;
import com.quirktastic.dashboard.profile.model.profiledetails.IdentityItem;
import com.quirktastic.dashboard.profile.model.profiledetails.InterestItem;
import com.quirktastic.dashboard.profile.model.profiledetails.PhotosItem;
import com.quirktastic.dashboard.profile.model.profiledetails.ProfileDetailsResponse;
import com.quirktastic.dashboard.profile.model.profiledetails.SocialLifeItem;
import com.quirktastic.dashboard.profile.model.profiledetails.UserDetailsItem;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.ProgressDialog;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.adapter.EventListSearchForEditAdapter;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.onboard.model.addphotos.AddPhotoModel;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.ItemMoveCallback;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.StartDragListener;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import br.com.instachat.emojilibrary.model.layout.EmojiEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static com.quirktastic.utility.AppContants.AUTH_VALUE;
import static com.quirktastic.utility.AppContants.GENDER_MAN;
import static com.quirktastic.utility.AppContants.GENDER_NON_BINARY;
import static com.quirktastic.utility.AppContants.GENDER_WOMAN;
import static com.quirktastic.utility.AppContants.GOOGLE_MAP_API_KEY;
import static io.fabric.sdk.android.services.common.CommonUtils.streamToString;


public class EditProfileFragment extends Fragment implements StartDragListener {

    @BindView(R.id.rcvMyInterests)
    RecyclerView rcvMyInterests;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;

    @BindView(R.id.edtInterestAnswer)
    EmojiEditText edtInterestAnswer;
    @BindView(R.id.edtPersonalityText)
    EmojiEditText edtPersonalityText;
    @BindView(R.id.edtBioData)
    EmojiEditText edtBioData;
    @BindView(R.id.tvBioTextCount)
    TextView tvBioTextCount;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.edtDob)
    EditText edtDob;
    @BindView(R.id.llEditZipCode)
    LinearLayout llEditZipCode;
    @BindView(R.id.edtZipCode)
    TextView edtZipCode;
    @BindView(R.id.rcvMyEthnicity)
    RecyclerView rcvMyEthnicity;
    @BindView(R.id.rcvSocialLifeList)
    RecyclerView rcvSocialLifeList;
    @BindView(R.id.tbvFacebook)
    ToggleButton tbvFacebook;
    @BindView(R.id.tbvInstagram)
    ToggleButton tbvInstagram;
    @BindView(R.id.llEditDetails)
    LinearLayout llEditDetails;

  /*  @BindView(R.id.rlFirstImage)
    RelativeLayout rlFirstImage;

    @BindView(R.id.rlSecondImage)
    RelativeLayout rlSecondImage;

    @BindView(R.id.rlThirdImage)
    RelativeLayout rlThirdImage;

    @BindView(R.id.rlFourthImage)
    RelativeLayout rlFourthImage;*/

    @BindView(R.id.cbWoman)
    CheckBox cbWoman;

    @BindView(R.id.cbMan)
    CheckBox cbMan;

    @BindView(R.id.cbNonBinary)
    CheckBox cbNonBinary;

    @BindView(R.id.flEditEventChip)
    FlexboxLayout flEditEventChip;
    @BindView(R.id.rvMyPhotos)
    RecyclerView rvMyPhotos;

    private View rootView;

    private UserDetailsItem userDetailsItem;

    private int mYear, mMonth, mDay;


    private ArrayList<EventItem> eventsList;
    private ArrayList<EventItem> eventsListAdded;

    private EventListSearchForEditAdapter adapter;

    private ArrayList<AddPhotoModel> listAddPhoto;
    private String TAG = getClass().getSimpleName();

    public static int clickedAddImageIndex = -1;
    private int screenWidth;
    private int imageWidthHeight;

    private int FINDHOMECITY = 102;

    private ProfileActivity profileActivity;


    private String cityName = "";
    private String stateName = "";
    private String stateShortName = "";

    public static int totalSize;

    ItemTouchHelper touchHelper;

    private ArrayList<PhotosItem> photosList;
    private static final int RESULT_LOAD_IMAGE = 123;
    private static final int RESULT_LOAD_CAMERA = 120;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {

        EditProfileFragment f = new EditProfileFragment();
        return f;
    }

    private ProgressDialog progressDialog;
    private Dialog dialoginstagramAuthorize;
    private String request_token, instagramAccessToken = "", instagramid = "";
    private String authURLString, tokenURLString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        imageWidthHeight = (screenWidth / 2);
        profileActivity = (ProfileActivity) getActivity();
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRetainInstance(true);
        if (getView() != null) {
            ButterKnife.bind(EditProfileFragment.this, getView());
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, rootView);

      /*  LinearLayout.LayoutParams allImageParentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageWidthHeight, 1f);

        rlFirstImage.setLayoutParams(allImageParentParams);
        rlSecondImage.setLayoutParams(allImageParentParams);
        rlThirdImage.setLayoutParams(allImageParentParams);
        rlFourthImage.setLayoutParams(allImageParentParams);*/

        listAddPhoto = new ArrayList<>();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            userDetailsItem = (UserDetailsItem) getArguments().getSerializable("userDetailsItem");
            init();
            setUpListener();
            setUpViews(userDetailsItem);
        }
    }

    private void init() {

        rvMyPhotos.setLayoutManager(new GridLayoutManager(profileActivity, 2));

        edtDob.addTextChangedListener(new TextWatcher() {
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

//        edtDob.setFocusable( false );

    }

    private void setUpListener() {


        tbvInstagram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (buttonView.isPressed()) {
                        instagramAuthorize();
                    }
                } else {

                    if (buttonView.isPressed()) {
                        instagramAccessToken = "";
                        instagramid = "";
                        CookieSyncManager.createInstance(profileActivity);
                        CookieManager cookieManager = CookieManager.getInstance();
                        cookieManager.removeAllCookie();
                    }

                }
            }
        });

        tbvFacebook.setEnabled(false);

        tbvFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });


        cbWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    if (userDetailsItem != null) {
                        userDetailsItem.setGender(GENDER_WOMAN);
                        setUpGender(GENDER_WOMAN);
                    }
                }
            }
        });

        cbMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (userDetailsItem != null) {
                        userDetailsItem.setGender(GENDER_MAN);
                        setUpGender(GENDER_MAN);
                    }
                }
            }
        });

        cbNonBinary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (userDetailsItem != null) {
                        userDetailsItem.setGender(GENDER_NON_BINARY);
                        setUpGender(GENDER_NON_BINARY);
                    }
                }
            }
        });

        edtBioData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tvBioTextCount.setText(charSequence.toString().length() + "/150");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ProfileActivity.tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkValidation();
            }
        });

    }

    public static int checkPhotos() {
        return totalSize;
    }

    private void checkValidation() {

        try {

            int age = 0;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date birthDate = sdf.parse(edtDob.getText().toString());
                age = Util.getAge(birthDate);
                System.out.println("age------------>" + age);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            /*if (totalSize != 4) {
                Toast.show(getActivity(), "Please add at least 4 photos");
            } else*/
            if (edtFirstName.getText().toString().trim().equals("")) {
                Toast.show(getActivity(), "Please enter the first name");
            } else if (edtLastName.getText().toString().trim().equals("")) {
                Toast.show(getActivity(), "Please enter the last name");
            } else if (edtDob.getText().toString().trim().equals("")) {
                Toast.show(getActivity(), "Please enter the Date of birth");
            } else if (edtZipCode.getText().toString().trim().equals("")) {
                Toast.show(getActivity(), "Please enter the zipcode");
            } else if (edtZipCode.getText().toString().trim().length() < 5) {
                Toast.show(getActivity(), "Please enter the valid zipcode");
            } else if (!Util.isDateValid(edtDob.getText().toString().trim(), "MM/dd/yyyy")) {
                Toast.show(getActivity(), "Please enter the valid Date of birth");
            } else if (age < 18) {
                Toast.show(getActivity(), "To use this app.You must 18 years old");
            } else if (!cbMan.isChecked() && !cbWoman.isChecked() && !cbNonBinary.isChecked()) {
                Toast.show(getActivity(), "Please select gender");
            } else {
                wsGetCityState(edtZipCode.getText().toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @OnClick({R.id.edtDob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edtDob:
//                setUpDateOfBirthPicker();
                break;
          /*  case R.id.ivRemoveFirstImage:

                if (userDetailsItem.getPhotos() != null && userDetailsItem.getPhotos().size() > 0) {
                    if (userDetailsItem.getPhotos().get(0).getPhotos() != null
                            && userDetailsItem.getPhotos().get(0).getPhotos().length() > 0) {

                        deleteImageAPI(userDetailsItem.getPhotos().get(0).getId(), 0);

                    } else {
                        deletePhoto((Uri) ivFirst.getTag(), 0);
                    }

                } else {
                    deletePhoto((Uri) ivFirst.getTag(), 0);
                }

                break;
            case R.id.ivRemoveSecondImage:

                if (userDetailsItem.getPhotos() != null && userDetailsItem.getPhotos().size() > 1) {
                    if (userDetailsItem.getPhotos().get(1).getPhotos() != null
                            && userDetailsItem.getPhotos().get(1).getPhotos().length() > 0) {

                        deleteImageAPI(userDetailsItem.getPhotos().get(1).getId(), 1);

                    } else {
                        deletePhoto((Uri) ivSecond.getTag(), 1);
                    }
                } else {
                    deletePhoto((Uri) ivSecond.getTag(), 1);
                }

                break;
            case R.id.ivRemoveThirdImage:

                if (userDetailsItem.getPhotos() != null && userDetailsItem.getPhotos().size() > 2) {
                    if (userDetailsItem.getPhotos().get(2).getPhotos() != null
                            && userDetailsItem.getPhotos().get(2).getPhotos().length() > 0) {


                        deleteImageAPI(userDetailsItem.getPhotos().get(2).getId(), 2);

                    } else {
                        deletePhoto((Uri) ivThird.getTag(), 2);
                    }
                } else {
                    deletePhoto((Uri) ivThird.getTag(), 2);
                }

                break;
            case R.id.ivRemoveFourthImage:
                if (userDetailsItem.getPhotos() != null && userDetailsItem.getPhotos().size() > 3) {
                    if (userDetailsItem.getPhotos().get(3).getPhotos() != null
                            && userDetailsItem.getPhotos().get(3).getPhotos().length() > 0) {

                        deleteImageAPI(userDetailsItem.getPhotos().get(3).getId(), 3);

                    } else {
                        deletePhoto((Uri) ivFourth.getTag(), 3);
                    }
                } else {
                    deletePhoto((Uri) ivFourth.getTag(), 3);
                }

                break;
            case R.id.ivAddFirstImage:
                clickedAddImageIndex = 0;
                checkPermission();
                break;
            case R.id.ivAddSecondImage:
                clickedAddImageIndex = 1;
                checkPermission();
                break;
            case R.id.ivAddThirdImage:
                clickedAddImageIndex = 2;
                checkPermission();
                break;
            case R.id.ivAddFourthImage:
                clickedAddImageIndex = 3;
                checkPermission();
                break;*/
        }
    }


    private void instagramAuthorize() {
        dialoginstagramAuthorize = new Dialog(profileActivity);
        dialoginstagramAuthorize.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoginstagramAuthorize.setContentView(R.layout.custom_popup_webview);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialoginstagramAuthorize.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        authURLString = WSUrl.INSTA_AUTHURL + "?client_id=" + AppContants.INSTA_CLIENT_ID + "&redirect_uri=" + WSUrl.INSTA_CALLBACKURL + "&response_type=code";
        tokenURLString = WSUrl.INSTA_TOKENURL + "?client_id=" + AppContants.INSTA_CLIENT_ID + "&client_secret=" + AppContants.INSTA_CLIENT_SECRET + "&redirect_uri=" + WSUrl.INSTA_CALLBACKURL + "&grant_type=authorization_code";

        WebView webView = (WebView) dialoginstagramAuthorize.findViewById(R.id.webView);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(0);
        webView.setBackgroundResource(android.R.color.white);
        webView.loadUrl(authURLString);

        dialoginstagramAuthorize.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialoginstagramAuthorize.show();
        dialoginstagramAuthorize.getWindow().setAttributes(lp);

        dialoginstagramAuthorize.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (instagramAccessToken.equals("") && instagramid.equals("")) {
                    tbvInstagram.setChecked(false);
                } else {
                    tbvInstagram.setChecked(true);
                }
            }
        });

    }

    public class AuthWebViewClient extends WebViewClient {

        public AuthWebViewClient() {
            showProgressDialog(profileActivity);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(WSUrl.INSTA_CALLBACKURL)) {
                System.out.println(url);
                String parts[] = url.split("=");
                request_token = parts[1];  //This is your request token.
                dialoginstagramAuthorize.dismiss();
                new InstagramAuthorize().execute("");
                return true;
            }
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            hideProgressDialog();

        }
    }

    private class InstagramAuthorize extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(tokenURLString);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
                outputStreamWriter.write("client_id=" + AppContants.INSTA_CLIENT_ID +
                        "&client_secret=" + AppContants.INSTA_CLIENT_SECRET +
                        "&grant_type=authorization_code" +
                        "&redirect_uri=" + WSUrl.INSTA_CALLBACKURL +
                        "&code=" + request_token);

                outputStreamWriter.flush();
                String response = streamToString(httpsURLConnection.getInputStream());
                JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
                instagramAccessToken = jsonObject.getString("access_token"); //Here is your ACCESS TOKEN
                instagramid = jsonObject.getJSONObject("user").getString("id");
                String username = jsonObject.getJSONObject("user").getString("username"); //This is how you can get the user info. You can explore the JSON sent by Instagram as well to know what info you got in a response

            } catch (Exception e) {
                e.printStackTrace();
                tbvInstagram.setChecked(false);
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            if (instagramAccessToken.equals("") && instagramid.equals("")) {
                tbvInstagram.setChecked(false);
            } else {
                tbvInstagram.setChecked(true);
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    private void setUpDateOfBirthPicker() {
        Util.hideKeyboard(getActivity(), edtDob);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;

                        edtDob.setText(Util.getTwoDigitInt((monthOfYear + 1)) + "/" + Util.getTwoDigitInt(dayOfMonth) + "/" + year);

                    }
                }, mYear, mMonth, mDay);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);
        cal.add(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private void setUpViews(UserDetailsItem userDetailsItem) {
        try {
            if (userDetailsItem != null) {

                if (userDetailsItem.getDateOfBirth() != null && userDetailsItem.getDateOfBirth().trim().length() > 0) {

                    try {

                        if (userDetailsItem.getDateOfBirth().equals("0000-00-00")) {
                            Calendar cal = Calendar.getInstance();
                            mYear = cal.get(Calendar.YEAR);
                            mMonth = cal.get(Calendar.MONTH);
                            mDay = cal.get(Calendar.DAY_OF_MONTH);
                        } else {

                            String dob[] = userDetailsItem.getDateOfBirth().split("-");
                            int year = Integer.parseInt(dob[0]);
                            int month = Integer.parseInt(dob[1]);
                            int day = Integer.parseInt(dob[2]);

                            mYear = year;
                            mMonth = (month - 1);
                            mDay = day;
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Calendar cal = Calendar.getInstance();
                        mYear = cal.get(Calendar.YEAR);
                        mMonth = cal.get(Calendar.MONTH);
                        mDay = cal.get(Calendar.DAY_OF_MONTH);
                    }

                } else {
                    Calendar cal = Calendar.getInstance();
                    mYear = cal.get(Calendar.YEAR);
                    mMonth = cal.get(Calendar.MONTH);
                    mDay = cal.get(Calendar.DAY_OF_MONTH);
                }

                if (userDetailsItem.getHeaderBio() != null && userDetailsItem.getHeaderBio().trim().length() > 0) {
                    edtBioData.setText(Util.gifDecode(userDetailsItem.getHeaderBio().trim()));
                }

                if (userDetailsItem.getPersonalityText() != null && userDetailsItem.getPersonalityText().trim().length() > 0) {
                    edtPersonalityText.setText(Util.gifDecode(userDetailsItem.getPersonalityText().trim()));
                }

                if (userDetailsItem.getInterestsText() != null && userDetailsItem.getInterestsText().trim().length() > 0) {
                    edtInterestAnswer.setText(Util.gifDecode(userDetailsItem.getInterestsText().trim()));
                }

                if (userDetailsItem.getZipcode() != null && userDetailsItem.getZipcode().trim().length() > 0) {
                    edtZipCode.setText(userDetailsItem.getZipcode().trim());
                }

                if (userDetailsItem.getInstagramId() != null && userDetailsItem.getInstagramId().trim().length() > 0) {

                    if (userDetailsItem.getInstagramAccessToken() != null && userDetailsItem.getInstagramAccessToken().trim().length() > 0) {
                        tbvInstagram.setChecked(true);
                        instagramid = userDetailsItem.getInstagramId();
                        instagramAccessToken = userDetailsItem.getInstagramAccessToken();
                    } else {
                        tbvInstagram.setChecked(false);
                    }
                } else {
                    tbvInstagram.setChecked(false);
                }

                setUpimages(userDetailsItem);
                setUpInterest(userDetailsItem.getInterest());

                setUpEvents(userDetailsItem.getEvent());

                if (userDetailsItem.getFirstName() != null && userDetailsItem.getFirstName().trim().length() > 0) {
                    edtFirstName.setText(userDetailsItem.getFirstName().trim());
                    Prefs.setString(getActivity(), PrefsKey.BASIC_INFO_FIRST_NAME, userDetailsItem.getFirstName());
                }

                if (userDetailsItem.getLastName() != null && userDetailsItem.getLastName().trim().length() > 0) {
                    edtLastName.setText(userDetailsItem.getLastName().trim());
                    Prefs.setString(getActivity(), PrefsKey.BASIC_INFO_LAST_NAME, userDetailsItem.getLastName());
                }

                if (userDetailsItem.getFirstName() != null && userDetailsItem.getFirstName().trim().length() > 0
                        || userDetailsItem.getLastName() != null && userDetailsItem.getLastName().trim().length() > 0) {
                    ProfileActivity.setUserName(userDetailsItem.getFirstName(), userDetailsItem.getLastName());
                }

                if (userDetailsItem.getDateOfBirth() != null && userDetailsItem.getDateOfBirth().trim().length() > 0) {
                    edtDob.setText(Util.getMDYfromYYYYMMDD(userDetailsItem.getDateOfBirth()).trim());
                }

                if (userDetailsItem.getZipcode() != null && userDetailsItem.getZipcode().trim().length() > 0) {
                    edtZipCode.setText(userDetailsItem.getZipcode().trim());
                }

                if (userDetailsItem.getProfilePic() != null && userDetailsItem.getProfilePic().trim().length() > 0) {
                    Prefs.setString(getActivity(), PrefsKey.PROFILE_PIC, userDetailsItem.getProfilePic());
                } else {
                    Prefs.setString(getActivity(), PrefsKey.PROFILE_PIC, "");
                }

                setUpIdentity(userDetailsItem.getIdentity());
                setUpSocial(userDetailsItem.getSocialLife());
                setUpGender(userDetailsItem.getGender());


                if (userDetailsItem.getFacebookId() != null && userDetailsItem.getFacebookId().trim().length() > 0) {
                    tbvFacebook.setChecked(true);
                } else {
                    tbvFacebook.setChecked(false);
                }

            }
        } catch (Exception ex) {

        }
    }


    private void setUpGender(String gender) {

        if (gender.equalsIgnoreCase(GENDER_MAN)) {
            cbMan.setChecked(true);
            cbWoman.setChecked(false);
            cbNonBinary.setChecked(false);
        } else if (gender.equalsIgnoreCase(GENDER_WOMAN)) {
            cbMan.setChecked(false);
            cbWoman.setChecked(true);
            cbNonBinary.setChecked(false);
        } else if (gender.equalsIgnoreCase(GENDER_NON_BINARY)) {
            cbMan.setChecked(false);
            cbWoman.setChecked(false);
            cbNonBinary.setChecked(true);

        }
    }

    private void setUpEvents(ArrayList<EventItem> eventsList) {

        if (eventsListAdded == null) {
            eventsListAdded = new ArrayList<>();
        }

        // add selected events
        if (eventsList != null && eventsList.size() > 0) {
            for (int i = 0; i < eventsList.size(); i++) {
                if (eventsList.get(i).getIsSelected() == 1) {
                    eventsListAdded.add(eventsList.get(i));
                }
            }
            for (int i = 0; i < eventsListAdded.size(); i++) {
                View v = editEventChip(getActivity(), eventsListAdded.get(i));
                flEditEventChip.addView(v, 0);
            }


        }

        // for searching events, it hold all events
        adapter = new EventListSearchForEditAdapter(getActivity(), eventsList);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EventItem eventListItem = adapter.getItem(position);
                if (eventsListAdded == null) {
                    eventsListAdded = new ArrayList<>();
                }

                if (!eventsListAdded.contains(eventListItem)) {
                    if (eventsListAdded.size() < 3) {
                        eventsListAdded.add(eventListItem);
                        View v = editEventChip(getActivity(), eventListItem);
                        flEditEventChip.addView(v, 0);
                        Toast.show(getActivity(), getString(R.string.event_add_successfully));
                    } else {
                        Toast.show(getActivity(), getString(R.string.max_three_events));
                    }
                }


                autoCompleteTextView.setText("");
            }
        });
    }

    private void setUpSocial(ArrayList<SocialLifeItem> socialLife) {
        if (socialLife != null && socialLife.size() > 0) {
            rcvSocialLifeList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rcvSocialLifeList.setAdapter(new SocialCheckAdapter(getActivity(), socialLife));
        }
    }

    private void setUpIdentity(ArrayList<IdentityItem> identity) {
        if (identity != null && identity.size() > 0) {
            rcvMyEthnicity.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rcvMyEthnicity.setAdapter(new IdentityCheckAdapter(getActivity(), identity));
        }
    }

    private void setUpInterest(ArrayList<InterestItem> interest) {
        if (interest != null && interest.size() > 0) {
            rcvMyInterests.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rcvMyInterests.setAdapter(new InterestCheckAdapter(getActivity(), interest));
        }
    }

    private void setUpimages(UserDetailsItem userDetailsItem) {


        photosList = new ArrayList<PhotosItem>();

        photosList = userDetailsItem.getPhotos();
        totalSize = 0;
        for (int i = 0; i < photosList.size(); i++) {
            PhotosItem photosItem = new PhotosItem();
            photosItem.setPhotosUri("");
            totalSize = totalSize + 1;
            photosItem.setPhotos(photosList.get(i).getPhotos());
            photosItem.setId(photosList.get(i).getId());
            photosList.set(i, photosItem);
        }

        if (photosList.size() == 4) {
            AppContants.IS_UPLOAD_CALLED = true;
        }

        if (photosList.size() < 4) {

            int a = 4 - photosList.size();

            for (int i = 0; i < a; i++) {
                PhotosItem photosItem = new PhotosItem();
                photosItem.setId("");
                photosItem.setPhotos("");
                photosItem.setPhotosUri("");
                photosList.add(photosItem);
            }


            MyPhotosAdapter myPhotosAdapter = new MyPhotosAdapter(profileActivity, EditProfileFragment.this, photosList, imageWidthHeight, this);

            ItemTouchHelper.Callback callback =
                    new ItemMoveCallback(myPhotosAdapter);
            touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(rvMyPhotos);
            rvMyPhotos.setAdapter(myPhotosAdapter);

        } else {

            for (int i = 0; i < photosList.size(); i++) {
                PhotosItem photosItem = new PhotosItem();
                photosItem.setPhotos(photosList.get(i).getPhotos());
                photosItem.setId(photosList.get(i).getId());
                photosItem.setPhotosUri("");
                photosList.set(i, photosItem);
            }

            MyPhotosAdapter myPhotosAdapter = new MyPhotosAdapter(profileActivity, EditProfileFragment.this, photosList, imageWidthHeight, this);

            ItemTouchHelper.Callback callback =
                    new ItemMoveCallback(myPhotosAdapter);
            touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(rvMyPhotos);

            rvMyPhotos.setAdapter(myPhotosAdapter);
        }

        /*try {
            if (userDetailsItem.getPhotos().size() > 0) {


                switch (userDetailsItem.getPhotos().size()) {
                    case 1:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                       setSecondImageInvisible();
                        setThirdImageInvisible();
                        setFourthImageInvisible();
                        break;
                    case 2:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        setThirdImageInvisible();
                        setFourthImageInvisible();
                        break;
                    case 3:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        setThirdimage(userDetailsItem.getPhotos().get(2).getPhotos());
                        setFourthImageInvisible();
                        break;
                    case 4:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        setThirdimage(userDetailsItem.getPhotos().get(2).getPhotos());
                        setFourthimage(userDetailsItem.getPhotos().get(3).getPhotos());
                        break;
                    case 5:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        setThirdimage(userDetailsItem.getPhotos().get(2).getPhotos());
                        setFourthimage(userDetailsItem.getPhotos().get(3).getPhotos());
                        break;
                }
            } else {

                setFirstImageInvisible();
                setSecondImageInvisible();
                setThirdImageInvisible();
                setFourthImageInvisible();
            }

            if (userDetailsItem.getPhotos().size() == 4) {
                AppContants.IS_UPLOAD_CALLED = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

    }

  /*  private void setFourthImageInvisible() {
        ivRemoveFourthImage.setVisibility(View.GONE);
        ivAddFourthImage.setVisibility(View.VISIBLE);
    }

    private void setThirdImageInvisible() {
        ivRemoveThirdImage.setVisibility(View.GONE);
        ivAddThirdImage.setVisibility(View.VISIBLE);
    }

    private void setSecondImageInvisible() {
        ivRemoveSecondImage.setVisibility(View.GONE);
        ivAddSecondImage.setVisibility(View.VISIBLE);
    }

    private void setFirstImageInvisible() {
        ivRemoveFirstImage.setVisibility(View.GONE);
        ivAddFirstImage.setVisibility(View.VISIBLE);
    }*/

   /* private void setFirstimage(String imageUrl) {

        if (imageUrl != null && imageUrl.length() > 0) {
            Glide.with(getActivity())
                    .load(imageUrl)
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.img_placeholder)
                            .error(R.drawable.img_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .override(imageWidthHeight, imageWidthHeight))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ivRemoveFirstImage.setVisibility(View.VISIBLE);
                            ivAddFirstImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivFirst);
        } else {
            ivRemoveFirstImage.setVisibility(View.GONE);
            ivAddFirstImage.setVisibility(View.VISIBLE);
        }
    }

    private void setSecondimage(String imageUrl) {

        if (imageUrl != null && imageUrl.length() > 0) {

            Glide.with(getActivity())
                    .load(imageUrl)
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.img_placeholder)
                            .error(R.drawable.img_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .override(imageWidthHeight, imageWidthHeight))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ivRemoveSecondImage.setVisibility(View.VISIBLE);
                            ivAddSecondImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivSecond);
        } else {
            ivRemoveSecondImage.setVisibility(View.GONE);
            ivAddSecondImage.setVisibility(View.VISIBLE);
        }
    }

    private void setThirdimage(String imageUrl) {

        if (imageUrl != null && imageUrl.length() > 0) {
            Glide.with(getActivity())
                    .load(imageUrl)
                    .load(imageUrl)
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.img_placeholder)
                            .error(R.drawable.img_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .override(imageWidthHeight, imageWidthHeight))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ivRemoveThirdImage.setVisibility(View.VISIBLE);
                            ivAddThirdImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivThird);
        } else {
            ivRemoveThirdImage.setVisibility(View.GONE);
            ivAddThirdImage.setVisibility(View.VISIBLE);
        }
    }

    private void setFourthimage(String imageUrl) {

        if (imageUrl != null && imageUrl.length() > 0) {
            Glide.with(getActivity())
                    .load(imageUrl)
                    .load(imageUrl)
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.img_placeholder)
                            .error(R.drawable.img_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .override(imageWidthHeight, imageWidthHeight))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ivRemoveFourthImage.setVisibility(View.VISIBLE);
                            ivAddFourthImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivFourth);
        } else {
            ivRemoveFourthImage.setVisibility(View.GONE);
            ivAddFourthImage.setVisibility(View.VISIBLE);
        }
    }*/


    // calling ws for update profile

    public void updateProfileAPI() {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(getActivity(), PrefsKey.USER_ID, ""));

        map.put(WSKey.FIRST_NAME, edtFirstName.getText().toString().trim());
        map.put(WSKey.LAST_NAME, edtLastName.getText().toString().trim());
        map.put(WSKey.DATE_OF_BIRTH, Util.getYYYYMMDDfromMDY(edtDob.getText().toString().trim()));
        map.put(WSKey.CITY_NAME, cityName);
        map.put(WSKey.STATE_NAME, stateName);
        map.put(WSKey.STATE_SHORT_NAME, stateShortName);
        map.put(WSKey.EVENT_DATA, getSelectedEventsIds().toString()); // ["1","2","3","5"]
        map.put(WSKey.INTEREST_DATA, getSelectedInterestList().toString()); // ["1","2","3","5"]
        map.put(WSKey.SOCIAL_LIFE_DATA, getSelectedSocialLifetList().toString()); // ["1""]
        map.put(WSKey.GENDER, userDetailsItem.getGender()); // "1"
        map.put(WSKey.IDENTITY_ID, getSelectedNationalityId()); // 1
        map.put(WSKey.ZIPCODE, edtZipCode.getText().toString().trim()); // 1
        map.put(WSKey.INSTAGRAM_ID, instagramid); // 1
        map.put(WSKey.INSTAGRAM_ACCESS_TOKEN, instagramAccessToken); // 1
        if (edtInterestAnswer.getText() != null &&edtInterestAnswer.getText().toString().length() > 0) {
            map.put(WSKey.INTEREST_TEXT,  Util.gifEncode(edtInterestAnswer.getText().toString().trim()));
        }

        map.put(WSKey.PERSONALITY_TEXT, Util.gifEncode(edtPersonalityText.getText().toString().trim()));
        map.put(WSKey.HEADER_BIO, Util.gifEncode(edtBioData.getText().toString().trim()));

        /*ArrayList<File> listUpload = new ArrayList<>();
        for (int i = 0; i < listAddPhoto.size(); i++) {
            listUpload.add(listAddPhoto.get(i).getFile());
        }*/

        HashMap<String, Object> mapFiles = new HashMap<>();


        for (int i = 0; i < photosList.size(); i++) {

            if (!photosList.get(i).getPhotosUri().equals("")) {

                try {
                    map.put("photos[" + i + "][id]", "");
                    mapFiles.put("photos[" + i + "][file]", new Compressor(profileActivity).compressToFile(new File(Util.stringToUri(photosList.get(i).getPhotosUri()).getPath())));
                    map.put("photos[" + i + "][position]", "" + i);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                map.put("photos[" + i + "][id]", "" + photosList.get(i).getId());
                map.put("photos[" + i + "][position]", "" + i);
            }
        }


        new CallNetworkRequest().uploadFilesHashMap(getActivity(), true, "update user profile", AUTH_VALUE, WSUrl.POST_UPDATE_USER_PROFILE, map, mapFiles,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            ProfileDetailsResponse profileDetailsResponse = gson.fromJson(response, ProfileDetailsResponse.class);

                            if (profileDetailsResponse.isFlag()) {
//                            Toast.show(ProfileActivity.this, commonResponse.getMessage());

                                if (profileDetailsResponse.getUserDetails() != null && profileDetailsResponse.getUserDetails().size() > 0) {

                                    UserDetailsItem userDetailsItem = profileDetailsResponse.getUserDetails().get(0);

                                    Prefs.setString(profileActivity, PrefsKey.CITY_NAME, cityName);
                                    Prefs.setString(profileActivity, PrefsKey.STATE_NAME, stateName);
                                    Prefs.setString(profileActivity, PrefsKey.STATE_SHORT_NAME, stateShortName);

                                    if (userDetailsItem.getFirstName() != null && userDetailsItem.getFirstName().trim().length() > 0) {
                                        Prefs.setString(getActivity(), PrefsKey.BASIC_INFO_FIRST_NAME, userDetailsItem.getFirstName());
                                    }

                                    if (userDetailsItem.getLastName() != null && userDetailsItem.getLastName().trim().length() > 0) {
                                        Prefs.setString(getActivity(), PrefsKey.BASIC_INFO_LAST_NAME, userDetailsItem.getLastName());
                                    }

                                    if (userDetailsItem.getDateOfBirth() != null && userDetailsItem.getDateOfBirth().trim().length() > 0) {
                                        Prefs.setString(getActivity(), PrefsKey.BASIC_INFO_DATE_OF_BIRTH, userDetailsItem.getDateOfBirth());
                                    }

                                    if (userDetailsItem.getPhoneNumber() != null && userDetailsItem.getPhoneNumber().trim().length() > 0) {
                                        Prefs.setString(getActivity(), PrefsKey.BASIC_INFO_PHONE_NUMBER, userDetailsItem.getPhoneNumber());
                                    }

                                    if (userDetailsItem.getEmailId() != null && userDetailsItem.getEmailId().trim().length() > 0) {
                                        Prefs.setString(getActivity(), PrefsKey.BASIC_INFO_EMAIL, userDetailsItem.getEmailId());
                                    }

                                    if (userDetailsItem.getProfilePic() != null && userDetailsItem.getProfilePic().trim().length() > 0) {
                                        Prefs.setString(getActivity(), PrefsKey.PROFILE_PIC, userDetailsItem.getProfilePic());
                                    } else {
                                        Prefs.setString(getActivity(), PrefsKey.PROFILE_PIC, "");
                                    }


                                    AppContants.IS_UPLOAD_CALLED = true;

                                    profileActivity.finish();

                                   /* if (AppContants.IS_FROM_SPLASH) {
                                        Intent intent = new Intent(profileActivity, DashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        profileActivity.finish();
                                    } else {
                                         profileActivity.finish();
                                    }*/

                                }

                            } else {
                                Toast.show(getActivity(), profileDetailsResponse.getMessage());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e("error", error.getErrorBody());
                        Toast.show(profileActivity, getString(R.string.error_contact_server));
                    }
                });
    }


    //calling ws for delete images from server

    public void deleteImageAPI(String imageId, final int position) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(getActivity(), PrefsKey.USER_ID, ""));
        map.put(WSKey.IMG_ID, imageId);

        new CallNetworkRequest().postResponse(getActivity(), true, "delete user image", AUTH_VALUE, WSUrl.POST_DELETE_USER_IMAGE, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            CommonResponse commonResponse = new Gson().fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
/*
                                PhotosItem photosItem = new PhotosItem();
                                photosItem.setId("");
                                photosItem.setPhotos("");
                                photosItem.setPhotosUri("");
                                photosList.set(position,photosItem);

                                updatePhotoAdapter();*/

                                //  totalSize = totalSize - 1;

                               /* switch (position) {
                                    case 0:
                                        userDetailsItem.getPhotos().get(0).setPhotos("");
                                        ivFirst.setImageResource(0);
                                        ivRemoveFirstImage.setVisibility(View.GONE);
                                        ivAddFirstImage.setVisibility(View.VISIBLE);
                                        break;

                                    case 1:
                                        userDetailsItem.getPhotos().get(1).setPhotos("");
                                        ivSecond.setImageResource(0);
                                        ivRemoveSecondImage.setVisibility(View.GONE);
                                        ivAddSecondImage.setVisibility(View.VISIBLE);
                                        break;

                                    case 2:
                                        userDetailsItem.getPhotos().get(2).setPhotos("");
                                        ivThird.setImageResource(0);
                                        ivRemoveThirdImage.setVisibility(View.GONE);
                                        ivAddThirdImage.setVisibility(View.VISIBLE);
                                        break;

                                    case 3:
                                        userDetailsItem.getPhotos().get(3).setPhotos("");
                                        ivFourth.setImageResource(0);
                                        ivRemoveFourthImage.setVisibility(View.GONE);
                                        ivAddFourthImage.setVisibility(View.VISIBLE);
                                        break;
                                }
                                totalSize = totalSize - 1;
                                AppContants.IS_UPLOAD_CALLED = false;*/


                            } else {
                                Toast.show(getActivity(), commonResponse.getMessage());
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(profileActivity, getString(R.string.error_contact_server));

                    }
                });
    }


    private JSONArray getSelectedEventsIds() {

        JSONArray selectedIds = new JSONArray();
        if (eventsListAdded != null && eventsListAdded.size() > 0) {
            for (int i = 0; i < eventsListAdded.size(); i++) {
                selectedIds.put(eventsListAdded.get(i).getId());
            }
        }
        return selectedIds;
    }

    private JSONArray getSelectedInterestList() {

        JSONArray selectedIds = new JSONArray();
        if (userDetailsItem.getInterest() != null && userDetailsItem.getInterest().size() > 0) {
            for (int i = 0; i < userDetailsItem.getInterest().size(); i++) {

                if (userDetailsItem.getInterest().get(i).getIsSelected() == 1) {
                    selectedIds.put(userDetailsItem.getInterest().get(i).getId());
                }
            }
        }
        return selectedIds;
    }

    private JSONArray getSelectedSocialLifetList() {


        JSONArray selectedIds = new JSONArray();

        if (userDetailsItem.getSocialLife() != null && userDetailsItem.getSocialLife().size() > 0) {
            for (int i = 0; i < userDetailsItem.getSocialLife().size(); i++) {

                if (userDetailsItem.getSocialLife().get(i).getIsSelected() == 1) {
                    selectedIds.put(userDetailsItem.getSocialLife().get(i).getId());
                }
            }
        }

        return selectedIds;
    }

    private String getSelectedNationalityId() {

        String nationalityId = "";

        if (userDetailsItem.getIdentity() != null && userDetailsItem.getIdentity().size() > 0) {
            for (int i = 0; i < userDetailsItem.getIdentity().size(); i++) {

                if (userDetailsItem.getIdentity().get(i).getIsSelected() == 1) {
                    nationalityId = userDetailsItem.getIdentity().get(i).getId();
                    break;
                }
            }
        }

        return nationalityId;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            CropImage.activity(selectedImage).setAspectRatio(1, 1)
                    .start(profileActivity, this);
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

                Toast.show(profileActivity, "Error while capturing image");

                return;

            }

            try {
                Uri selectedImage = Uri.fromFile(f);
                CropImage.activity(selectedImage).setAspectRatio(1, 1)
                        .start(profileActivity, this);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {

                    Uri resultUri = result.getUri();

                    /*AddPhotoModel addPhotoModel = new AddPhotoModel();
                    addPhotoModel.setUri(resultUri);
                    File compressedImageFile = new Compressor(getActivity()).compressToFile(new File(resultUri.getPath()));
                    addPhotoModel.setFile(compressedImageFile);
                    if (listAddPhoto == null) {
                        listAddPhoto = new ArrayList<>();
                    }
                    listAddPhoto.add(addPhotoModel);
//                    Collections.reverse(listAddPhoto);*/


                    setPhotosToView(clickedAddImageIndex, Util.uriToString(resultUri));

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (requestCode == FINDHOMECITY) {
            if (resultCode == RESULT_OK) {
                try {
                    // Place place = PlaceAutocomplete.getPlace(profileActivity, data);
                    // wsGetCityState(place.getLatLng().latitude, place.getLatLng().longitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void setPhotosToView(int clickedAddImageIndex, String uri) {


        PhotosItem photosItem = new PhotosItem();
        photosItem.setPhotosUri(uri);
        photosItem.setId("");
        photosItem.setPhotos("");
        photosList.set(clickedAddImageIndex, photosItem);

        if (photosList.size() > 0) {

            MyPhotosAdapter myPhotosAdapter = new MyPhotosAdapter(profileActivity, EditProfileFragment.this, photosList, imageWidthHeight, this);

            ItemTouchHelper.Callback callback =
                    new ItemMoveCallback(myPhotosAdapter);
            touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(rvMyPhotos);

            rvMyPhotos.setAdapter(myPhotosAdapter);

        }
        totalSize = totalSize + 1;
        AppContants.IS_UPLOAD_CALLED = false;


/*
        if (clickedAddImageIndex == 0) {
            ivFirst.setImageURI(uri);
            ivFirst.setTag(uri);
            ivRemoveFirstImage.setVisibility(View.VISIBLE);
            ivAddFirstImage.setVisibility(View.GONE);
        } else if (clickedAddImageIndex == 1) {
            ivSecond.setImageURI(uri);
            ivSecond.setTag(uri);
            ivRemoveSecondImage.setVisibility(View.VISIBLE);
            ivAddSecondImage.setVisibility(View.GONE);
        } else if (clickedAddImageIndex == 2) {
            ivThird.setImageURI(uri);
            ivThird.setTag(uri);
            ivRemoveThirdImage.setVisibility(View.VISIBLE);
            ivAddThirdImage.setVisibility(View.GONE);
        } else if (clickedAddImageIndex == 3) {
            ivFourth.setImageURI(uri);
            ivFourth.setTag(uri);
            ivRemoveFourthImage.setVisibility(View.VISIBLE);
            ivAddFourthImage.setVisibility(View.GONE);
        }
        totalSize = totalSize + 1;
        AppContants.IS_UPLOAD_CALLED = false;*/
    }

   /* public void deletePhoto(Uri uri, int clickedAddImageIndex) {

        Iterator<AddPhotoModel> iterator = listAddPhoto.iterator();
        while (iterator.hasNext()) {
            AddPhotoModel value = iterator.next();

            if (value.getUri().equals(uri)) {
                iterator.remove();

                if (clickedAddImageIndex == 0) {
                    ivFirst.setImageResource(0);
                    ivFirst.setTag(null);
                    ivRemoveFirstImage.setVisibility(View.GONE);
                    ivAddFirstImage.setVisibility(View.VISIBLE);
                } else if (clickedAddImageIndex == 1) {
                    ivSecond.setImageResource(0);
                    ivSecond.setTag(null);
                    ivRemoveSecondImage.setVisibility(View.GONE);
                    ivAddSecondImage.setVisibility(View.VISIBLE);
                } else if (clickedAddImageIndex == 2) {
                    ivThird.setImageResource(0);
                    ivThird.setTag(null);
                    ivRemoveThirdImage.setVisibility(View.GONE);
                    ivAddThirdImage.setVisibility(View.VISIBLE);
                } else if (clickedAddImageIndex == 3) {
                    ivFourth.setImageResource(0);
                    ivFourth.setTag(null);
                    ivRemoveFourthImage.setVisibility(View.GONE);
                    ivAddFourthImage.setVisibility(View.VISIBLE);
                }


                break;
            }
        }
        totalSize = totalSize - 1;
        AppContants.IS_UPLOAD_CALLED = false;

        for (int i = 0; i < listAddPhoto.size(); i++) {
        }
    }*/

    public void addPhoto(final int position) {


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {


                clickedAddImageIndex = position;
                photoPickDialog();

                // CropImage.activity().setAspectRatio(1, 1).start(getActivity(), EditProfileFragment.this);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(getActivity())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(getString(R.string.permission_denied))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private View editEventChip(Context context, final EventItem eventItem) {
        View view = View.inflate(context, R.layout.item_added_event_search, null);
        TextView tvEventItem = view.findViewById(R.id.tvEventItem);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        tvEventItem.setText(eventItem.getTitle());


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlexboxLayout linearParent = (FlexboxLayout) view.getParent().getParent();
                FrameLayout frameChild = (FrameLayout) view.getParent();
                linearParent.removeView(frameChild);
                removeItem(eventItem);
            }
        });
        return view;
    }


    private void removeItem(final EventItem eventItem) {
        Iterator<EventItem> iterator = eventsListAdded.iterator();
        while (iterator.hasNext()) {
            EventItem event = iterator.next();
            if (eventItem.equals(event)) {
                iterator.remove();
                break;
            }
        }
    }


    private void photoPickDialog() {

        final Dialog dialog = new Dialog(profileActivity);
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

               /* Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);*/

            }
        });
    }

    public void captureImage() {

        File f = new File(android.os.Environment
                .getExternalStorageDirectory(), "quirktasticTemp.jpg");

        Uri outUri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // only for gingerbread and newer versions
            outUri = FileProvider.getUriForFile(profileActivity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    f);
        } else
            outUri = Uri.fromFile(f);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);


        startActivityForResult(cameraIntent,
                RESULT_LOAD_CAMERA);

    }


    // call google ws for get city and state name from lat and long
    private void wsGetCityState(String zipcode) {

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&sensor=false&key=" + GOOGLE_MAP_API_KEY;

        new CallNetworkRequest().getResponse(profileActivity, true, "update Eevent", AUTH_VALUE, url,
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

                                                Prefs.setString(profileActivity, PrefsKey.CITY_NAME, cityName);
                                                Prefs.setString(profileActivity, PrefsKey.STATE_NAME, stateName);
                                                Prefs.setString(profileActivity, PrefsKey.STATE_SHORT_NAME, stateShortName);

                                                updateProfileAPI();
                                            } else {
                                                Toast.show(profileActivity, "Please enter valid zipcode");
                                            }
                                        } else {
                                            Toast.show(profileActivity, "Please enter valid zipcode");
                                        }
                                    } else {
                                        Toast.show(profileActivity, "Please enter valid zipcode");
                                    }
                                } else {
                                    Toast.show(profileActivity, "Please enter valid zipcode");
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(profileActivity, getString(R.string.error_contact_server));
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        /*if (totalSize == 4) {
            touchHelper.startDrag(viewHolder);
        }*/
        touchHelper.startDrag(viewHolder);

    }

    private void showProgressDialog(Context context) {
        if (progressDialog != null && !progressDialog.isShowing() && !((Activity) context).isFinishing()) {
            progressDialog.show();
        } else if (!((Activity) context).isFinishing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
