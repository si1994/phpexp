package br.com.instachat.emojilibrary.model.layout;

import java.io.File;

/**
 * Created by edgar on 21/02/2016.
 */
public interface WhatsAppPanelEventListener {

    public void onSendClicked();
    public void onSendGif(File file);

}
