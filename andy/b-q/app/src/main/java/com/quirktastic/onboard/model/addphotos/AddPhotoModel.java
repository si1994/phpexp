package com.quirktastic.onboard.model.addphotos;

import android.net.Uri;

import java.io.File;

/**
 * @author Deepak
 * @created on Wed,Nov,2018
 * @updated on $file.lastModified
 */
public class AddPhotoModel {
    private Uri uri;
    private File file;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


}
