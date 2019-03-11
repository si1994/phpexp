package com.quirktastic.chat.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.chat.adapter.ChatRecycleAdapter;
import com.quirktastic.chat.chatmodel.USERDETAILSItem;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.instachat.emojilibrary.controller.WhatsAppPanel;
import br.com.instachat.emojilibrary.model.layout.WhatsAppPanelEventListener;


public class ChatFragment extends BaseFragment implements WhatsAppPanelEventListener {


    String login_user_id = "";
    boolean show_past_progress = true;
    ArrayList<Integer> list_showdate = new ArrayList<>();
    boolean loading_previous_data = false;
    boolean past_ws_call = true;
    Handler handler;
    Runnable run;
    int storeLastMessageId = 0;
    int storeNewMessageId = 0;

    //List<ChatConversion> chatConversionList = new ArrayList<>();

    List<USERDETAILSItem> list_chat_details = new ArrayList<>();
    List<USERDETAILSItem> simple_list_chat_details = new ArrayList<>();


    private String toUserId = "", toUserImage = "", toUserName = "";
    boolean allow_check_for_new_message = true;
    String isBlock = "0"; // 0 unblock , 1 = block

    boolean isPatch = false;
    boolean isPatchReciveMessage = false;
    int lastPassIdForRemoveDuplicate = 0;

    private ChatProfileActivity chatProfileActivity;


    private RecyclerView rvChatList;
    //private TextView tvSendChat;

    private View rootView;
    // private EditText edtSendChat;
    private RelativeLayout rlChatProgress;

    private ColorGenerator generator;

    private String strChatUSerName;
    private String strChatUserPic = "";
    private String isBlocked = "";
    private String isUnFriend = "";
    private LinearLayoutManager linearLayoutManager;
    private WhatsAppPanel mBottomPanel;
    private boolean isFirstTime = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatProfileActivity = (ChatProfileActivity) getActivity();
        list_chat_details = new ArrayList<>();
        simple_list_chat_details = new ArrayList<>();
        if (getArguments() != null) {
            login_user_id = getArguments().getString(IBundleKey.TO_USER_ID);
            strChatUSerName = getArguments().getString(IBundleKey.CHAT_USER_NAME);
            strChatUserPic = getArguments().getString(IBundleKey.CHAT_USER_PIC);
            isBlocked = getArguments().getString(IBundleKey.IS_BLOCKED);
            isUnFriend = getArguments().getString(IBundleKey.IS_UNFRIEND);
        }
        handler = new Handler();

