package com.quirktastic.instagram.model.instagram;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CarouselMediaItem{

	@SerializedName("users_in_photo")
	private List<UsersInPhotoItem> usersInPhoto;

	@SerializedName("videos")
	private Videos videos;

	@SerializedName("type")
	private String type;

	public void setUsersInPhoto(List<UsersInPhotoItem> usersInPhoto){
		this.usersInPhoto = usersInPhoto;
	}

	public List<UsersInPhotoItem> getUsersInPhoto(){
		return usersInPhoto;
	}

	public void setVideos(Videos videos){
		this.videos = videos;
	}

	public Videos getVideos(){
		return videos;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"CarouselMediaItem{" + 
			"users_in_photo = '" + usersInPhoto + '\'' + 
			",videos = '" + videos + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}