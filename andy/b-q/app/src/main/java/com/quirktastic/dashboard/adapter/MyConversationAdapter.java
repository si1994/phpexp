package com.quirktastic.dashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
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
import com.quirktastic.dashboard.model.modelmyconversation.USERDETAILSItem;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.instachat.emojilibrary.model.layout.EmojiTextView;

public class MyConversationAdapter extends RecyclerView.Adapter<MyConversationAdapter.MyConversationViewHolder> {
    private Context context;
    private ArrayList<USERDETAILSItem>mListConversation;
    private final LayoutInflater layoutInflater;

    public MyConversationAdapter(Context context, ArrayList<USERDETAILSItem> mListConversation) {
        this.context = context;
        this.mListConversation = mListConversation;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyConversationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.row_item_my_conversation,viewGroup,false);
        return new MyConversationViewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyConversationViewHolder myConnectionViewHolder, final int i) {
        Glide.with(context)
                .load(mListConversation.get(i).getProfilePic())
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(100, 100))
                .into(myConnectionViewHolder.ivMyConversation);
        myConnectionViewHolder.tvConversationName.setText(mListConversation.get(i).getFullName());

        if(mListConversation.get(i).getLastMessageUrl().equals(""))
        {
            try {
                myConnectionViewHolder.tvConversationAbout.setText(URLDecoder.decode(
                        mListConversation.get(i).getLastMessage().trim(), "UTF-8").trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else {
            myConnectionViewHolder.tvConversationAbout.setText("GIF");
        }


        if(mListConversation.get(i).getUnreadCount().equals("0") || mListConversation.get(i).getUnreadCount().equals("") )
        {
            myConnectionViewHolder.tvUnreadMsgCount.setVisibility(View.GONE);
            myConnectionViewHolder.tvConversationAbout.setTextColor(ContextCompat.getColor(context,R.color.chat_last_message));
            myConnectionViewHolder.tvConversationAbout.setTypeface(ResourcesCompat.getFont(context, R.font.sf_pro_text_regular));
        }
        else {
            myConnectionViewHolder.tvUnreadMsgCount.setVisibility(View.VISIBLE);
            myConnectionViewHolder.tvConversationAbout.setTextColor(ContextCompat.getColor(context,R.color.black));
            myConnectionViewHolder.tvConversationAbout.setTypeface(ResourcesCompat.getFont(context, R.font.sf_pro_text_semibold));
            myConnectionViewHolder.tvUnreadMsgCount.setText(mListConversation.get(i).getUnreadCount());
        }

        myConnectionViewHolder.rlRowMyConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String is_block;

                String[] items = mListConversation.get(i).getBlockBy().split(",");
                List<String> container = Arrays.asList(items);

                if (container.size() > 0) {
                    if (container.contains(Prefs.getString(context, PrefsKey.USER_ID, ""))) {
                        is_block = "1";
                    } else {
                        is_block = "0";
                    }

                } else {
                    is_block = mListConversation.get(i).getIsBlocked();
                }


                final Intent intent = new Intent(context,ChatProfileActivity.class);
                intent.putExtra(IBundleKey.TO_USER_ID,mListConversation.get(i).getUserId());
                intent.putExtra(IBundleKey.CHAT_USER_NAME,mListConversation.get(i).getFullName());
                intent.putExtra(IBundleKey.CHAT_USER_PIC,mListConversation.get(i).getProfilePic());
                intent.putExtra(IBundleKey.IS_BLOCKED,is_block);
                intent.putExtra(IBundleKey.IS_UNFRIEND,mListConversation.get(i).getIsUnfriend());
                intent.putExtra(IBundleKey.SET_USER_PROFILE,0);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListConversation.size();
    }

    public class MyConversationViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivMyConversation;
        private TextView tvConversationName;
        private EmojiTextView tvConversationAbout;
        private TextView tvUnreadMsgCount;
        private RelativeLayout rlRowMyConversation;

        public MyConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMyConversation = (ImageView)itemView.findViewById(R.id.ivMyConversation);
            tvConversationName = (TextView) itemView.findViewById(R.id.tvConversationName);
            tvConversationAbout = (EmojiTextView) itemView.findViewById(R.id.tvConversationAbout);
            tvUnreadMsgCount = (TextView) itemView.findViewById(R.id.tvUnreadMsgCount);
            rlRowMyConversation = (RelativeLayout) itemView.findViewById(R.id.rlRowMyConversation);
        }
    }
}
