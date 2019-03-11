package com.quirktastic.instagram.model.instagram;


import com.google.gson.annotations.SerializedName;


public class UsersInPhotoItem{

	@SerializedName("position")
	private Position position;

	@SerializedName("user")
	private User user;

	public void setPosition(Position position){
		this.position = position;
	}

	public Position getPosition(){
		return position;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"UsersInPhotoItem{" + 
			"position = '" + position + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}