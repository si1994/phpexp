package com.quirktastic.dashboard.friendrequeststatus.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.dashboard.friendrequeststatus.FriendRequestStatusActivity;
import com.quirktastic.dashboard.friendrequeststatus.ViewUserProfileActivity;
import com.quirktastic.dashboard.friendrequeststatus.model.FriendRequestListUserDetailsItem;
import com.quirktastic.dashboard.friendrequeststatus.model.FriendRequestStatusResponse;
import com.quirktastic.dashboard.fragment.InboxFragment;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import java.util.HashMap;
import java.util.List;

import br.com.instachat.emojilibrary.model.layout.EmojiTextView;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class RequestStatusAdapter extends RecyclerView.Adapter<RequestStatusAdapter.RequestStatusViewHolder> {
    public static final String TAG = InboxFragment.class.getName();
    private Context context;
    private final LayoutInflater layoutInflater;
    private List<FriendRequestListUserDetailsItem> userdetails;

    public RequestStatusAdapter(Context context, List<FriendRequestListUserDetailsItem> userdetails) {
        this.context = context;
        this.userdetails = userdetails;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RequestStatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.row_item_request_status, viewGroup, false);
        return new RequestStatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestStatusViewHolder requestStatusViewHolder, final int i) {

        Glide.with(context)
                .load(userdetails.get(i).getProfilePic())
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(200, 200))
                .into(requestStatusViewHolder.ivUser);

        requestStatusViewHolder.tvUserName.setText(userdetails.get(i).getFirstName() + " " + userdetails.get(i).getLastName());
        requestStatusViewHolder.tvUserDescription.setText(Util.gifDecode(userdetails.get(i).getIntroYourSelfToFriend()));


        requestStatusViewHolder.tvAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRequestStatusAPI(userdetails.get(i).getFromUserId(), "1", i);
            }
        });

        requestStatusViewHolder.tvRejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectReqDialog(i);
            }
        });

        requestStatusViewHolder.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ViewUserProfileActivity.class);
                intent.putExtra(IBundleKey.USER_ID,userdetails.get(i).getFromUserId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userdetails.size();
    }

    public class RequestStatusViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUser;
        TextView tvUserName;
        EmojiTextView tvUserDescription;
        TextView tvAcceptRequest;
        TextView tvRejectRequest;

        public RequestStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = (ImageView) itemView.findViewById(R.id.ivUser);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvUserDescription = (EmojiTextView) itemView.findViewById(R.id.tvUserDescription);
            tvAcceptRequest = (TextView) itemView.findViewById(R.id.tvAcceptRequest);
            tvRejectRequest = (TextView) itemView.findViewById(R.id.tvRejectRequest);
        }
    }


    private void callRequestStatusAPI(String from, String status, final int position) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, from);
        map.put(WSKey.TO_USER_ID, Prefs.getString(context, PrefsKey.USER_ID, ""));
        map.put(WSKey.STATUS, status);
        new CallNetworkRequest().postResponse(context, true, "callRequestStatusAPI", AUTH_VALUE, WSUrl.POST_FRIEND_REQUEST_STATUS, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            Gson gson = new Gson();
                            FriendRequestStatusResponse friendRequestStatusResponse = gson.fromJson(response, FriendRequestStatusResponse.class);

                            if (friendRequestStatusResponse.isFLAG()) {
                                Toast.show(context, friendRequestStatusResponse.getMESSAGE());
                                userdetails.remove(position);

                                if (userdetails.size() == 0) {
                                    ((FriendRequestStatusActivity) context).showNodataFound("No pending request found", "a_user.json");

                                }
                                notifyDataSetChanged();


                            } else {
                                Toast.show(context, friendRequestStatusResponse.getMESSAGE());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(context,context.getString(R.string.error_contact_server));
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                    }
                });
    }

    // Show dialog for reject request....

    private void rejectReqDialog(final int position) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup_layout);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        final TextView txtDescription = (TextView) dialog.findViewById(R.id.txtDescription);
        final TextView btnNo = (TextView) dialog.findViewById(R.id.btnNo);
        final TextView btnYes = (TextView) dialog.findViewById(R.id.btnYes);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        txtDescription.setText("Are you sure you want to reject request?");

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                callRequestStatusAPI(userdetails.get(position).getFromUserId(), "2", position);
            }
        });
    }


}
