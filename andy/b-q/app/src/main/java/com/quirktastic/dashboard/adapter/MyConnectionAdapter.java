package com.quirktastic.dashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.dashboard.model.modelmyconnection.USERDETAILSItem;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyConnectionAdapter extends RecyclerView.Adapter<MyConnectionAdapter.MyConnectionViewHolder> {
    private Context context;
    private ArrayList<USERDETAILSItem> mListConnection;
    private final LayoutInflater layoutInflater;

    public MyConnectionAdapter(Context context, ArrayList<USERDETAILSItem> mListConnection) {
        this.context = context;
        this.mListConnection = mListConnection;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.row_item_my_connection, viewGroup, false);
        return new MyConnectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyConnectionViewHolder myConnectionViewHolder, final int i) {

        Glide.with(context)
                .load(mListConnection.get(i).getProfilePic())
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(100, 100))
                .into(myConnectionViewHolder.ivMyConnection);

        myConnectionViewHolder.tvConnectionName.setText(mListConnection.get(i).getFullName());
        if (!TextUtils.isEmpty(mListConnection.get(i).getCityState())) {
            myConnectionViewHolder.tvCityState.setText(mListConnection.get(i).getCityState());
        } else {
            myConnectionViewHolder.tvCityState.setText("");
        }


        myConnectionViewHolder.rlRowMyConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String is_block;

                String[] items = mListConnection.get(i).getBlockBy().split(",");
                List<String> container = Arrays.asList(items);

                if (container.size() > 0) {
                    if (container.contains(Prefs.getString(context, PrefsKey.USER_ID, ""))) {
                        is_block = "1";
                    } else {
                        is_block = "0";
                    }

                } else {
                    is_block = mListConnection.get(i).getIsBlocked();
                }

                Intent intent = new Intent(context, ChatProfileActivity.class);
                intent.putExtra(IBundleKey.TO_USER_ID, mListConnection.get(i).getToUserId());
                intent.putExtra(IBundleKey.CHAT_USER_NAME, mListConnection.get(i).getFullName());
                intent.putExtra(IBundleKey.CHAT_USER_PIC, mListConnection.get(i).getProfilePic());
                intent.putExtra(IBundleKey.IS_BLOCKED, is_block);
                intent.putExtra(IBundleKey.IS_UNFRIEND, mListConnection.get(i).getIsUnfriend());
                intent.putExtra(IBundleKey.SET_USER_PROFILE, 1);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListConnection.size();
    }

    public class MyConnectionViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMyConnection;
        private TextView tvConnectionName;
        private TextView tvCityState;
        private RelativeLayout rlRowMyConnection;

        public MyConnectionViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMyConnection = (ImageView) itemView.findViewById(R.id.ivMyConnection);
            tvConnectionName = (TextView) itemView.findViewById(R.id.tvConnectionName);
            tvCityState = (TextView) itemView.findViewById(R.id.tvCityState);
            rlRowMyConnection = (RelativeLayout) itemView.findViewById(R.id.rlRowMyConnection);
        }
    }
}
