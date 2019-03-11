package com.quirktastic.dashboard.profile.model.profiledetails;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhotosItem implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("photos")
    private String photos;

    @SerializedName("photosUri")
    private String photosUri;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotos() {
        return photos;
    }

    public String getPhotosUri() {
        return photosUri;
    }

    public void setPhotosUri(String photosUri) {
        this.photosUri = photosUri;
    }

    @Override
    public String toString() {
        return
                "PhotosItem{" +
                        "id = '" + id + '\'' +
                        ",photos = '" + photos + '\'' +
                        "}";
    }
}