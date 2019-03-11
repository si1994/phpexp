package com.quirktastic.onboard.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.adapter.EventListSearchAdapter;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.onboard.model.events.EventListItem;
import com.quirktastic.onboard.model.events.EventsListResponse;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class EventsActivity extends AppCompatActivity {


    @BindView(R.id.tvEventsTitleLabel)
    TextView tvEventsTitleLabel;
    @BindView(R.id.tvEventsNameLabel)
    TextView tvEventsNameLabel;

    @BindView(R.id.llEventsBottom)
    LinearLayout llEventsBottom;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;


    @BindView(R.id.flAddEventChip)
    FlexboxLayout flAddEventChip;

    private String TAG = getClass().getSimpleName();
    private ArrayList<EventListItem> eventsList;
    private ArrayList<EventListItem> eventsListAdded;
    private EventListSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);


        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //do something
                    Util.hideKeyboard(EventsActivity.this, autoCompleteTextView);
                }
                return false;
            }
        });


        getEventsListAPI();
    }

    @OnClick({R.id.llEventsBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llEventsBottom:
                checkAndCallUpdateEventsAPI();
//                redirectContinueScreen();
                break;
        }
    }

    // call ws get all events list
    private void getEventsListAPI() {

        HashMap<String, Object> map = new HashMap<>();
        new CallNetworkRequest().postResponse(EventsActivity.this, true, "eventsList", AUTH_VALUE, WSUrl.POST_EVENTS_LIST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            EventsListResponse eventsListResponse = gson.fromJson(response, EventsListResponse.class);

                            if (eventsListResponse.isFlag()) {
                                if (eventsListResponse.getEventList() != null && eventsListResponse.getEventList().size() > 0) {
                                    eventsList = eventsListResponse.getEventList();
//                                rcvSocialLifeList.setAdapter(new SocialLifeListAdapter(EventsActivity.this, eventsList));
                                    setAutoCompleteTextViewWithData();
                                } else {

                                }
                            } else {
                                Toast.show(EventsActivity.this, eventsListResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(EventsActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }


    //call ws for update all events

    private void updateEventsAPI(String interestIds) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(EventsActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.EVENT_DATA, interestIds);

        new CallNetworkRequest().postResponse(EventsActivity.this, true, "update Eevent", AUTH_VALUE, WSUrl.POST_UPDATE_EVENT, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
//                            Toast.show(EventsActivity.this, commonResponse.getMessage());
                                redirectContinueScreen();
                            } else {
                                Toast.show(EventsActivity.this, commonResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(EventsActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }

    private void checkAndCallUpdateEventsAPI() {

        JSONArray selectedIds = getSelectedEventList();
        updateEventsAPI(selectedIds.toString());


    }

    private JSONArray getSelectedEventList() {

        JSONArray selectedIds = new JSONArray();

        if (eventsListAdded != null && eventsListAdded.size() > 0) {
            for (int i = 0; i < eventsListAdded.size(); i++) {

                selectedIds.put(eventsListAdded.get(i).getId());
            }
        }

        return selectedIds;
    }

    private void setAutoCompleteTextViewWithData() {

        adapter = new EventListSearchAdapter(this, eventsList);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EventListItem eventListItem = adapter.getItem(position);
                if (eventsListAdded == null) {
                    eventsListAdded = new ArrayList<>();
                }

                if (!eventsListAdded.contains(eventListItem)) {
                    if (eventsListAdded.size() < 3) {
                        eventsListAdded.add(eventListItem);
                        View v = addEventChip(EventsActivity.this, eventListItem);
                        flAddEventChip.addView(v, 0);
                        Toast.show(EventsActivity.this, getString(R.string.event_add_successfully));
                    } else {
                        Toast.show(EventsActivity.this, getString(R.string.max_three_events));
                    }

                }


                autoCompleteTextView.setText("");
            }
        });

    }

    private void redirectContinueScreen() {

        Intent intent = new Intent(EventsActivity.this, ContinueActivity.class);
        startActivity(intent);

    }

    private View addEventChip(Context context, final EventListItem eventItem) {
        View view = View.inflate(context, R.layout.item_added_event_search, null);
        TextView tvEventItem = view.findViewById(R.id.tvEventItem);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        tvEventItem.setText(eventItem.getTitle());


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlexboxLayout linearParent = (FlexboxLayout) view.getParent().getParent();
                FrameLayout linearChild = (FrameLayout) view.getParent();
                linearParent.removeView(linearChild);
                removeItem(eventItem);
            }
        });
        return view;
    }

    private void removeItem(final EventListItem eventItem) {
        Iterator<EventListItem> iterator = eventsListAdded.iterator();
        while (iterator.hasNext()) {
            EventListItem event = iterator.next();
            if (eventItem.equals(event)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