        generator = ColorGenerator.MATERIAL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        init();
        getChatConversion(true, 1, "0", "0"); // // 0 = new , 1 = old
        checkForIfNewMessagesAriived();
        return rootView;
    }

    private void init() {
        this.mBottomPanel = new WhatsAppPanel(chatProfileActivity, rootView, this, R.color.colorPrimary);
        rvChatList = (RecyclerView) rootView.findViewById(R.id.rvChatList);
        // edtSendChat = (EditText) rootView.findViewById(R.id.edtSendChat);
        // tvSendChat = (TextView) rootView.findViewById(R.id.tvSendChat);
        rlChatProgress = (RelativeLayout) rootView.findViewById(R.id.rlChatProgress);

       /* tvSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMyMessage();
            }
        });
        edtSendChat.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edtSendChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    tvSendChat.setVisibility(View.GONE);
                } else {
                    tvSendChat.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        initLayoutManager();

    }

    private void initLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(chatProfileActivity);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvChatList.setLayoutManager(linearLayoutManager);
    }


    private void checkForIfNewMessagesAriived() {

        Logger.e("check_for_new_msg_1__", list_chat_details.size() + "");
        run = new Runnable() {
            @Override
            public void run() {
                Logger.e("check_for_new_msg_1.1__", list_chat_details.size() + "");
                //check_for_new_message(false, 1, storeNewMessageId, "0");
                if (list_chat_details.size() > 0) {
                    check_for_new_message(false, 1, storeNewMessageId, "0");
                } else {
                    check_for_new_message(false, 1, 0, "0");
                }
            }
        };
        //run.run();
        handler.removeCallbacks(run);
        handler.postDelayed(run, 5000);
    }

    private void getChatConversion(boolean showProgress, int pageNo, String msgId, String isNew) {

//        rvChatList.setLayoutManager(new LinearLayoutManager(chatProfileActivity));
        rvChatList.setHasFixedSize(false);

        loading_previous_data = true;

        String url = WSUrl.GET_USER_CHAT_MSG_LIST + Prefs.getString(chatProfileActivity, PrefsKey.USER_ID, "")
                + WSUrl.TO_USER_CHAT_MSG_LIST + login_user_id + WSUrl.LAST_ID + msgId + WSUrl.IS_NEW + isNew;

        new CallNetworkRequest().getResponse(chatProfileActivity, showProgress, "userChatMsgList", AppContants.AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            rlChatProgress.setVisibility(View.GONE);
                            Logger.e("resp_receive_msg_", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("FLAG").equals("true")) {


                                JSONArray details_arr = obj.getJSONArray("USER_DETAILS");


                                parsejsonArray(details_arr);
                                loading_previous_data = false;


                            } else {
                                loading_previous_data = false;

                            }
                        } catch (JSONException e) {

                            loading_previous_data = false;
                            e.printStackTrace();
                            rlChatProgress.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        //   Toast.show(chatProfileActivity, getString(R.string.error_contact_server));
                        loading_previous_data = false;
                        rlChatProgress.setVisibility(View.GONE);
                    }
                });

    }

    public void parsejsonArray(JSONArray arr) {
        try {

            Logger.e("nirav_add_chat_1__", "getChatConversion");

            list_chat_details.clear();
            ArrayList<USERDETAILSItem> list_temp = new ArrayList();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Gson gson = new Gson();
                USERDETAILSItem fl = new USERDETAILSItem();
                fl = gson.fromJson(obj.toString(), USERDETAILSItem.class);
                setSenderDetails(fl);
                setRecvDetails(fl);

                list_temp.add(fl);
            }

            if (list_temp.size() > 0) {
                storeNewMessageId = Integer.parseInt(list_temp.get(0).getId());
            }

            Collections.reverse(list_temp);
            for (int i = 0; i < list_temp.size(); i++) {
                list_chat_details.add(list_temp.get(list_temp.size() - i - 1));
                // db.addContact(list_temp.get(list_temp.size() - i - 1));
            }

            if (list_temp.size() > 0) {
                //storeLastMessageId = Integer.parseInt(list_chat_details.get(0).getId());
                storeLastMessageId = Integer.parseInt(list_temp.get(0).getId());
            }
            Logger.e("check_msg_id_000_:", storeLastMessageId + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        create_showdate_list();
        setAdapter_chat();
    }

    private void check_for_new_message(boolean showProgress, int pageNo, int msgId, String isNew) {

        if (!allow_check_for_new_message) {
            return;
        }

        loading_previous_data = true;

        String url = WSUrl.GET_USER_CHAT_MSG_LIST + Prefs.getString(chatProfileActivity, PrefsKey.USER_ID, "")
                + WSUrl.TO_USER_CHAT_MSG_LIST + login_user_id + WSUrl.LAST_ID + msgId + WSUrl.IS_NEW + isNew;


        new CallNetworkRequest().getResponse(chatProfileActivity, showProgress, "userChatMsgList", AppContants.AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {

                            //Logger.e(para"resp_receive_msg_", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("FLAG").equals("true")) {

                                JSONArray details_arr = obj.getJSONArray("USER_DETAILS");
                                parsejsonArray_new(details_arr);


                            } else {


                            }
                        } catch (JSONException e) {
                            loading_previous_data = false;
                            e.printStackTrace();
                        } finally {
                            show_past_progress = false;
                            loading_previous_data = false;
                            try {
//                                rlChatProgress.setVisibility(View.VISIBLE);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Logger.e("check_for_new_msg_2__", list_chat_details.size() + "");
                                        if (list_chat_details.size() > 0) {
                                            check_for_new_message(false, 1, storeNewMessageId, "0");
                                        } else {
                                            isPatchReciveMessage = true;
                                            check_for_new_message(false, 1, 0, "0");
                                        }
//                                        rlChatProgress.setVisibility(View.GONE);
                                    }
                                },3000);

                            } catch (Exception e) {
                                e.printStackTrace();
                                rlChatProgress.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onError(ANError error) {
//                        Toast.show(chatProfileActivity,getString(R.string.error_contact_server));
                        loading_previous_data = false;
                        rlChatProgress.setVisibility(View.GONE);
                    }
                });

    }


    public void parsejsonArray_new(JSONArray arr) {
        try {

            //Chat_details_db db = new Chat_details_db(Chat_Message_Conversion_Activity.this);
            final ArrayList<USERDETAILSItem> list_temp = new ArrayList<USERDETAILSItem>();
            //Logger.w("table", "deleted");
            list_temp.clear();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Gson gson = new Gson();
                USERDETAILSItem fl = new USERDETAILSItem();
                fl = gson.fromJson(obj.toString(), USERDETAILSItem.class);
                setSenderDetails(fl);
                setRecvDetails(fl);
                list_temp.add(fl);
                //db.addContact(list_chat_details.get(i));
            }
            list_chat_details.clear();
            list_chat_details = new ArrayList(simple_list_chat_details);
            if (list_temp.size() > 0) {
                storeNewMessageId = Integer.parseInt(list_temp.get(0).getId());
            }
            for (int i = 0; i < list_temp.size(); i++) {//setAdapter_chat_notify(list.size());
                if (!list_chat_details.contains(list_temp.get(i).getId())) {

                    list_chat_details.add(0, list_temp.get(list_temp.size() - i - 1));
                }
                // db.addContact(list_temp.get(list_temp.size() - i - 1));
            }

            chatProfileActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Logger.e("nirav_add_chat_2__", "runOnUiThread");

                    LinearLayoutManager llm = (LinearLayoutManager) rvChatList.getLayoutManager();

//                    int pos = rvChatList.getAdapter().getItemCount() - 1 - list_temp.size();
                    create_showdate_list();

                    if (llm.findFirstVisibleItemPosition() == 0) {
                        setAdapter_chat();
                        rvChatList.scrollToPosition(0);
                    } else {
                        int posi = llm.findLastVisibleItemPosition() + list_temp.size();
                        setAdapter_chat();
                        rvChatList.scrollToPosition(posi);


                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void create_showdate_list() {

        simple_list_chat_details.clear();
        simple_list_chat_details = new ArrayList(list_chat_details);

        if (true) {
            return;
        }
        int index = 0;
        ArrayList<USERDETAILSItem> list = new ArrayList();

        simple_list_chat_details.clear();
        simple_list_chat_details = new ArrayList(list_chat_details);
        list.clear();
        list_showdate.clear();
        for (int i = 0; i < list_chat_details.size(); i++) {
            if (i == list_chat_details.size() - 1) {
                //Logger.w("msg_i at end", "" + i + ":" + list_chat_details.get(i).getMessage());
                list.add(list_chat_details.get(i));//show msg
                list.add(list_chat_details.get(i));//show dates
                list_showdate.add(list.size() - 1);
                break;
            }
            list.add(list_chat_details.get(i));

            //Logger.w("msg_i at normal", "" + i +":"+list_chat_details.get(i).getMessage());
            if (!list_chat_details.get(i).getCreatedDate().equalsIgnoreCase(list_chat_details.get(i + 1).getCreatedDate())) {
                //Logger.w("msg_i at date",""+i+":"+list_chat_details.get(i).getMessage());
                list.add(list_chat_details.get(i));
                list_showdate.add(list.size() - 1);
            }

        }
        list_chat_details = list;
    }


    public void setAdapter_chat() {


        rvChatList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(chatProfileActivity);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);

        rvChatList.setLayoutManager(llm);
        // TODO NIRAV
        //Collections.reverse(list_chat_details);
        String recv_profile_pic = "";
        for (int i = 0; i < list_chat_details.size(); i++) {
            if (!list_chat_details.get(i).getFromUserId().equalsIgnoreCase(Prefs.getString(chatProfileActivity, PrefsKey.USER_ID, ""))) {
                recv_profile_pic = list_chat_details.get(i).getRecvProfilePic();
                break;
            }
        }

        rvChatList.setAdapter(new ChatRecycleAdapter(chatProfileActivity, list_chat_details, recv_profile_pic));
        rvChatList.addOnScrollListener(scroll_listener);
        rvChatList.scrollToPosition(0);
        //binding.recyclerView.addOnScrollListener(scroll_listener);
   //     rvChatList.smoothScrollToPosition(0);
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                rvChatList.smoothScrollToPosition(0);
//            }
//        }, 200);


    }

    RecyclerView.OnScrollListener scroll_listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
//            getPastMessageListinBG(false, 1, storeLastMessageId);

            if (llm.findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1 && !loading_previous_data) {
                Logger.w("call webservices", "load_data : " + list_chat_details.size());
                System.out.println("load_data============>"+list_chat_details.size());
               if (list_chat_details.size() > 0) {

                   System.out.println("storeLastMessageId=================>"+storeLastMessageId);

                    getPastMessageListinBG(false, 1, storeLastMessageId);
                }
            }
        }
    };


    private void getPastMessageListinBG(boolean showProgress, int pageNo, final int msgId) {


        loading_previous_data = true;
        show_past_progress = true;


        rlChatProgress.setVisibility(View.GONE);
        String url = "";

        Logger.e("hahahahaha_", isPatch + " : " + isPatchReciveMessage + " : " + storeNewMessageId + " : " + storeLastMessageId + " : " + msgId);
        if (isPatch || isPatchReciveMessage) {
            if (isPatch) {
                isPatch = false;
            } else if (isPatchReciveMessage) {
                isPatchReciveMessage = false;
            }

            url = WSUrl.GET_USER_CHAT_MSG_LIST + Prefs.getString(chatProfileActivity, PrefsKey.USER_ID, "")
                    + WSUrl.TO_USER_CHAT_MSG_LIST + login_user_id + WSUrl.LAST_ID + storeNewMessageId + WSUrl.IS_NEW + "1";
        } else {

            url = WSUrl.GET_USER_CHAT_MSG_LIST + Prefs.getString(chatProfileActivity, PrefsKey.USER_ID, "")
                    + WSUrl.TO_USER_CHAT_MSG_LIST + login_user_id + WSUrl.LAST_ID + msgId + WSUrl.IS_NEW + "1";
        }

        new CallNetworkRequest().getResponse(chatProfileActivity, showProgress, "userChatMsgList", AppContants.AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            rlChatProgress.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            if (obj.optString("FLAG").equals("true")) {
                                JSONArray details_arr = obj.getJSONArray("USER_DETAILS");
                                if (details_arr.length() > 0) {          //no error in topsdemo.in but was seen in local
                                    parsejsonArray_past(details_arr);
                                    //loading_previous_data = false;
                                } else {
                                    show_past_progress = false;
                                    Logger.w("remove progress", "from chat recycler");
                                    past_ws_call = false;
                                    //recyclerView.getAdapter().notifyItemChanged(recyclerView.getAdapter().getItemCount()-1);
                                    rvChatList.getAdapter().notifyDataSetChanged();
                                }
                            } else {
                                loading_previous_data = false;
                                //edited by dev
                                show_past_progress = false;
                                rvChatList.getAdapter().notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            loading_previous_data = false;
                            show_past_progress = false;
                            rvChatList.getAdapter().notifyDataSetChanged();
                            e.printStackTrace();
                            rlChatProgress.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onError(ANError error) {
//                        Toast.show(chatProfileActivity,getString(R.string.error_contact_server));
                        loading_previous_data = false;
                        rlChatProgress.setVisibility(View.GONE);
                    }
                });


    }


    public void parsejsonArray_past(JSONArray arr) {
        try {
            //Chat_details_db db = new Chat_details_db(Chat_Message_Conversion_Activity.this);
            ArrayList<USERDETAILSItem> list_temp = new ArrayList<USERDETAILSItem>();
            //Logger.w("table", "deleted");
            list_temp.clear();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Gson gson = new Gson();
                USERDETAILSItem fl = new USERDETAILSItem();
                fl = gson.fromJson(obj.toString(), USERDETAILSItem.class);
                list_temp.add(fl);
                //db.addContact(fl);
            }
            list_chat_details.clear();
            list_chat_details = new ArrayList(simple_list_chat_details);

            Logger.e("nirav_add_chat_3__", "parsejsonArray_past");

            Collections.reverse(list_temp);

            if (list_temp.size() > 0) {
                storeLastMessageId = Integer.parseInt(list_temp.get(0).getId());
            }

            for (int i = 0; i < list_temp.size(); i++) {
                if (!list_chat_details.contains(list_temp.get(i).getId())) {
                    list_chat_details.add(list_temp.get(list_temp.size() - i - 1));
                }
            }

            create_showdate_list();
            LinearLayoutManager llm = (LinearLayoutManager) rvChatList.getLayoutManager();
            int pos = llm.findLastVisibleItemPosition();
            setAdapter_chat();
            System.out.println("isFirstTime==============>"+isFirstTime);

            rvChatList.scrollToPosition(pos);
//            rvChatList.scrollToPosition(list_chat_details.size()-1);
            Logger.w("scrolled to pos", "" + pos);

            loading_previous_data = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendMyMessage() {
        if (isUnFriend.equals("3")) {
            Toast.show(chatProfileActivity, "You can't send message because " + strChatUSerName + " is not in your friend list ");
        } else if (isBlocked.equals("1")) {
            Toast.show(chatProfileActivity, "You have been Blocked or " + strChatUSerName + " has been blocked by you");
        } else if (isUnFriend.equals("0")) {
            Toast.show(chatProfileActivity, "You can't send message because " + strChatUSerName + " haven't accepted your request");
        } else if (isUnFriend.equals("2")) {
            Toast.show(chatProfileActivity, "You can't send message because " + strChatUSerName + " rejected your request");
        } else if (this.mBottomPanel.getText().trim().isEmpty()) {
            Toast.show(chatProfileActivity, "Please enter a message");
        } else {
            rvChatList.smoothScrollToPosition(0);
            callSendMessageWs(false, 0, URLEncoder.encode(this.mBottomPanel.getText().trim()).replace("+", "%20")
                    .replace("%40", "@")
                    .replace("*", "%2A"));
            mBottomPanel.setText("");
        }

    }


    public void sendMyGif(File file) {
        if (isUnFriend.equals("3")) {
            Toast.show(chatProfileActivity, "You can't send message because " + strChatUSerName + " is not in your friend list ");
        } else if (isBlocked.equals("1")) {
            Toast.show(chatProfileActivity, "You have been Blocked or " + strChatUSerName + " has been blocked by you");
        } else if (isUnFriend.equals("0")) {
            Toast.show(chatProfileActivity, "You can't send message because " + strChatUSerName + " haven't accepted your request");
        } else if (isUnFriend.equals("2")) {
            Toast.show(chatProfileActivity, "You can't send message because " + strChatUSerName + " rejected your request");
        } else {
            rvChatList.smoothScrollToPosition(0);
            callSendGifWs(file);
            mBottomPanel.setText("");
        }

    }


    private void callSendMessageWs(boolean showProgress, int pageNo, String message) {


        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(chatProfileActivity, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, login_user_id);
        map.put(WSKey.MASSAGE, message);
        new CallNetworkRequest().postResponse(chatProfileActivity, false, "sendMessage", AppContants.AUTH_VALUE,
                WSUrl.POST_SEND_MASSAGE, map, new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                parseSendMessage(response);
                            } else {
                                Toast.show(chatProfileActivity, getString(R.string.error_contact_server));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.show(chatProfileActivity, getString(R.string.error_contact_server));
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(chatProfileActivity, getString(R.string.error_contact_server));
                    }
                });
    }


    private void callSendGifWs(File file) {


        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(chatProfileActivity, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, login_user_id);

        new CallNetworkRequest().uploadGif(chatProfileActivity, false, "sendGif", AppContants.AUTH_VALUE,
                WSUrl.POST_SEND_MASSAGE, map, file, new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                parseSendMessage(response);
                            } else {
                                Toast.show(chatProfileActivity, getString(R.string.error_contact_server));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.show(chatProfileActivity, getString(R.string.error_contact_server));
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(chatProfileActivity, getString(R.string.error_contact_server));
                    }
                });
    }

    private void parseSendMessage(String response) throws Exception {

        mBottomPanel.setText("");
        JSONObject obj = new JSONObject(response);
        if (!obj.getString("FLAG").equalsIgnoreCase("true")) {
            Toast.show(chatProfileActivity, obj.optString("MESSAGE"));
            return;
        }

//        rvChatList.scrollToPosition(list_chat_details.size() - 1);

//        initLayoutManager();

        if (storeNewMessageId == 0 && !isPatch) {
            isPatch = true;

        }


    }

    @Override
    public void onStop() {
        //  Util.hideKeyboard(chatProfileActivity, edtSendChat);
        handler.removeCallbacks(run);

        super.onStop();


    }

    @Override
    public void onDestroy() {
        // Util.hideKeyboard(chatProfileActivity, edtSendChat);
        if (handler != null && run != null) {
            handler.removeCallbacks(run);
        }
        AndroidNetworking.cancel("userChatMsgList");
        allow_check_for_new_message = false;
        super.onDestroy();


    }

    @Override
    public void onDestroyView() {
        //  Util.hideKeyboard(chatProfileActivity, edtSendChat);
        if (handler != null && run != null) {
            handler.removeCallbacks(run);
        }
        AndroidNetworking.cancel("userChatMsgList");
        allow_check_for_new_message = false;
        super.onDestroyView();
    }

    private void setSenderDetails(USERDETAILSItem fl) {
        fl.setSenderFullName(Prefs.getString(chatProfileActivity, PrefsKey.BASIC_INFO_LAST_NAME, "")
                + " " + Prefs.getString(chatProfileActivity, PrefsKey.BASIC_INFO_LAST_NAME, ""));
        fl.setSenderAlphabetCapital(Prefs.getString(chatProfileActivity, PrefsKey.BASIC_INFO_LAST_NAME, "")
                + " " + Prefs.getString(chatProfileActivity, PrefsKey.BASIC_INFO_LAST_NAME, ""));


    }

    private void setRecvDetails(USERDETAILSItem fl) {

        fl.setRecvFullName(strChatUSerName);

        fl.setRecvProfilePic(strChatUserPic);


    }

    @Override
    public void onSendClicked() {
        sendMyMessage();
    }

    @Override
    public void onSendGif(File file) {
        sendMyGif(file);
    }


    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }


    // https://stackoverflow.com/questions/18255458/trigger-an-event-when-mediaplayer-stops
    // file:///home/tops/Nirav/GitLabNew/Sequin%20closet/slices/Alamaaree%20-%20App/Alamaaree_TOPS-TTF_Icons/demo.html
}