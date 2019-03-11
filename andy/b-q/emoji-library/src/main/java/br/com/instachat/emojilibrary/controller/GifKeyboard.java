package br.com.instachat.emojilibrary.controller;

import android.content.res.AssetManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import br.com.instachat.emojilibrary.R;
import br.com.instachat.emojilibrary.adapter.GifAdapter;
import br.com.instachat.emojilibrary.model.layout.EmojiCompatActivity;
import br.com.instachat.emojilibrary.model.layout.OnGifClickListener;

/**
 * Created by edgar on 21/02/2016.
 */
public class GifKeyboard implements OnGifClickListener {
    private static final String TAG = "GifKeyboard";

    private EmojiCompatActivity mActivity;

    private ImageView[] mTabIcons = new ImageView[6];
    private RelativeLayout mGifKeyboardLayout;
    private ImageView mBackspace;
    private View view;
    private RecyclerView rvGifKeyBord;
    private WhatsAppPanel whatsAppPanel;

    // CONSTRUCTOR
    public GifKeyboard(EmojiCompatActivity activity, View view, WhatsAppPanel whatsAppPanel) {
        this.mActivity = activity;
        this.whatsAppPanel = whatsAppPanel;
        this.view = view;
        this.mGifKeyboardLayout = (RelativeLayout) view.findViewById(R.id.gif_keyboard);
        initGifKeyboardViewPager();
    }

    // INTIALIZATIONS
    private void initGifKeyboardViewPager() {


        rvGifKeyBord = (RecyclerView) view.findViewById(R.id.rvGifKeyBord);
        rvGifKeyBord.setLayoutManager(new GridLayoutManager(mActivity, 3));

        AssetManager am = mActivity.getAssets();

        ArrayList<Integer> listGif = new ArrayList<>();
        listGif.add(R.raw.gif_1);
        listGif.add(R.raw.gif_1);
        listGif.add(R.raw.gif_2);
        listGif.add(R.raw.gif_3);
        listGif.add(R.raw.gif_4);

        rvGifKeyBord.setAdapter(new GifAdapter(mActivity, listGif, whatsAppPanel));


    }

    //GETTERS AND SETTERS
    public RelativeLayout getGifKeyboardLayout() {
        return mGifKeyboardLayout;
    }

    @Override
    public void onGifClicked(int gif) {

    }
}
